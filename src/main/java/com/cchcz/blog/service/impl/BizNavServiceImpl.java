package com.cchcz.blog.service.impl;

import com.cchcz.blog.aspect.annotation.RedisCache;
import com.cchcz.blog.dao.beans.BizNav;
import com.cchcz.blog.dao.mapper.BizNavMapper;
import com.cchcz.blog.model.entity.Nav;
import com.cchcz.blog.model.vo.NavConditionVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.cchcz.blog.service.BizNavService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <ClassName>BizNavServiceImpl</ClassName>
 * <Description>导航网址</Description>
 *
 * @author cchcz
 * @date 2018/7/15 18:06
 */
@Service
public class BizNavServiceImpl implements BizNavService {

    @Autowired
    private BizNavMapper bizNavMapper;

    /**
     * 保存一个实体，null的属性不会保存，会使用数据库默认值
     *
     * @param entity
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @RedisCache(flush = true)
    public Nav insert(Nav entity) {
        Assert.notNull(entity, "Nav不可为空！");
        entity.setUpdateTime(new Date());
        entity.setCreateTime(new Date());
        bizNavMapper.insertSelective(entity.getBizNav());
        return entity;
    }

    /**
     * 批量插入，支持批量插入的数据库可以使用，例如MySQL,H2等，另外该接口限制实体包含id属性并且必须为自增列
     *
     * @param entities
     */
    @Override
    @RedisCache(flush = true)
    public void insertList(List<Nav> entities) {
        Assert.notNull(entities, "Navs不可为空！");
        List<BizNav> list = new ArrayList<>();
        for (Nav entity : entities) {
            entity.setUpdateTime(new Date());
            entity.setCreateTime(new Date());
            list.add(entity.getBizNav());
        }
        bizNavMapper.insertList(list);
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
        return bizNavMapper.deleteByPrimaryKey(primaryKey) > 0;
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
    public boolean update(Nav entity) {
        Assert.notNull(entity, "Nav不可为空！");
        entity.setUpdateTime(new Date());
        return bizNavMapper.updateByPrimaryKey(entity.getBizNav()) > 0;
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
    public boolean updateSelective(Nav entity) {
        Assert.notNull(entity, "Nav不可为空！");
        entity.setUpdateTime(new Date());
        return bizNavMapper.updateByPrimaryKeySelective(entity.getBizNav()) > 0;
    }

    /**
     * 根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号
     *
     * @param primaryKey
     * @return
     */
    @Override
    public Nav getByPrimaryKey(Long primaryKey) {
        Assert.notNull(primaryKey, "PrimaryKey不可为空！");
        BizNav entity = bizNavMapper.selectByPrimaryKey(primaryKey);
        return null == entity ? null : new Nav(entity);
    }

    /**
     * 根据实体中的属性进行查询，只能有一个返回值，有多个结果时抛出异常，查询条件使用等号
     *
     * @param entity
     * @return
     */
    @Override
    public Nav getOneByEntity(Nav entity) {
        Assert.notNull(entity, "Nav不可为空！");
        BizNav bo = bizNavMapper.selectOne(entity.getBizNav());
        return null == bo ? null : new Nav(bo);
    }

    /**
     * 查询全部结果，listByEntity(null)方法能达到同样的效果
     *
     * @return
     */
    @Override
    @RedisCache
    public List<Nav> listAll() {
        List<BizNav> entityList = bizNavMapper.selectAll();

        if (CollectionUtils.isEmpty(entityList)) {
            return null;
        }
        List<Nav> list = new ArrayList<>();
        for (BizNav entity : entityList) {
            list.add(new Nav(entity));
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
    public List<Nav> listByEntity(Nav entity) {
        Assert.notNull(entity, "Nav不可为空！");
        List<BizNav> entityList = bizNavMapper.select(entity.getBizNav());
        if (CollectionUtils.isEmpty(entityList)) {
            return null;
        }
        List<Nav> list = new ArrayList<>();
        for (BizNav po : entityList) {
            list.add(new Nav(po));
        }
        return list;
    }

    @Override
    public PageInfo<Nav> findPageBreakByCondition(NavConditionVO vo) {
        PageHelper.startPage(vo.getPageNumber(), vo.getPageSize());
        List<BizNav> list = bizNavMapper.findPageBreakByCondition(vo);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        List<Nav> boList = new ArrayList<>();
        for (BizNav BizNav : list) {
            boList.add(new Nav(BizNav));
        }
        PageInfo bean = new PageInfo<>(list);
        bean.setList(boList);
        return bean;
    }

}
