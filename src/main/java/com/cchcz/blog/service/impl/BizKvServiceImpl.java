
package com.cchcz.blog.service.impl;

import com.cchcz.blog.aspect.annotation.RedisCache;
import com.cchcz.blog.dao.beans.BizKv;
import com.cchcz.blog.dao.mapper.BizKvMapper;
import com.cchcz.blog.model.entity.Kv;
import com.cchcz.blog.model.vo.KvConditionVO;
import com.cchcz.blog.service.BizKvService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 标签
 *
 * @author cchcz
 * @version 1.0
 * @date 2018/4/16 16:26
 * @since 1.0
 */
@Service
public class BizKvServiceImpl implements BizKvService {

    @Autowired
    private BizKvMapper bizKvMapper;

    /**
     * 分页查询
     *
     * @param vo
     * @return
     */
    @Override
    public PageInfo<Kv> findPageBreakByCondition(KvConditionVO vo) {
        PageHelper.startPage(vo.getPageNumber(), vo.getPageSize());
        List<BizKv> list = bizKvMapper.findPageBreakByCondition(vo);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        List<Kv> boList = new ArrayList<>();
        for (BizKv bizKv : list) {
            boList.add(new Kv(bizKv));
        }
        PageInfo bean = new PageInfo<BizKv>(list);
        bean.setList(boList);
        return bean;
    }

    @Override
    @RedisCache(expire = 10, unit = TimeUnit.MINUTES)
    public String getValue(String key) {
        KvConditionVO kvConditionVO = new KvConditionVO();
        kvConditionVO.setPkey(key);
        List<BizKv> pageBreakByCondition = bizKvMapper.findPageBreakByCondition(kvConditionVO);
        return pageBreakByCondition == null || pageBreakByCondition.isEmpty() ? "" : pageBreakByCondition.get(0).getPvalue();
    }

    /**
     * 保存一个实体，null的属性不会保存，会使用数据库默认值
     *
     * @param entity
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @RedisCache(flush = true)
    public Kv insert(Kv entity) {
        Assert.notNull(entity, "KV不可为空！");
        entity.setUpdateTime(new Date());
        entity.setCreateTime(new Date());
        bizKvMapper.insertSelective(entity.getBizKv());
        return entity;
    }

    /**
     * 批量插入，支持批量插入的数据库可以使用，例如MySQL,H2等，另外该接口限制实体包含id属性并且必须为自增列
     *
     * @param entities
     */
    @Override
    @RedisCache(flush = true)
    public void insertList(List<Kv> entities) {
        Assert.notNull(entities, "KVs不可为空！");
        List<BizKv> list = new ArrayList<>();
        for (Kv entity : entities) {
            entity.setUpdateTime(new Date());
            entity.setCreateTime(new Date());
            list.add(entity.getBizKv());
        }
        bizKvMapper.insertList(list);
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
        return bizKvMapper.deleteByPrimaryKey(primaryKey) > 0;
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
    public boolean update(Kv entity) {
        Assert.notNull(entity, "KV不可为空！");
        entity.setUpdateTime(new Date());
        return bizKvMapper.updateByPrimaryKey(entity.getBizKv()) > 0;
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
    public boolean updateSelective(Kv entity) {
        Assert.notNull(entity, "KV不可为空！");
        entity.setUpdateTime(new Date());
        return bizKvMapper.updateByPrimaryKeySelective(entity.getBizKv()) > 0;
    }

    /**
     * 根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号
     *
     * @param primaryKey
     * @return
     */
    @Override
    public Kv getByPrimaryKey(Long primaryKey) {
        Assert.notNull(primaryKey, "PrimaryKey不可为空！");
        BizKv entity = bizKvMapper.selectByPrimaryKey(primaryKey);
        return null == entity ? null : new Kv(entity);
    }

    /**
     * 根据实体中的属性进行查询，只能有一个返回值，有多个结果时抛出异常，查询条件使用等号
     *
     * @param entity
     * @return
     */
    @Override
    public Kv getOneByEntity(Kv entity) {
        Assert.notNull(entity, "KV不可为空！");
        BizKv bo = bizKvMapper.selectOne(entity.getBizKv());
        return null == bo ? null : new Kv(bo);
    }

    /**
     * 查询全部结果，listByEntity(null)方法能达到同样的效果
     *
     * @return
     */
    @Override
    @RedisCache
    public List<Kv> listAll() {
        List<BizKv> entityList = bizKvMapper.selectAll();

        if (CollectionUtils.isEmpty(entityList)) {
            return null;
        }
        List<Kv> list = new ArrayList<>();
        for (BizKv entity : entityList) {
            list.add(new Kv(entity));
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
    public List<Kv> listByEntity(Kv entity) {
        Assert.notNull(entity, "KV不可为空！");
        List<BizKv> entityList = bizKvMapper.select(entity.getBizKv());
        if (CollectionUtils.isEmpty(entityList)) {
            return null;
        }
        List<Kv> list = new ArrayList<>();
        for (BizKv po : entityList) {
            list.add(new Kv(po));
        }
        return list;
    }

}
