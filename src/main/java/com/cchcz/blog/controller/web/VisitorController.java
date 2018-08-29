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
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
        Pair<List<VisitorRankVo>, List<VisitorVo>> pair = visitorRankBiz.getVisitorLatlngList();
        List<VisitorRankVo> visitorRankList = pair.getLeft();
        List<VisitorVo> visitorVoList = pair.getRight();
        visitorRankList.stream().forEach(so -> {
            String name = so.getName();
            name = name.replaceAll("_", " ");
            so.setName(name);
        });
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
