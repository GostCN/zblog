package com.cchcz.blog.interceptor;

import com.cchcz.blog.event.EventProducer;
import com.cchcz.blog.event.VisitEvent;
import com.cchcz.blog.async.VisitorAsyncTask;
import com.cchcz.blog.util.DateUtil;
import com.cchcz.blog.util.IpUtil;
import com.cchcz.blog.util.SpringBeanFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 自定义拦截器
 * Created by BlueT on 2017/3/9.
 */
@Component
@Slf4j
@Order(2)
public class VisitorInterceptor extends HandlerInterceptorAdapter {
    @Resource
    private RedisTemplate redisTemplate;
    @Autowired
    private EventProducer<VisitEvent> visitEventProducer;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {
        recordVisitIp(request);
    }

    private void recordVisitIp(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String realIp = IpUtil.getRealIp(request);
        if (uri.equals("/")
                || uri.startsWith("/music")
                || uri.startsWith("/onlineTool")
                || uri.startsWith("/index")
                || uri.startsWith("/type")
                || uri.startsWith("/tag")
                || uri.startsWith("/article")
                || uri.startsWith("/about")
                || uri.startsWith("/tool")
                || uri.startsWith("/nav")
                || uri.startsWith("/book")
                || uri.startsWith("/osp")
                || uri.startsWith("/links")
                || uri.startsWith("/archives")
                || uri.startsWith("/guestbook")
                || uri.startsWith("/recommended")
                || uri.startsWith("/disclaimer")
                || uri.startsWith("/visitor")
                || uri.startsWith("/updateLog")) {
            Date currentDate = new Date();
            String today = DateUtil.date2Str(currentDate, "yyyyMMdd");
            Long visitToday = redisTemplate.opsForValue().increment("visitToday_" + today, 1);
            Long visitAll = redisTemplate.opsForValue().increment("visitAll", 1);
            redisTemplate.opsForSet().add("visitAll_ip", realIp);
            redisTemplate.opsForSet().add("visitToday_ip_" + today, realIp);

            long tomorrowTime = DateUtil.getTomorrowTime() * 1000;
            long expireTime = tomorrowTime - System.currentTimeMillis();
            redisTemplate.expire("visitToday_" + today, expireTime, TimeUnit.MILLISECONDS);
            redisTemplate.expire("visitToday_ip_" + today, expireTime, TimeUnit.MILLISECONDS);

            request.setAttribute("visitToday", visitToday);
            request.setAttribute("visitAll", visitAll);

            visitEventProducer.post(VisitEvent.create(realIp));

            VisitorAsyncTask visitorAsyncTask = SpringBeanFactory.getBean(VisitorAsyncTask.class);
            visitorAsyncTask.visitorAnalyze(realIp, currentDate);
        }
    }
}