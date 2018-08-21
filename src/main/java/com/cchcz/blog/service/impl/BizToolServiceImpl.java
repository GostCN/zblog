
package com.cchcz.blog.service.impl;

import com.cchcz.blog.aspect.annotation.RedisCache;
import com.cchcz.blog.dao.beans.BizTool;
import com.cchcz.blog.dao.mapper.BizToolMapper;
import com.cchcz.blog.model.entity.Tool;
import com.cchcz.blog.model.vo.ToolConditionVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.cchcz.blog.service.BizToolService;
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
public class BizToolServiceImpl implements BizToolService {

    @Autowired
    private BizToolMapper bizToolMapper;

    /**
     * 保存一个实体，null的属性不会保存，会使用数据库默认值
     *
     * @param entity
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @RedisCache(flush = true)
    public Tool insert(Tool entity) {
        Assert.notNull(entity, "Tool不可为空！");
        entity.setUpdateTime(new Date());
        entity.setCreateTime(new Date());
        bizToolMapper.insertSelective(entity.getBizTool());
        return entity;
    }

    /**
     * 批量插入，支持批量插入的数据库可以使用，例如MySQL,H2等，另外该接口限制实体包含id属性并且必须为自增列
     *
     * @param entities
     */
    @Override
    @RedisCache(flush = true)
    public void insertList(List<Tool> entities) {
        Assert.notNull(entities, "Tools不可为空！");
        List<BizTool> list = new ArrayList<>();
        for (Tool entity : entities) {
            entity.setUpdateTime(new Date());
            entity.setCreateTime(new Date());
            list.add(entity.getBizTool());
        }
        bizToolMapper.insertList(list);
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
        return bizToolMapper.deleteByPrimaryKey(primaryKey) > 0;
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
    public boolean update(Tool entity) {
        Assert.notNull(entity, "Tool不可为空！");
        entity.setUpdateTime(new Date());
        return bizToolMapper.updateByPrimaryKey(entity.getBizTool()) > 0;
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
    public boolean updateSelective(Tool entity) {
        Assert.notNull(entity, "Tool不可为空！");
        entity.setUpdateTime(new Date());
        return bizToolMapper.updateByPrimaryKeySelective(entity.getBizTool()) > 0;
    }

    /**
     * 根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号
     *
     * @param primaryKey
     * @return
     */
    @Override
    public Tool getByPrimaryKey(Long primaryKey) {
        Assert.notNull(primaryKey, "PrimaryKey不可为空！");
        BizTool entity = bizToolMapper.selectByPrimaryKey(primaryKey);
        return null == entity ? null : new Tool(entity);
    }

    /**
     * 根据实体中的属性进行查询，只能有一个返回值，有多个结果时抛出异常，查询条件使用等号
     *
     * @param entity
     * @return
     */
    @Override
    public Tool getOneByEntity(Tool entity) {
        Assert.notNull(entity, "Tool不可为空！");
        BizTool bo = bizToolMapper.selectOne(entity.getBizTool());
        return null == bo ? null : new Tool(bo);
    }

    /**
     * 查询全部结果，listByEntity(null)方法能达到同样的效果
     *
     * @return
     */
    @Override
    @RedisCache
    public List<Tool> listAll() {
        List<BizTool> entityList = bizToolMapper.selectAll();

        if (CollectionUtils.isEmpty(entityList)) {
            return null;
        }
        List<Tool> list = new ArrayList<>();
        for (BizTool entity : entityList) {
            list.add(new Tool(entity));
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
    public List<Tool> listByEntity(Tool entity) {
        Assert.notNull(entity, "Tool不可为空！");
        List<BizTool> entityList = bizToolMapper.select(entity.getBizTool());
        if (CollectionUtils.isEmpty(entityList)) {
            return null;
        }
        List<Tool> list = new ArrayList<>();
        for (BizTool po : entityList) {
            list.add(new Tool(po));
        }
        return list;
    }

    @Override
    public PageInfo<Tool> findPageBreakByCondition(ToolConditionVO vo) {
        PageHelper.startPage(vo.getPageNumber(), vo.getPageSize());
        List<BizTool> list = bizToolMapper.findPageBreakByCondition(vo);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        List<Tool> boList = new ArrayList<>();
        for (BizTool BizTool : list) {
            boList.add(new Tool(BizTool));
        }
        PageInfo bean = new PageInfo<>(list);
        bean.setList(boList);
        return bean;
    }

}
