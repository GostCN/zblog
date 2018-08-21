package com.cchcz.blog.spider;

import com.cchcz.blog.spider.support.DefaultSpiderParser;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * <Description> 爬虫工厂</Description>
 * <ClassName> SpiderFactory</ClassName>
 *
 * @author cchcz
 * @date 2018年03月08日 18:54
 */
@Service
public class SpiderParserFactory implements ApplicationContextAware, ApplicationListener {
    private static Map<String, ISpiderParser> spiderCache = new HashMap<>();
    private ApplicationContext applicationContext;

    public ISpiderParser getSpider(String url) {
        final String url_ = url.toLowerCase();
        Set<Map.Entry<String, ISpiderParser>> entries = spiderCache.entrySet();
        for (Map.Entry<String, ISpiderParser> entry : entries) {
            if (url_.contains(entry.getKey())) {
                return entry.getValue();
            }
        }
        return spiderCache.get(DefaultSpiderParser.SPIDER_TYPE);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if (applicationEvent != null && applicationEvent instanceof ContextRefreshedEvent) {
            Map<String, ISpiderParser> beansOfType = applicationContext.getBeansOfType(ISpiderParser.class);
            if (beansOfType != null) {
                beansOfType.forEach((k, v) -> {
                    spiderCache.put(v.getSpiderType(), v);
                });
            }
        }
        spiderCache.put(DefaultSpiderParser.SPIDER_TYPE, new DefaultSpiderParser());
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
