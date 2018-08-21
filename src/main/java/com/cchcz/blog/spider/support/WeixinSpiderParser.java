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
public class WeixinSpiderParser implements ISpiderParser {
    public static final String SPIDER_TYPE = "weixin";

    @Override
    public String[] filter(String[] result) {
        return result;
    }

    @Override
    public String getSpiderType() {
        return SPIDER_TYPE;
    }
}
