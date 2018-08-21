package com.cchcz.blog.spider.service;

import com.cchcz.blog.spider.spider.NetNavSpider;
import com.cchcz.blog.model.entity.Nav;
import com.cchcz.blog.service.BizNavService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * <ClassName>SyncOpenSourceInfoTask</ClassName>
 * <Description></Description>
 *
 * @Author cchcz
 * @Date 2018年07月03日 14:30
 */
@Service
@Slf4j
public class SyncNavInfoService {
    @Autowired
    private NetNavSpider netNavSpider;
    @Autowired
    private BizNavService bizNavService;

    public void syncNavs() {
        List<Nav> navList = netNavSpider.getNavList();
//        List<Nav> navList = bizNavService.listAll();
        saveNavs(navList);
        downNavIcon();
    }


    private void saveNavs(List<Nav> navList) {
        navList.stream().filter(x -> x.getOutLink().startsWith("https://tool.lu")).forEach(x -> {
            Nav query = new Nav();
            query.setName(x.getName());
            query.setTypeId(x.getTypeId());
            Nav oneByEntity = bizNavService.getOneByEntity(query);
            if (oneByEntity != null) {
//                x.setId(oneByEntity.getId());
//                String outLink = oneByEntity.getOutLink();
//                if (outLink.startsWith("https://tool.lu")) {
//                    String realURL = getRealURL(outLink);
//                    oneByEntity.setOutLink(realURL);
//                    bizNavService.updateSelective(x);
//                }
            } else {
                String realURL = getRealURL(x.getOutLink());
                oneByEntity.setOutLink(realURL);
                bizNavService.insert(x);
            }
        });
    }

    private void downNavIcon() {
        List<Nav> navs = bizNavService.listAll();
        navs.stream().forEach(x -> {
            String imgUrl = x.getImageIcon();
            if (imgUrl.startsWith("https://tool.lu/cache/")) {
                try {
                    String[] split = imgUrl.split("/");
                    File imageFile = new File("/app/static/zblog/nav/" + split[split.length - 1]);
                    //new一个URL对象
                    URL url = new URL(imgUrl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(5 * 1000);
                    InputStream inStream = conn.getInputStream();
                    byte[] data = readInputStream(inStream);
                    FileOutputStream outStream = new FileOutputStream(imageFile);
                    outStream.write(data);
                    outStream.close();
                    x.setImageIcon("/nav/" + split[split.length - 1]);
                    bizNavService.updateSelective(x);
                } catch (Exception e) {
                    log.error("", e);
                }
            }
        });
    }

    private static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }

    private String getRealURL(String url) {
        try {
            HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
            urlConnection.setConnectTimeout(10000);
            urlConnection.setReadTimeout(10000);
            urlConnection.connect();
            int responseCode = urlConnection.getResponseCode();
            if (responseCode == 301) {
                List<String> location = urlConnection.getHeaderFields().get("Location");
                return location.get(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return url;
    }
}
