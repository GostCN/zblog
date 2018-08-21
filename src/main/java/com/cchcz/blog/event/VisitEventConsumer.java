package com.cchcz.blog.event;

import com.alibaba.fastjson.JSONObject;
import com.cchcz.blog.model.entity.Config;
import com.google.common.base.Charsets;
import com.cchcz.blog.service.SysConfigService;
import com.cchcz.blog.util.BaiduUtil;
import com.cchcz.blog.util.SpringBeanFactory;
import com.cchcz.blog.util.UrlBuildUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.OnError;
import javax.websocket.Session;

/**
 * <ClassName>VisitEventConsumer</ClassName>
 * <Description></Description>
 *
 * @Author cchcz
 * @Date 2018年07月22日 16:08
 */
@Slf4j
public class VisitEventConsumer implements EventConsumer<VisitEvent> {
    private HttpServletResponse response;
    private boolean isException = false;
    private boolean isDeprecated = false;

    public void checkException() throws InterruptedException {
        while (true) {
            if (isException) {
                return;
            }
//            try {
//                response.getOutputStream().write("".getBytes());
//            } catch (IOException e) {
//                e.printStackTrace();
//                return;
//            }
            Thread.sleep(3000);
        }
    }

    @Override
    public void consume(VisitEvent event) {
        log.info("VisitEventConsumer消费:{},{}", event.getIp(), event.getMessage());
        boolean flag = true;
        String location = null;
        String lat = null;
        String lng = null;
        try {
            String ip = event.getIp();
            SysConfigService sysConfigService = SpringBeanFactory.getBean(SysConfigService.class);
            Config config = sysConfigService.get();
            String url = UrlBuildUtil.getLocationByIp(ip, config.getBaiduApiAk());
            String locationJson = BaiduUtil.getLocationInfo(url, ip);
            JSONObject localtionContent = JSONObject.parseObject(locationJson).getJSONObject("content");
            JSONObject addressDetail = localtionContent.getJSONObject("address_detail");
            String city = addressDetail.getString("city");
            String province = addressDetail.getString("province");
            location = "中国 " + province + " " + city;
            // 经纬度
            JSONObject point = localtionContent.getJSONObject("point");
            // 纬度
            lat = point.getString("y");
            // 经度
            lng = point.getString("x");
        } catch (Exception e) {
            log.error("VisitEventConsumer调用百度接口异常", e);
            flag = false;
        }
        log.info("VisitEventConsumer消费:{},{},是否写数据:{}", event.getIp(), event.getMessage(), flag);
        if (flag) {
            try {
                String data = new VisitGeoEventBean(location, lat, lng).toEventStr();
                response.setCharacterEncoding(Charsets.UTF_8.name());
                response.setContentType("text/event-stream");
                response.getOutputStream().write("event:visitor\n".getBytes());
                response.getOutputStream().write(data.getBytes());
                response.flushBuffer();//前端页面关闭或刷新
            } catch (Exception e) {
                //log.error("VisitEventConsumer Error", e);
                isException = true;
                deprecated();
            }
        }
    }


    public VisitEventConsumer(HttpServletResponse response) {
        this.response = response;
    }

    @Override
    public Class<VisitEvent> getType() {
        return VisitEvent.class;
    }

    @Override
    public boolean isDeprecated() {
        return isDeprecated;
    }

    public void deprecated() {
        isDeprecated = true;
    }


}
