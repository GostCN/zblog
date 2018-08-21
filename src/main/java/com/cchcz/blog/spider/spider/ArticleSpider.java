package com.cchcz.blog.spider.spider;

import com.cchcz.blog.spider.service.SpiderResultSaveService;
import com.cchcz.blog.spider.ISpider;
import com.cchcz.blog.spider.SpiderDispatcher;
import com.cchcz.blog.spider.util.SpiderUrlConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * <Description> 爬虫 </Description>
 * <ClassName> Spider </ClassName>
 *
 * @author cchcz
 * @date 2018年03月08日 22:28
 */
@Component
public class ArticleSpider implements ISpider {
    @Autowired
    private SpiderDispatcher spiderDispatcher;
    @Autowired
    private SpiderResultSaveService spiderResultSaveService;

    @Override
    public boolean doSpide(String url, Long uid) throws IOException {
        String content = SpiderUrlConnection.get(url);
        String[] res = spiderDispatcher.dispatch(url, content, false).get(0);
        return spiderResultSaveService.save(res, uid);
    }
}
