package com.cchcz.blog.service.impl;

import com.cchcz.blog.aspect.annotation.RedisCache;
import com.cchcz.blog.dao.beans.BizNavType;
import com.cchcz.blog.dao.mapper.BizNavTypeMapper;
import com.cchcz.blog.model.entity.NavType;
import com.cchcz.blog.model.vo.NavTypeConditionVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.cchcz.blog.service.BizNavTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <ClassName>BizNavTypeServiceImpl</ClassName>
 * <Description>网址导航类型</Description>
 *
 * @author cchcz
 * @date 2018/7/15 18:06
 */
@Service
public class BizNavTypeServiceImpl implements BizNavTypeService {

    @Autowired
    private BizNavTypeMapper bizNavTypeMapper;

    /**
     * 保存一个实体，null的属性不会保存，会使用数据库默认值
     *
     * @param entity
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @RedisCache(flush = true)
    public NavType insert(NavType entity) {
        Assert.notNull(entity, "NavType不可为空！");
        entity.setUpdateTime(new Date());
        entity.setCreateTime(new Date());
        bizNavTypeMapper.insertSelective(entity.getBizNavType());
        return entity;
    }

    /**
     * 批量插入，支持批量插入的数据库可以使用，例如MySQL,H2等，另外该接口限制实体包含id属性并且必须为自增列
     *
     * @param entities
     */
    @Override
    @RedisCache(flush = true)
    public void insertList(List<NavType> entities) {
        Assert.notNull(entities, "NavTypes不可为空！");
        List<BizNavType> list = new ArrayList<>();
        for (NavType entity : entities) {
            entity.setUpdateTime(new Date());
            entity.setCreateTime(new Date());
            list.add(entity.getBizNavType());
        }
        bizNavTypeMapper.insertList(list);
    }

    /**
     * 根据主键字段进行删除，方法参数必须包含完整的主键属性
     *
     * @param primaryKey
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @RedisCache(flush = true)
    public boolean removeByPrimaryKey(Long primaryKey) {
        return bizNavTypeMapper.deleteByPrimaryKey(primaryKey) > 0;
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
    public boolean update(NavType entity) {
        Assert.notNull(entity, "NavType不可为空！");
        entity.setUpdateTime(new Date());
        return bizNavTypeMapper.updateByPrimaryKey(entity.getBizNavType()) > 0;
    }

    /**
     * 根据主键更新属性不为null的值
     *
     * @param entity
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @RedisCache(flush = true)
    public boolean updateSelective(NavType entity) {
        Assert.notNull(entity, "NavType不可为空！");
        entity.setUpdateTime(new Date());
        return bizNavTypeMapper.updateByPrimaryKeySelective(entity.getBizNavType()) > 0;
    }

    /**
     * 根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号
     *
     * @param primaryKey
     * @return
     */
    @Override
    public NavType getByPrimaryKey(Long primaryKey) {
        Assert.notNull(primaryKey, "PrimaryKey不可为空！");
        BizNavType entity = bizNavTypeMapper.selectByPrimaryKey(primaryKey);
        return null == entity ? null : new NavType(entity);
    }

    /**
     * 根据实体中的属性进行查询，只能有一个返回值，有多个结果时抛出异常，查询条件使用等号
     *
     * @param entity
     * @return
     */
    @Override
    public NavType getOneByEntity(NavType entity) {
        Assert.notNull(entity, "NavType不可为空！");
        BizNavType bo = bizNavTypeMapper.selectOne(entity.getBizNavType());
        return null == bo ? null : new NavType(bo);
    }

    /**
     * 查询全部结果，listByEntity(null)方法能达到同样的效果
     *
     * @return
     */
    @Override
    @RedisCache
    public List<NavType> listAll() {
        List<BizNavType> entityList = bizNavTypeMapper.selectAll();

        if (CollectionUtils.isEmpty(entityList)) {
            return null;
        }
        List<NavType> list = new ArrayList<>();
        for (BizNavType entity : entityList) {
            list.add(new NavType(entity));
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
    public List<NavType> listByEntity(NavType entity) {
        Assert.notNull(entity, "NavType不可为空！");
        List<BizNavType> entityList = bizNavTypeMapper.select(entity.getBizNavType());
        if (CollectionUtils.isEmpty(entityList)) {
            return null;
        }
        List<NavType> list = new ArrayList<>();
        for (BizNavType po : entityList) {
            list.add(new NavType(po));
        }
        return list;
    }

    @Override
    public PageInfo<NavType> findPageBreakByCondition(NavTypeConditionVO vo) {
        PageHelper.startPage(vo.getPageNumber(), vo.getPageSize());
        List<BizNavType> list = bizNavTypeMapper.findPageBreakByCondition(vo);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        List<NavType> boList = new ArrayList<>();
        for (BizNavType bizNavType : list) {
            boList.add(new NavType(bizNavType));
        }
        PageInfo bean = new PageInfo<>(list);
        bean.setList(boList);
        return bean;
    }

}
