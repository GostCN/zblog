package com.cchcz.blog.service.impl;

import com.cchcz.blog.dao.beans.SysSpiderConfig;
import com.cchcz.blog.dao.mapper.SysSpiderConfigMapper;
import com.cchcz.blog.model.entity.SpiderConfig;
import com.cchcz.blog.service.SysSpiderConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <ClassName>SysSpiderConfigServiceImpl</ClassName>
 * <Description></Description>
 *
 * @Author cchcz
 * @Date 2018年06月30日 22:29
 */
@Service
public class SysSpiderConfigServiceImpl implements SysSpiderConfigService {
    @Autowired
    private SysSpiderConfigMapper sysSpiderConfigMapper;


    /**
     * 保存一个实体，null的属性不会保存，会使用数据库默认值
     *
     * @param entity
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SpiderConfig insert(SpiderConfig entity) {
        Assert.notNull(entity, "entity不可为空！");
        sysSpiderConfigMapper.insertSelective(entity.getSysSpiderConfig());
        return entity;
    }

    /**
     * 批量插入，支持批量插入的数据库可以使用，例如MySQL,H2等，另外该接口限制实体包含id属性并且必须为自增列
     *
     * @param entities
     */
    @Override
    public void insertList(List<SpiderConfig> entities) {
        Assert.notNull(entities, "entities不可为空！");
        List<SysSpiderConfig> list = new ArrayList<>();
        for (SpiderConfig entity: entities) {
            list.add(entity.getSysSpiderConfig());
        }
        sysSpiderConfigMapper.insertList(list);
    }

    /**
     * 根据主键字段进行删除，方法参数必须包含完整的主键属性
     *
     * @param primaryKey
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByPrimaryKey(Long primaryKey) {
        return sysSpiderConfigMapper.deleteByPrimaryKey(primaryKey) > 0;
    }

    /**
     * 根据主键更新实体全部字段，null值会被更新
     *
     * @param entity
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(SpiderConfig entity) {
        Assert.notNull(entity, "SpiderConfig不可为空！");
        return sysSpiderConfigMapper.updateByPrimaryKey(entity.getSysSpiderConfig()) > 0;
    }

    /**
     * 根据主键更新属性不为null的值
     *
     * @param entity
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateSelective(SpiderConfig entity) {
        Assert.notNull(entity, "SpiderConfig不可为空！");
        return sysSpiderConfigMapper.updateByPrimaryKeySelective(entity.getSysSpiderConfig()) > 0;
    }

    /**
     * 根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号
     *
     * @param primaryKey
     * @return
     */
    @Override
    public SpiderConfig getByPrimaryKey(Long primaryKey) {
        Assert.notNull(primaryKey, "PrimaryKey不可为空！");
        SysSpiderConfig entity = sysSpiderConfigMapper.selectByPrimaryKey(primaryKey);
        return null == entity ? null : new SpiderConfig(entity);
    }

    /**
     * 根据实体中的属性进行查询，只能有一个返回值，有多个结果时抛出异常，查询条件使用等号
     *
     * @param entity
     * @return
     */
    @Override
    public SpiderConfig getOneByEntity(SpiderConfig entity) {
        Assert.notNull(entity, "SpiderConfig不可为空！");
        SysSpiderConfig bo = sysSpiderConfigMapper.selectOne(entity.getSysSpiderConfig());
        return null == bo ? null : new SpiderConfig(bo);
    }

    /**
     * 查询全部结果，listByEntity(null)方法能达到同样的效果
     *
     * @return
     */
    @Override
    public List<SpiderConfig> listAll() {
        List<SysSpiderConfig> entityList = sysSpiderConfigMapper.selectAll();

        if (CollectionUtils.isEmpty(entityList)) {
            return null;
        }
        List<SpiderConfig> list = new ArrayList<>();
        for (SysSpiderConfig entity: entityList) {
            list.add(new SpiderConfig(entity));
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
    public List<SpiderConfig> listByEntity(SpiderConfig entity) {
        Assert.notNull(entity, "SpiderConfig不可为空！");
        List<SysSpiderConfig> entityList = sysSpiderConfigMapper.select(entity.getSysSpiderConfig());
        if (CollectionUtils.isEmpty(entityList)) {
            return null;
        }
        List<SpiderConfig> list = new ArrayList<>();
        for (SysSpiderConfig po: entityList) {
            list.add(new SpiderConfig(po));
        }
        return list;
    }
}
