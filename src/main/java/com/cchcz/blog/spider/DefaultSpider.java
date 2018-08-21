package com.cchcz.blog.spider;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * <Description> 爬虫 </Description>
 * <ClassName> Spider </ClassName>
 *
 * @author cchcz
 * @date 2018年03月08日 22:28
 */
@Component
public class DefaultSpider {
    @Resource
    private ISpider articleSpider;
    @Resource
    private ISpider catalogSpider;

    public boolean doSpide(String url, Long uid, boolean isCatalog) throws Exception {
        if (isCatalog) {
//            return false;
            return catalogSpider.doSpide(url, uid);
        }
        return articleSpider.doSpide(url, uid);
    }
}
