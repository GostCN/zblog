package com.cchcz.blog.spider.spider;

import com.alibaba.fastjson.JSON;
import com.cchcz.blog.model.entity.Tool;
import com.cchcz.blog.model.entity.ToolType;
import com.cchcz.blog.service.BizToolTypeService;
import com.cchcz.blog.spider.util.SpiderUrlConnection;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <ClassName>GiteeSpider</ClassName>
 * <Description></Description>
 *
 * @Author cchcz
 * @Date 2018年07月02日 22:24
 */
@Slf4j
@Service
public class ScraptToolSpider {

    @Autowired
    private BizToolTypeService bizToolTypeService;

    public List<Tool> getToolList() {
        List<Tool> toolList = Lists.newArrayList();
        try {
            String result = SpiderUrlConnection.get("https://www.zhyd.me/tool");
            Document doc = Jsoup.parse(result);
            Elements projectContainer = doc.select("div[class=zhyd-box zhyd-tool]");
            projectContainer.stream().forEach(e -> {
                String categoryName = e.select("div[class=category] div").text();
                ToolType qentity = new ToolType();
                qentity.setName(categoryName);
                qentity = bizToolTypeService.getOneByEntity(qentity);
                Long id = -1L;
                if (qentity != null) {
                    id = qentity.getId();
                }
                Long typeId = id;
                e.select("a[target=_blank]").stream().forEach(a -> {
                    String href = a.attr("href");
                    String name = a.select("div[class=logo]").text();
                    String img = a.select("img").attr("data-original");
                    String description = a.select("div[class=desc]").text();
                    Tool tool = new Tool();
                    tool.setTypeId(typeId);
                    tool.setImageIcon(img);
                    tool.setDescription(description);
                    tool.setOutLink(href);
                    tool.setName(name);
                    toolList.add(tool);
                });
            });
        } catch (Exception e) {
            log.error("ScraptToolSpider.getToolList:", e);
        }
        return toolList;
    }
}
