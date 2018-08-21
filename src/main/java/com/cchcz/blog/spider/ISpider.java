package com.cchcz.blog.spider;

/**
 * <Description> 爬虫接口</Description>
 * <ClassName> ISpider</ClassName>
 *
 * @author cchcz
 * @date 2018年03月12日 10:44
 */
public interface ISpider {
    boolean doSpide(String url, Long uid) throws Exception;
}
