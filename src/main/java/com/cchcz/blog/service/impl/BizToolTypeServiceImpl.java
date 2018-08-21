
package com.cchcz.blog.service.impl;

import com.cchcz.blog.aspect.annotation.RedisCache;
import com.cchcz.blog.dao.beans.BizToolType;
import com.cchcz.blog.dao.mapper.BizToolTypeMapper;
import com.cchcz.blog.model.entity.ToolType;
import com.cchcz.blog.model.vo.ToolTypeConditionVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.cchcz.blog.service.BizToolTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 标签
 *
 * @author cchcz
 * @version 1.0
 * @date 2018/4/16 16:26
 * @since 1.0
 */
@Service
public class BizToolTypeServiceImpl implements BizToolTypeService {

    @Autowired
    private BizToolTypeMapper bizToolTypeMapper;

    /**
     * 保存一个实体，null的属性不会保存，会使用数据库默认值
     *
     * @param entity
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @RedisCache(flush = true)
    public ToolType insert(ToolType entity) {
        Assert.notNull(entity, "ToolType不可为空！");
        entity.setUpdateTime(new Date());
        entity.setCreateTime(new Date());
        bizToolTypeMapper.insertSelective(entity.getBizToolType());
        return entity;
    }

    /**
     * 批量插入，支持批量插入的数据库可以使用，例如MySQL,H2等，另外该接口限制实体包含id属性并且必须为自增列
     *
     * @param entities
     */
    @Override
    @RedisCache(flush = true)
    public void insertList(List<ToolType> entities) {
        Assert.notNull(entities, "ToolTypes不可为空！");
        List<BizToolType> list = new ArrayList<>();
        for (ToolType entity: entities) {
            entity.setUpdateTime(new Date());
            entity.setCreateTime(new Date());
            list.add(entity.getBizToolType());
        }
        bizToolTypeMapper.insertList(list);
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
        return bizToolTypeMapper.deleteByPrimaryKey(primaryKey) > 0;
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
    public boolean update(ToolType entity) {
        Assert.notNull(entity, "ToolType不可为空！");
        entity.setUpdateTime(new Date());
        return bizToolTypeMapper.updateByPrimaryKey(entity.getBizToolType()) > 0;
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
    public boolean updateSelective(ToolType entity) {
        Assert.notNull(entity, "ToolType不可为空！");
        entity.setUpdateTime(new Date());
        return bizToolTypeMapper.updateByPrimaryKeySelective(entity.getBizToolType()) > 0;
    }

    /**
     * 根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号
     *
     * @param primaryKey
     * @return
     */
    @Override
    public ToolType getByPrimaryKey(Long primaryKey) {
        Assert.notNull(primaryKey, "PrimaryKey不可为空！");
        BizToolType entity = bizToolTypeMapper.selectByPrimaryKey(primaryKey);
        return null == entity ? null : new ToolType(entity);
    }

    /**
     * 根据实体中的属性进行查询，只能有一个返回值，有多个结果时抛出异常，查询条件使用等号
     *
     * @param entity
     * @return
     */
    @Override
    public ToolType getOneByEntity(ToolType entity) {
        Assert.notNull(entity, "ToolType不可为空！");
        BizToolType bo = bizToolTypeMapper.selectOne(entity.getBizToolType());
        return null == bo ? null : new ToolType(bo);
    }

    /**
     * 查询全部结果，listByEntity(null)方法能达到同样的效果
     *
     * @return
     */
    @Override
    @RedisCache
    public List<ToolType> listAll() {
        List<BizToolType> entityList = bizToolTypeMapper.selectAll();

        if (CollectionUtils.isEmpty(entityList)) {
            return null;
        }
        List<ToolType> list = new ArrayList<>();
        for (BizToolType entity: entityList) {
            list.add(new ToolType(entity));
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
    public List<ToolType> listByEntity(ToolType entity) {
        Assert.notNull(entity, "ToolType不可为空！");
        List<BizToolType> entityList = bizToolTypeMapper.select(entity.getBizToolType());
        if (CollectionUtils.isEmpty(entityList)) {
            return null;
        }
        List<ToolType> list = new ArrayList<>();
        for (BizToolType po: entityList) {
            list.add(new ToolType(po));
        }
        return list;
    }

    @Override
    public PageInfo<ToolType> findPageBreakByCondition(ToolTypeConditionVO vo) {
        PageHelper.startPage(vo.getPageNumber(), vo.getPageSize());
        List<BizToolType> list = bizToolTypeMapper.findPageBreakByCondition(vo);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        List<ToolType> boList = new ArrayList<>();
        for (BizToolType bizToolType : list) {
            boList.add(new ToolType(bizToolType));
        }
        PageInfo bean = new PageInfo<>(list);
        bean.setList(boList);
        return bean;
    }

}
