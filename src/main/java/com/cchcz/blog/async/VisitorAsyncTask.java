package com.cchcz.blog.async;

import com.alibaba.fastjson.JSONObject;
import com.cchcz.blog.model.entity.Config;
import com.cchcz.blog.service.SysConfigService;
import com.cchcz.blog.util.BaiduUtil;
import com.cchcz.blog.util.SpringBeanFactory;
import com.cchcz.blog.util.UrlBuildUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.Future;

/**
 * created by zhangcheng 2018/8/3
 */

@Slf4j
@Component
public class VisitorAsyncTask {

    @Resource
    private RedisTemplate redisTemplate;

    @Async
    public Future<String> visitorAnalyze(String ip, Date visitDate) {
        String location = null, lat = null, lng = null;
        try {
            SysConfigService sysConfigService = SpringBeanFactory.getBean(SysConfigService.class);
            Config config = sysConfigService.get();
            String url = UrlBuildUtil.getLocationByIp(ip, config.getBaiduApiAk());
            String locationJson = BaiduUtil.getLocationInfo(url, ip);
            JSONObject jsonResult = JSONObject.parseObject(locationJson);
            int status = jsonResult.getIntValue("status");
            if (status == 0) {
                JSONObject localtionContent = jsonResult.getJSONObject("content");
                JSONObject addressDetail = localtionContent.getJSONObject("address_detail");
                String city = addressDetail.getString("city");
                String province = addressDetail.getString("province");
                if (StringUtils.isEmpty(province)) {
                    province = "北京市";
                }
                if (StringUtils.isEmpty(city)) {
                    province = "北京市";
                }
                location = "中国" + "_" + province + "_" + city;
                JSONObject point = localtionContent.getJSONObject("point");
                lng = point.getString("x");
                lat = point.getString("y");
            } else {
                location = "其它_国家_城市";
                lng = "1";
                lat = "1";
            }
            redisTemplate.opsForZSet().incrementScore("visitShowAll", location, 1);
            redisTemplate.opsForZSet().add("visit_" + location, lng + "_" + lat, visitDate.getTime());
        } catch (Exception e) {
            log.error("VisitorAsyncTask调用百度接口异常,ip:" + ip, e);
        } finally {
            log.info("VisitorAsyncTask,location:{},lng:{},lat:{}", location, lng, lat);
        }
        return null;
    }

    //public void initTestIP() {
    //    Set<String> visitAllIp = Optional.ofNullable(redisTemplate.opsForSet().members("visitAll_ip")).orElse(Sets.newHashSet());
    //    //Set<String> visitAllIp = new HashSet<>(Arrays.asList("202.108.14.240", "185.131.240.120"));
    //    for (String ip : visitAllIp) {
    //        visitorAnalyze(ip, new Date());
    //    }
    //}

}
