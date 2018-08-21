
package com.cchcz.blog.service.impl;

import com.cchcz.blog.aspect.annotation.RedisCache;
import com.cchcz.blog.constant.DateConstant;
import com.cchcz.blog.dao.beans.SysConfig;
import com.cchcz.blog.dao.mapper.SysConfigMapper;
import com.cchcz.blog.model.entity.Config;
import com.cchcz.blog.util.DateUtil;
import com.cchcz.blog.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * 系统配置
 *
 * @author cchcz
 * @version 1.0
 * @date 2018/4/16 16:26
 * @since 1.0
 */
@Service
public class SysConfigServiceImpl implements SysConfigService {

    @Autowired
    private SysConfigMapper sysConfigMapper;
    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 获取系统配置
     *
     * @return
     */
    @Override
    @RedisCache
    public Config get() {
        SysConfig config = sysConfigMapper.get();
        return null == config ? null : new Config(config);
    }

    /**
     * 添加系统配置
     *
     * @param config
     * @return
     */
    @Override
    @RedisCache(flush = true, group = "config")
    public Config insert(Config config) {
        config.setCreateTime(new Date());
        config.setUpdateTime(new Date());
        sysConfigMapper.insert(config.getSysConfig());
        return config;
    }

    /**
     * 删除系统配置记录
     *
     * @param id
     */
    @Override
    @RedisCache(flush = true, group = "config")
    public void remove(Long id) {
        sysConfigMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改系统配置记录
     *
     * @param config
     */
    @Override
    @RedisCache(flush = true, group = "config")
    public void update(Config config) {
        config.setUpdateTime(new Date());
        sysConfigMapper.updateByPrimaryKeySelective(config.getSysConfig());
    }

    /**
     * 获取网站详情
     *
     * @return
     */
    @Override
    public Map<String, Object> getSiteInfo() {
        Map<String, Object> map = sysConfigMapper.getSiteInfo();
        if (!CollectionUtils.isEmpty(map)) {
            Date recordeTime = (Date) map.get("recordeTime");
            if (!StringUtils.isEmpty(recordeTime)) {
                map.put("recordeTime", DateUtil.date2Str(recordeTime, "yyyy年MM月dd日HH点"));
            }
            Date buildSiteDate = DateUtil.str2Date("2017-10-27 00:00:00", DateConstant.YYYY_MM_DD_HH_MM_SS_EN);
            // 获取建站天数
            map.put("buildSiteDate", DateUtil.getGapDay(buildSiteDate, new Date()));
            String today = DateUtil.date2Str(new Date(), "yyyyMMdd");
            Object visitToday = redisTemplate.opsForValue().get("visitToday_" + today);
            Object visitAll = redisTemplate.opsForValue().get("visitAll");
            map.put("visitToday", visitToday);
            map.put("visitAll", visitAll);
        }
        return map;
    }
}
