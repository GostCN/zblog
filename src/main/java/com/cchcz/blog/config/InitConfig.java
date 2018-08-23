package com.cchcz.blog.config;

import com.cchcz.blog.dao.beans.BizVisitorRank;
import com.cchcz.blog.service.VisitorRankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * created by zhangcheng 2018/8/22
 */
@Slf4j
public class InitConfig {

    @Autowired
    private VisitorRankService visitorRankService;
    @Resource
    private RedisTemplate redisTemplate;

    @PostConstruct
    private void init() {
        //initVisitor2Redis();

    }

    public void initVisitor2Redis() {
        //加载数据库排行榜数据到redis
        List<BizVisitorRank> ranks = visitorRankService.listAll();
        ranks = CollectionUtils.isEmpty(ranks) ? new ArrayList<>() : ranks;
        for (Iterator<BizVisitorRank> it = ranks.iterator(); it.hasNext(); ) {
            BizVisitorRank rank = it.next();
            String location = rank.getCountry() + "_" + rank.getProvice() + "_" + rank.getCity();
            long num = rank.getNum();
            redisTemplate.opsForZSet().incrementScore("visitShowAll", location, num);
        }


    }


}
