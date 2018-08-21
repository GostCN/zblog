package com.cchcz.blog.spider;

import com.cchcz.blog.spider.util.SpiderUrlConnection;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <Description> 爬虫分发器</Description>
 * <ClassName> SpiderDispatcher</ClassName>
 *
 * @author cchcz
 * @date 2018年03月08日 18:53
 */
@Component
public class SpiderDispatcher {
    private Logger logger = LoggerFactory.getLogger(SpiderDispatcher.class);
    @Autowired
    private SpiderParserFactory spiderFactory;

    /**
     * <Title>dispatch</Title>
     * <Description> 获取指定的爬虫，解析html</Description>
     *
     * @param url
     * @param content
     * @return java.lang.String[]
     * @throws
     */
    public List<String[]> dispatch(String url, String content, boolean isCatalog) {
        List<String[]> result = Lists.newArrayList();
        if (isCatalog) {
            ISpiderParser spider = spiderFactory.getSpider(url);
            if (spider instanceof ICatalogSpiderParser) {
                List<String> strings = ((ICatalogSpiderParser) spider).parseCatalog(content);
                String surl = null;
                String article = null;
                for (int i = 0; i < strings.size(); i++) {
                    surl = strings.get(i);
                    try {
                        ISpiderParser subSpider = spiderFactory.getSpider(surl);
                        article = SpiderUrlConnection.get(surl);
                        String[] parse = subSpider.parse(article, surl);
                        if (SpiderParserValidator.checkWithoutException(parse)) {
                            result.add(parse);
                        } else {
                            logger.info("解析失败网址：{}", surl);
                        }
                    } catch (Exception e) {
                        logger.error("请求url异常：{},异常信息：{}", surl, e.getMessage());
                    }
                }
            } else {
                throw new RuntimeException("不支持解析该网站目录");
            }
        } else {
            String[] parse = spiderFactory.getSpider(url).parse(content, url);
            SpiderParserValidator.check(parse);
            result.add(parse);
        }
        return result;
    }
}
