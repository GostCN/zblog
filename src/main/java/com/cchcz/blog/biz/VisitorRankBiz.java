package com.cchcz.blog.biz;

import com.alibaba.fastjson.JSON;
import com.cchcz.blog.dao.beans.BizVisitorRank;
import com.cchcz.blog.model.vo.VisitorRankVo;
import com.cchcz.blog.service.VisitorRankService;
import com.cchcz.blog.util.VisitorUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
            String[] array = vo.getName().split("_");
            BizVisitorRank so = new BizVisitorRank();
            VisitorUtil.buildBizVisitorRank(array, so);
            so.setNum(vo.getNum());
            list.add(so);
            map.put(vo.getName(), vo.getNum());
        }
        //log.info("updateVisitorRank_ranks=" + JSON.toJSONString(ranks));
        log.info("updateVisitorRank_entities=" + JSON.toJSONString(entities));
        if (CollectionUtils.isEmpty(ranks)) {
            visitorRankService.insertList(list);
        } else {
            for (Iterator<BizVisitorRank> it = ranks.iterator(); it.hasNext(); ) {
                BizVisitorRank rank = it.next();
                String name = rank.getCountry() + "_" + rank.getProvice() + "_" + rank.getCity();
                if (map.containsKey(name)) {
                    rank.setNum(map.get(name));
                    map.remove(name);
                }
                visitorRankService.updateSelective(rank);
            }
            List<BizVisitorRank> newRanks = new ArrayList<>();
            for (Map.Entry<String, Long> entry : map.entrySet()) {
                String[] array = entry.getKey().split("_");
                long num = entry.getValue();
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
            visitorRankList.add(new VisitorRankVo(location, num));
        }
        return visitorRankList;
    }


}
