package com.cchcz.blog.spider.support;

import com.google.common.collect.Lists;
import com.cchcz.blog.spider.ICatalogSpiderParser;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <Description> 博客园爬虫</Description>
 * <ClassName> CNBlogsSpider</ClassName>
 *
 * @author cchcz
 * @date 2018年03月08日 18:50
 */
@Component
public class CNBlogsSpiderParser implements ICatalogSpiderParser {
    public static final String SPIDER_TYPE = "cnblogs";

    @Override
    public String[] filter(String[] result) {
        if (result[0].contains("博客园")) {
            result[0] = result[0].split("-")[0].trim();
        }
        return result;
    }

    @Override
    public String getSpiderType() {
        return SPIDER_TYPE;
    }

    @Override
    public List<String> parseCatalog(String content) {
        Document doc = Jsoup.parse(content);
        Elements select = doc.select("html body div[id=main] div[id=post_detail] div[class=post] div[class=postText] a");
        if (StringUtils.isEmpty(select.html())) {
            select = doc.select("html body div[id=main] div[id=post_detail] a");
        }
        if (StringUtils.isEmpty(select.html())) {
            select = doc.select("html body a");
        }
        int size = select.size();
        final List<String> res = Lists.newArrayListWithExpectedSize(size);
        select.forEach(e -> {
            res.add(e.attr("href"));
        });
        return res;
    }
}
//