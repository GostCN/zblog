package com.cchcz.blog.spider.spider;

import com.google.common.collect.Lists;
import com.cchcz.blog.model.entity.Nav;
import com.cchcz.blog.model.entity.NavType;
import com.cchcz.blog.service.BizNavTypeService;
import com.cchcz.blog.spider.util.SpiderUrlConnection;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * <ClassName>NetNavSpider</ClassName>
 * <Description></Description>
 *
 * @Author cchcz
 * @Date 2018年07月15日 17:19
 */
@Slf4j
@Service
public class NetNavSpider {
    @Autowired
    private BizNavTypeService bizNavTypeService;

    public List<Nav> getNavList() {
        List<Nav> navList = Lists.newArrayList();
        try {
            String result = SpiderUrlConnection.get("https://tool.lu/nav/", 1000);
            Document doc = Jsoup.parse(result);
            Elements navTypeElements = doc.select("h3[class=link-node]");
            Elements navElements = doc.select("div[class=link-list clearfix]");
            Element navType = null;
            Element navEle = null;
            for (int i = 0; i < navTypeElements.size(); i++) {
                navType = navTypeElements.get(i);
                String categoryName = navType.text();
                NavType qentity = new NavType();
                qentity.setName(categoryName);
                qentity = bizNavTypeService.getOneByEntity(qentity);
                if (qentity == null) {
                    qentity = new NavType();
                    qentity.setName(categoryName);
                    qentity = bizNavTypeService.insert(qentity);
                }
                navEle = navElements.get(i);
                Long typeId = qentity.getId();
                navEle.select("div[class=link-item]").stream().forEach(a -> {
                    String href = a.select("a").attr("href");
                    String name = a.select("a").text();
                    String img = a.select("span").attr("style").replaceAll("background-image:url\\(", "").replaceAll("\\);", "");
                    String description = a.select("p[class=link-body]").text();
                    Nav nav = new Nav();
                    nav.setTypeId(typeId);
                    nav.setImageIcon(img);
                    nav.setDescription(description);
                    nav.setOutLink(href);
                    nav.setName(name);
                    navList.add(nav);
                });
            }
        } catch (Exception e) {
            log.error("NetNavSpider.getNavList:", e);
        }
        return navList;
    }

    public static void main(String[] args) throws IOException {
        NetNavSpider netNavSpider = new NetNavSpider();
        List<Nav> navList = netNavSpider.getNavList();
        navList.forEach(nav -> System.out.println(nav.getOutLink()));
    }
}
