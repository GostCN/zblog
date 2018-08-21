package com.cchcz.blog.spider.support;

import com.cchcz.blog.spider.ISpiderParser;
import org.springframework.stereotype.Component;

/**
 * <Description> 博客园爬虫</Description>
 * <ClassName> CNBlogsSpider</ClassName>
 *
 * @author cchcz
 * @date 2018年03月08日 18:50
 */
@Component
public class CSDNSpiderParser implements ISpiderParser {
    public static final String SPIDER_TYPE = "csdn";

    @Override
    public String[] filter(String[] result) {
        if (result[0].contains("CSDN博客")) {
            result[0] = result[0].split("-")[0].trim();
        }
        return result;
    }

    @Override
    public String getSpiderType() {
        return SPIDER_TYPE;
    }
}
