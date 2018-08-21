package com.cchcz.blog.spider;

import java.util.List;

/**
 * <Description> 目录爬虫解析器接口</Description>
 * <ClassName> ICatalogSpider</ClassName>
 *
 * @author cchcz
 * @date 2018年03月09日 17:30
 */
public interface ICatalogSpiderParser extends ISpiderParser {
    /**
     * <Title>default</Title>
     * <Description> 解析目录网页，返回链接列表</Description>
     *
     * @param
     * @return
     * @throws
     */
    List<String> parseCatalog(String content);
}
