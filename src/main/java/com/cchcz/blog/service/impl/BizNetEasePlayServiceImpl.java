package com.cchcz.blog.service.impl;

import com.cchcz.blog.aspect.annotation.RedisCache;
import com.cchcz.blog.dao.beans.BizNetEasePlay;
import com.cchcz.blog.dao.mapper.BizNetEasePlayMapper;
import com.cchcz.blog.model.entity.NetEasePlay;
import com.cchcz.blog.model.enums.MusicStatusEnum;
import com.cchcz.blog.model.vo.NetEasePlayConditionVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.cchcz.blog.service.BizNetEasePlayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BizNetEasePlayServiceImpl implements BizNetEasePlayService {

    @Autowired
    private BizNetEasePlayMapper bizNetEasePlayMapper;

    /**
     * 分页查询
     *
     * @param vo
     * @return
     */
    @Override
    public PageInfo<NetEasePlay> findPageBreakByCondition(NetEasePlayConditionVO vo) {
        PageHelper.startPage(vo.getPageNumber(), vo.getPageSize());
        List<BizNetEasePlay> list = bizNetEasePlayMapper.selectAll();
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        List<NetEasePlay> boList = new ArrayList<>();
        for (BizNetEasePlay bizWangyiPlay : list) {
            boList.add(new NetEasePlay(bizWangyiPlay));
        }
        PageInfo bean = new PageInfo<>(list);
        bean.setList(boList);
        return bean;
    }

    /**
     * 获取默认的歌单
     *
     * @return
     */
    @Override
    @RedisCache
    public NetEasePlay getDefault() {
        NetEasePlay play = new NetEasePlay();
        play.setStatus(MusicStatusEnum.DEFAULT.getCode());
        BizNetEasePlay bizWangyiPlay = bizNetEasePlayMapper.selectOne(play.getBizNetEasePlay());
        return null == bizWangyiPlay ? null : new NetEasePlay(bizWangyiPlay);
    }

    /**
     * 保存一个实体，null的属性不会保存，会使用数据库默认值
     *
     * @param entity 实体业务对象
     * @return 当前实体业务对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @RedisCache(flush = true)
    public NetEasePlay insert(NetEasePlay entity) {
        Assert.notNull(entity, "WangyiPlay不可为空！");
        entity.setUpdateTime(new Date());
        entity.setCreateTime(new Date());
        bizNetEasePlayMapper.insertSelective(entity.getBizNetEasePlay());
        return entity;
    }

    /**
     * 根据主键字段进行删除，方法参数必须包含完整的主键属性
     *
     * @param primaryKey 主键
     * @return true: 删除成功 false: 删除失败
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @RedisCache(flush = true)
    public boolean removeByPrimaryKey(Integer primaryKey) {
        return bizNetEasePlayMapper.deleteByPrimaryKey(primaryKey) > 0;
    }

    /**
     * 根据主键更新属性不为null的值
     *
     * @param entity 实体业务对象
     * @return true: 更新成功 false: 更新失败
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @RedisCache(flush = true)
    public boolean updateSelective(NetEasePlay entity) {
        Assert.notNull(entity, "WangyiPlay不可为空！");
        entity.setUpdateTime(new Date());
        return bizNetEasePlayMapper.updateByPrimaryKey(entity.getBizNetEasePlay()) > 0;
    }

    /**
     * 根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号
     *
     * @param primaryKey 主键
     * @return 业务对象
     */
    @Override
    @RedisCache
    public NetEasePlay getByPrimaryKey(Integer primaryKey) {
        Assert.notNull(primaryKey, "PrimaryKey不可为空！");
        BizNetEasePlay entity = bizNetEasePlayMapper.selectByPrimaryKey(primaryKey);
        return null == entity ? null : new NetEasePlay(entity);
    }


    @Override
    @RedisCache(flush = true)
    public void insertList(List<NetEasePlay> entities) {
        Assert.notNull(entities, "NetEasePlay不可为空！");
        List<BizNetEasePlay> list = new ArrayList<>();
        for (NetEasePlay entity : entities) {
            entity.setUpdateTime(new Date());
            entity.setCreateTime(new Date());
            list.add(entity.getBizNetEasePlay());
        }
        bizNetEasePlayMapper.insertList(list);
    }

    /**
     * 根据主键更新实体全部字段，null值会被更新
     *
     * @param entity
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @RedisCache(flush = true)
    public boolean update(NetEasePlay entity) {
        Assert.notNull(entity, "NetEasePlay不可为空！");
        entity.setUpdateTime(new Date());
        return bizNetEasePlayMapper.updateByPrimaryKey(entity.getBizNetEasePlay()) > 0;
    }

    /**
     * 根据实体中的属性进行查询，只能有一个返回值，有多个结果时抛出异常，查询条件使用等号
     *
     * @param entity
     * @return
     */
    @Override
    public NetEasePlay getOneByEntity(NetEasePlay entity) {
        Assert.notNull(entity, "NetEasePlay不可为空！");
        BizNetEasePlay bo = bizNetEasePlayMapper.selectOne(entity.getBizNetEasePlay());
        return null == bo ? null : new NetEasePlay(bo);
    }


    /**
     * 查询全部结果，listByEntity(null)方法能达到同样的效果
     *
     * @return
     */
    @Override
    @RedisCache
    public List<NetEasePlay> listAll() {
        List<BizNetEasePlay> entityList = bizNetEasePlayMapper.selectAll();

        if (CollectionUtils.isEmpty(entityList)) {
            return null;
        }
        List<NetEasePlay> list = new ArrayList<>();
        for (BizNetEasePlay entity : entityList) {
            list.add(new NetEasePlay(entity));
        }
        return list;
    }

    /**
     * 根据实体中的属性值进行查询，查询条件使用等号
     *
     * @param entity
     * @return
     */
    @Override
    @RedisCache
    public List<NetEasePlay> listByEntity(NetEasePlay entity) {
        Assert.notNull(entity, "NetEasePlay不可为空！");
        List<BizNetEasePlay> entityList = bizNetEasePlayMapper.select(entity.getBizNetEasePlay());
        if (CollectionUtils.isEmpty(entityList)) {
            return null;
        }
        List<NetEasePlay> list = new ArrayList<>();
        for (BizNetEasePlay po : entityList) {
            list.add(new NetEasePlay(po));
        }
        return list;
    }
}
