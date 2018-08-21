
package com.cchcz.blog.service.impl;

import com.cchcz.blog.aspect.annotation.RedisCache;
import com.cchcz.blog.dao.beans.BizOsProject;
import com.cchcz.blog.dao.mapper.BizOSProjectMapper;
import com.cchcz.blog.model.entity.OSProject;
import com.cchcz.blog.model.vo.OSProjectConditionVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.cchcz.blog.service.BizOSProjectService;
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
public class BizOSProjectServiceImpl implements BizOSProjectService {

    @Autowired
    private BizOSProjectMapper bizOSProjectMapper;

    /**
     * 保存一个实体，null的属性不会保存，会使用数据库默认值
     *
     * @param entity
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @RedisCache(flush = true)
    public OSProject insert(OSProject entity) {
        Assert.notNull(entity, "OSProject不可为空！");
        entity.setUpdateTime(new Date());
        entity.setCreateTime(new Date());
        bizOSProjectMapper.insertSelective(entity.getBizOsProject());
        return entity;
    }

    /**
     * 批量插入，支持批量插入的数据库可以使用，例如MySQL,H2等，另外该接口限制实体包含id属性并且必须为自增列
     *
     * @param entities
     */
    @Override
    @RedisCache(flush = true)
    public void insertList(List<OSProject> entities) {
        Assert.notNull(entities, "OSProjects不可为空！");
        List<BizOsProject> list = new ArrayList<>();
        for (OSProject entity : entities) {
            entity.setUpdateTime(new Date());
            entity.setCreateTime(new Date());
            list.add(entity.getBizOsProject());
        }
        bizOSProjectMapper.insertList(list);
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
        return bizOSProjectMapper.deleteByPrimaryKey(primaryKey) > 0;
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
    public boolean update(OSProject entity) {
        Assert.notNull(entity, "OSProject不可为空！");
        entity.setUpdateTime(new Date());
        return bizOSProjectMapper.updateByPrimaryKey(entity.getBizOsProject()) > 0;
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
    public boolean updateSelective(OSProject entity) {
        Assert.notNull(entity, "OSProject不可为空！");
        entity.setUpdateTime(new Date());
        return bizOSProjectMapper.updateByPrimaryKeySelective(entity.getBizOsProject()) > 0;
    }

    /**
     * 根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号
     *
     * @param primaryKey
     * @return
     */
    @Override
    public OSProject getByPrimaryKey(Long primaryKey) {
        Assert.notNull(primaryKey, "PrimaryKey不可为空！");
        BizOsProject entity = bizOSProjectMapper.selectByPrimaryKey(primaryKey);
        return null == entity ? null : new OSProject(entity);
    }

    /**
     * 根据实体中的属性进行查询，只能有一个返回值，有多个结果时抛出异常，查询条件使用等号
     *
     * @param entity
     * @return
     */
    @Override
    public OSProject getOneByEntity(OSProject entity) {
        Assert.notNull(entity, "OSProject不可为空！");
        BizOsProject bo = bizOSProjectMapper.selectOne(entity.getBizOsProject());
        return null == bo ? null : new OSProject(bo);
    }

    /**
     * 查询全部结果，listByEntity(null)方法能达到同样的效果
     *
     * @return
     */
    @Override
    @RedisCache
    public List<OSProject> listAll() {
        List<BizOsProject> entityList = bizOSProjectMapper.selectAll();

        if (CollectionUtils.isEmpty(entityList)) {
            return null;
        }
        List<OSProject> list = new ArrayList<>();
        for (BizOsProject entity : entityList) {
            list.add(new OSProject(entity));
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
    public List<OSProject> listByEntity(OSProject entity) {
        Assert.notNull(entity, "OSProject不可为空！");
        List<BizOsProject> entityList = bizOSProjectMapper.select(entity.getBizOsProject());
        if (CollectionUtils.isEmpty(entityList)) {
            return null;
        }
        List<OSProject> list = new ArrayList<>();
        for (BizOsProject po : entityList) {
            list.add(new OSProject(po));
        }
        return list;
    }

    @Override
    public PageInfo<OSProject> findPageBreakByCondition(OSProjectConditionVO vo) {
        PageHelper.startPage(vo.getPageNumber(), vo.getPageSize());
        List<BizOsProject> list = bizOSProjectMapper.findPageBreakByCondition(vo);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        List<OSProject> boList = new ArrayList<>();
        for (BizOsProject bizOsProject : list) {
            boList.add(new OSProject(bizOsProject));
        }
        PageInfo bean = new PageInfo<>(list);
        bean.setList(boList);
        return bean;
    }
}
