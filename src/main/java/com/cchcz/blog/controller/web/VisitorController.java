package com.cchcz.blog.controller.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cchcz.blog.biz.VisitorRankBiz;
import com.cchcz.blog.event.EventBus;
import com.cchcz.blog.event.VisitEventConsumer;
import com.cchcz.blog.model.entity.Config;
import com.cchcz.blog.model.vo.VisitorRankVo;
import com.cchcz.blog.model.vo.VisitorVo;
import com.cchcz.blog.service.SysConfigService;
import com.cchcz.blog.util.BaiduUtil;
import com.cchcz.blog.util.DateUtil;
import com.cchcz.blog.util.UrlBuildUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * <ClassName>VisitorController</ClassName>
 * <Description></Description>
 *
 * @Author cchcz
 * @Date 2018年07月22日 09:52
 */
@Controller
@RequestMapping("/visitor")
@Slf4j
public class VisitorController {
    @Resource
    private RedisTemplate redisTemplate;
    @Autowired
    private SysConfigService configService;
    @Autowired
    private VisitorRankBiz visitorRankBiz;

    @RequestMapping("/")
    public String index(Model model) {
        List<VisitorVo> visitorVoList = Lists.newLinkedList();
        List<VisitorRankVo> visitorRankList = Lists.newLinkedList();
        List<VisitorRankVo> entities = visitorRankBiz.getVisitorRankVoList();
        for (Iterator<VisitorRankVo> it = entities.iterator(); it.hasNext(); ) {
            VisitorRankVo vo = it.next();
            long num = vo.getNum();
            String nameValue = vo.getName();
            String location = nameValue.replaceAll("_", " ");
            if (StringUtils.isEmpty(location) || location.contains("null")) {//兼容老数据
                continue;
            }
            if (!location.contains("其它") && !location.contains("中国")) {//兼容老数据
                location = "中国 " + location;
            }
            visitorRankList.add(new VisitorRankVo(location, num));

            long startScore = 1533052800000L;//2018-08-01 00:00:00
            long endScore = System.currentTimeMillis();
            Set<ZSetOperations.TypedTuple<Object>> visitLatLng = redisTemplate.opsForZSet().reverseRangeByScoreWithScores("visit_" + nameValue, startScore, endScore);
            log.info("visitLatLng:{}, nameValue:{}, num:{}", visitLatLng.size(), location, num);
            Iterator<ZSetOperations.TypedTuple<Object>> itLagLng = visitLatLng.iterator();
            while (itLagLng.hasNext()) {
                ZSetOperations.TypedTuple<Object> tupleLagLng = itLagLng.next();
                String latLng = String.valueOf(tupleLagLng.getValue());
                String time = String.valueOf(tupleLagLng.getScore());
                String lng = "", lat = "";
                if (latLng.split("_").length == 2) {//117.20426333779_39.138027950138
                    lng = latLng.split("_")[0];// 经度
                    lat = latLng.split("_")[1];   // 纬度
                } else {
                    lng = lat = "1";
                }
                log.info("VisitorVo,latLng:{},lng:{},lat:{},score:{}", latLng, lng, lat, time);
                if (!location.contains("其它")) {
                    visitorVoList.add(VisitorVo.create(location, lat, lng));
                }
            }
        }
        model.addAttribute("topList", visitorRankList);
        model.addAttribute("visitorVoList", JSONArray.toJSONString(visitorVoList));
        return "/visitor";
    }

    @GetMapping("/analyze")
    @ResponseBody
    public Map<String, Object> visitAnalyze() {
        String today = DateUtil.date2Str(new Date(), "yyyyMMdd");
        Set<String> visitTodayIp = Optional.ofNullable(redisTemplate.opsForSet().members("visitToday_ip_" + today)).orElse(Sets.newHashSet());
        Config config = configService.get();
        Map<String, Object> result = Maps.newHashMap();
        Map<String, Integer> visitLocation = Maps.newTreeMap();

        Map<String, String> ipInfo = Maps.newTreeMap();
        visitTodayIp.stream().forEach(ip -> {
            try {
                String locationJson = BaiduUtil.getLocationInfo(UrlBuildUtil.getLocationByIp(ip, config.getBaiduApiAk()), ip);
                JSONObject localtionContent = JSONObject.parseObject(locationJson).getJSONObject("content");
                JSONObject addressDetail = localtionContent.getJSONObject("address_detail");
                String city = addressDetail.getString("city");
                String province = addressDetail.getString("province");
                String location = "中国 " + province + " " + city;
                Integer ipnum = visitLocation.get(location);
                if (ipnum == null) {
                    visitLocation.put(location, 1);
                } else {
                    visitLocation.put(location, ipnum + 1);
                }
                ipInfo.put(ip, location);
            } catch (Exception e) {
            }
        });
        result.put("num", visitTodayIp.size());
        result.put("visitLocation", visitLocation);
        result.put("ipInfo", ipInfo);
        return result;
    }

    @RequestMapping("event")
    public void event(HttpServletRequest request, HttpServletResponse response) {
        VisitEventConsumer consumer = new VisitEventConsumer(response);
        try {
            EventBus.getInstance().register(consumer);
            consumer.checkException();
        } catch (Exception e) {
            log.error("event异常", e);
        } finally {
            consumer.deprecated();
            try {
                response.getOutputStream().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
