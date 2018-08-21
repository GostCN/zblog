package com.cchcz.blog.spider.support;

import com.cchcz.blog.spider.ISpiderParser;
import org.springframework.stereotype.Component;

/**
 * <Description> 开源中国解析器</Description>
 * <ClassName> OsChinaSpiderParser</ClassName>
 *
 * @author cchcz
 * @date 2018年04月03日 11:25
 */
@Component
public class OsChinaSpiderParser implements ISpiderParser {
    public static final String SPIDER_TYPE = "oschina";

    @Override
    public String[] filter(String[] result) {
        return result;
    }

    @Override
    public String getSpiderType() {
        return SPIDER_TYPE;
    }
}
