package com.cchcz.blog.event;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.Session;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * <ClassName>VisitEventConsumer</ClassName>
 * <Description></Description>
 *
 * @Author cchcz
 * @Date 2018年07月22日 16:08
 */
@Slf4j
public class WebSocketVisitEventConsumer implements EventConsumer<VisitEvent> {
    private boolean isException = false;
    private boolean isDeprecated = false;
    private CopyOnWriteArraySet<Session> webSocketSet;

    @Override
    public void consume(VisitEvent event) {
        log.info("VisitEventConsumer消费web:{},{}", event.getIp(), event.getMessage());
        //boolean flag = true;
        //String location = null;
        //String lat = null;
        //String lng = null;
        //try {
        //    String ip = event.getIp();
        //    SysConfigService sysConfigService = SpringBeanFactory.getBean(SysConfigService.class);
        //    Config config = sysConfigService.get();
        //    String url = UrlBuildUtil.getLocationByIp(ip, config.getBaiduApiAk());
        //    String locationJson = BaiduUtil.getLocationInfo(url, ip);
        //    JSONObject localtionContent = JSONObject.parseObject(locationJson).getJSONObject("content");
        //    JSONObject addressDetail = localtionContent.getJSONObject("address_detail");
        //    String city = addressDetail.getString("city");
        //    String province = addressDetail.getString("province");
        //    location = "中国 " + province + " " + city;
        //    // 经纬度
        //    JSONObject point = localtionContent.getJSONObject("point");
        //    // 纬度
        //    lat = point.getString("y");
        //    // 经度
        //    lng = point.getString("x");
        //} catch (Exception e) {
        //    log.error("VisitEventConsumer调用百度接口异常", e);
        //    flag = false;
        //}
        //log.info("VisitEventConsumer消费web:{},{},是否写数据:{}", event.getIp(), event.getMessage(), flag);
        //if (flag) {
        //    try {
        //        String data = new VisitGeoEventBean(location, lat, lng).toJSONString();
        //        WebSocketUtil.broadcast(data, webSocketSet);
        //    } catch (Exception e) {
        //        log.error("VisitEventConsumer Error", e);
        //        isException = true;
        //        deprecated();
        //    }
        //}
    }

    public WebSocketVisitEventConsumer(CopyOnWriteArraySet<Session> webSocketSet) {
        this.webSocketSet = webSocketSet;
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

    class EventBean {
        private String address;
        private Geo location;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Geo getLocation() {
            return location;
        }

        public void setLocation(Geo location) {
            this.location = location;
        }

        public EventBean(String address, String lat, String lng) {
            this.address = address;
            this.location = new Geo(lat, lng);
        }

        public String toEventStr() {
            return "data:" + JSONObject.toJSONString(this) + "\n\n";
        }
    }

    class Geo {
        private String lat;
        private String lng;

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public Geo(String lat, String lng) {
            this.lat = lat;
            this.lng = lng;
        }
    }
}
