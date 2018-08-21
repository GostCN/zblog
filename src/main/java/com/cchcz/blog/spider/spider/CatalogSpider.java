package com.cchcz.blog.spider.spider;

import com.cchcz.blog.spider.service.SpiderResultSaveService;
import com.cchcz.blog.spider.util.SpiderUrlConnection;
import com.cchcz.blog.spider.ISpider;
import com.cchcz.blog.spider.SpiderDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * <Description> 目录爬虫 </Description>
 * <ClassName> Spider </ClassName>
 *
 * @author cchcz
 * @date 2018年03月08日 22:28
 */
@Component
public class CatalogSpider implements ISpider {
    private Logger logger = LoggerFactory.getLogger(CatalogSpider.class);
    @Autowired
    private SpiderDispatcher spiderDispatcher;
    @Autowired
    private SpiderResultSaveService spiderResultSaveService;

    @Override
    public boolean doSpide(String url, Long uid) throws IOException {
        String content = SpiderUrlConnection.get(url);
        List<String[]> list = spiderDispatcher.dispatch(url, content, true);
        for (int i = 0; i < list.size(); i++) {
            try {
                spiderResultSaveService.save(list.get(i), uid);
            } catch (Exception e) {
                logger.info("入库失败：", e);
            }
        }
        return true;
    }
}
