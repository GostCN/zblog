package com.cchcz.blog.spider.service;

import com.alibaba.fastjson.JSON;
import com.cchcz.blog.spider.spider.ScraptToolSpider;
import com.cchcz.blog.model.entity.Tool;
import com.cchcz.blog.service.BizToolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
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
public class SyncToolInfoService {
    @Autowired
    private ScraptToolSpider scraptToolSpider;
    @Autowired
    private BizToolService bizToolService;

    public void syncTools() {
        List<Tool> toolList = scraptToolSpider.getToolList();
        saveTools(toolList);
        downToolIcon();
    }


    private void saveTools(List<Tool> toolList) {
        toolList.stream().forEach(x -> {
            Tool query = new Tool();
            query.setName(x.getName());
            query.setTypeId(x.getTypeId());
            Tool oneByEntity = bizToolService.getOneByEntity(query);
            if (oneByEntity != null) {
                x.setId(oneByEntity.getId());
//                bizToolService.updateSelective(x);
            } else {
                bizToolService.insert(x);
            }
        });
    }

    private void downToolIcon() {
        List<Tool> tools = bizToolService.listAll();
        tools.stream().forEach(x -> {
            String imgUrl = x.getImageIcon();
            if (imgUrl.startsWith("https://static.zhyd.me/static/img/tool/")) {
                try {
                    File imageFile = new File("/root/zblog/tool/" + (imgUrl.replaceAll("https://static.zhyd.me/static/img/tool/", "")));
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
                    x.setImageIcon(imgUrl.replaceAll("https://static.zhyd.me/static/img", "http://static.cchcz.com/zblog"));
                    bizToolService.updateSelective(x);
                    log.info("ScraptToolSpider.toolList:" + JSON.toJSONString(x));
                } catch (Exception e) {
                    log.error("",e);
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
}
