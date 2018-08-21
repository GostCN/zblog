package com.cchcz.blog.service.impl;

import com.cchcz.blog.aspect.annotation.RedisCache;
import com.cchcz.blog.dao.beans.BizBook;
import com.cchcz.blog.dao.mapper.BizBookMapper;
import com.cchcz.blog.model.entity.Book;
import com.cchcz.blog.model.vo.BookConditionVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.cchcz.blog.service.BizBookService;
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
public class BizBookServiceImpl implements BizBookService {

    @Autowired
    private BizBookMapper bizBookMapper;

    /**
     * 保存一个实体，null的属性不会保存，会使用数据库默认值
     *
     * @param entity
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @RedisCache(flush = true)
    public Book insert(Book entity) {
        Assert.notNull(entity, "Book不可为空！");
        entity.setUpdateTime(new Date());
        entity.setCreateTime(new Date());
        bizBookMapper.insertSelective(entity.getBizBook());
        return entity;
    }

    /**
     * 批量插入，支持批量插入的数据库可以使用，例如MySQL,H2等，另外该接口限制实体包含id属性并且必须为自增列
     *
     * @param entities
     */
    @Override
    @RedisCache(flush = true)
    public void insertList(List<Book> entities) {
        Assert.notNull(entities, "Books不可为空！");
        List<BizBook> list = new ArrayList<>();
        for (Book entity : entities) {
            entity.setUpdateTime(new Date());
            entity.setCreateTime(new Date());
            list.add(entity.getBizBook());
        }
        bizBookMapper.insertList(list);
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
        return bizBookMapper.deleteByPrimaryKey(primaryKey) > 0;
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
    public boolean update(Book entity) {
        Assert.notNull(entity, "Book不可为空！");
        entity.setUpdateTime(new Date());
        return bizBookMapper.updateByPrimaryKey(entity.getBizBook()) > 0;
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
    public boolean updateSelective(Book entity) {
        Assert.notNull(entity, "Book不可为空！");
        entity.setUpdateTime(new Date());
        return bizBookMapper.updateByPrimaryKeySelective(entity.getBizBook()) > 0;
    }

    /**
     * 根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号
     *
     * @param primaryKey
     * @return
     */
    @Override
    public Book getByPrimaryKey(Long primaryKey) {
        Assert.notNull(primaryKey, "PrimaryKey不可为空！");
        BizBook entity = bizBookMapper.selectByPrimaryKey(primaryKey);
        return null == entity ? null : new Book(entity);
    }

    /**
     * 根据实体中的属性进行查询，只能有一个返回值，有多个结果时抛出异常，查询条件使用等号
     *
     * @param entity
     * @return
     */
    @Override
    public Book getOneByEntity(Book entity) {
        Assert.notNull(entity, "Book不可为空！");
        BizBook bo = bizBookMapper.selectOne(entity.getBizBook());
        return null == bo ? null : new Book(bo);
    }

    /**
     * 查询全部结果，listByEntity(null)方法能达到同样的效果
     *
     * @return
     */
    @Override
    @RedisCache
    public List<Book> listAll() {
        List<BizBook> entityList = bizBookMapper.selectAll();

        if (CollectionUtils.isEmpty(entityList)) {
            return null;
        }
        List<Book> list = new ArrayList<>();
        for (BizBook entity : entityList) {
            list.add(new Book(entity));
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
    public List<Book> listByEntity(Book entity) {
        Assert.notNull(entity, "Book不可为空！");
        List<BizBook> entityList = bizBookMapper.select(entity.getBizBook());
        if (CollectionUtils.isEmpty(entityList)) {
            return null;
        }
        List<Book> list = new ArrayList<>();
        for (BizBook po : entityList) {
            list.add(new Book(po));
        }
        return list;
    }

    @Override
    public PageInfo<Book> findPageBreakByCondition(BookConditionVO vo) {
        PageHelper.startPage(vo.getPageNumber(), vo.getPageSize());
        List<BizBook> list = bizBookMapper.findPageBreakByCondition(vo);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        List<Book> boList = new ArrayList<>();
        for (BizBook bizBook : list) {
            boList.add(new Book(bizBook));
        }
        PageInfo bean = new PageInfo<>(list);
        bean.setList(boList);
        return bean;
    }

}
