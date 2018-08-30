package com.cchcz.blog.biz;

import com.alibaba.fastjson.JSON;
import com.cchcz.blog.dao.beans.BizVisitorRank;
import com.cchcz.blog.model.vo.VisitorRankVo;
import com.cchcz.blog.model.vo.VisitorVo;
import com.cchcz.blog.service.VisitorRankService;
import com.cchcz.blog.util.VisitorUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * created by zhangcheng 2018/8/18
 */
@Slf4j
@Service
public class VisitorRankBiz {

    @Autowired
    private VisitorRankService visitorRankService;
    @Resource
    private RedisTemplate redisTemplate;

    public void updateVisitorRank() {
        List<BizVisitorRank> ranks = visitorRankService.listAll();
        List<VisitorRankVo> entities = getVisitorRankVoList();
        List<BizVisitorRank> list = new ArrayList<>();
        Map<String, Long> map = new HashMap<>();
        for (Iterator<VisitorRankVo> it = entities.iterator(); it.hasNext(); ) {
            VisitorRankVo vo = it.next();
            String name = vo.getName();
            long num = vo.getNum();
            if ((StringUtils.isEmpty(name) && name.trim().length() == 0) || num == 0) {
                continue;
            }
            String[] array = vo.getName().split("_");
            BizVisitorRank so = new BizVisitorRank();
            VisitorUtil.buildBizVisitorRank(array, so);
            so.setNum(num);
            list.add(so);
            map.put(vo.getName(), num);
        }
        //log.info("updateVisitorRank_ranks=" + JSON.toJSONString(ranks));
        log.info("updateVisitorRank_entities=" + JSON.toJSONString(entities));
        if (CollectionUtils.isEmpty(ranks)) {
            visitorRankService.insertList(list);
        } else {
            for (Iterator<BizVisitorRank> it = ranks.iterator(); it.hasNext(); ) {
                BizVisitorRank rank = it.next();
                if (StringUtils.isEmpty(rank) || StringUtils.isEmpty(rank.getCountry())) {
                    continue;
                }
                String name = rank.getCountry() + "_" + rank.getProvice() + "_" + rank.getCity();
                if (map.containsKey(name)) {
                    rank.setNum(map.get(name));
                    visitorRankService.updateSelective(rank);
                    map.remove(name);
                }
            }
            List<BizVisitorRank> newRanks = new ArrayList<>();
            for (Map.Entry<String, Long> entry : map.entrySet()) {
                String[] array = entry.getKey().split("_");
                long num = entry.getValue();
                if (num == 0) {
                    continue;
                }
                BizVisitorRank vo = new BizVisitorRank();
                VisitorUtil.buildBizVisitorRank(array, vo);
                vo.setNum(num);
                newRanks.add(vo);
            }
            //log.info("updateVisitorRank_newRanks=" + JSON.toJSONString(newRanks));
            visitorRankService.insertList(newRanks);
        }

    }

    public List<VisitorRankVo> getVisitorRankVoList() {
        List<VisitorRankVo> visitorRankList = Lists.newLinkedList();
        Set<ZSetOperations.TypedTuple<Object>> visitShowAll = redisTemplate.opsForZSet().reverseRangeByScoreWithScores("visitShowAll", 0, 1000000000);
        Iterator<ZSetOperations.TypedTuple<Object>> it = visitShowAll.iterator();
        while (it.hasNext()) {
            ZSetOperations.TypedTuple<Object> tuple = it.next();
            long num = Math.round(tuple.getScore());
            String location = String.valueOf(tuple.getValue());
            if (null != location && location.replaceAll("_", "").trim().length() > 0) {
                visitorRankList.add(new VisitorRankVo(location, num));
            }
        }
        return visitorRankList;
    }

    public Pair<List<VisitorRankVo>, List<VisitorVo>> getVisitorLatlngList() {
        List<VisitorVo> visitorVoList = Lists.newLinkedList();
        List<VisitorRankVo> entities = getVisitorRankVoList();
        for (Iterator<VisitorRankVo> it = entities.iterator(); it.hasNext(); ) {
            VisitorRankVo vo = it.next();
            String location = vo.getName();
            long num = vo.getNum();
            if ((StringUtils.isEmpty(location) && location.trim().length() == 0) || num == 0) {
                continue;
            }
            long startScore = 1533052800000L;//2018-08-01 00:00:00
            long endScore = System.currentTimeMillis();
            Set<ZSetOperations.TypedTuple<Object>> visitLatLng = redisTemplate.opsForZSet().reverseRangeByScoreWithScores("visit_" + location, startScore, endScore);
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
        return Pair.of(entities, visitorVoList);
    }


}
