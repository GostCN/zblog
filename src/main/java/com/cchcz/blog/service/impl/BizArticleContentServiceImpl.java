package com.cchcz.blog.service.impl;

import com.cchcz.blog.dao.beans.BizArticleContent;
import com.cchcz.blog.dao.mapper.BizArticleContentMapper;
import com.cchcz.blog.model.entity.ArticleContent;
import com.cchcz.blog.service.BizArticleContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <ClassName>BizArticleContentServiceImpl</ClassName>
 * <Description></Description>
 *
 * @Author cchcz
 * @Date 2018年06月30日 18:42
 */
@Service
public class BizArticleContentServiceImpl implements BizArticleContentService {
    @Autowired
    private BizArticleContentMapper bizArticleContentMapper;


    /**
     * 保存一个实体，null的属性不会保存，会使用数据库默认值
     *
     * @param entity
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ArticleContent insert(ArticleContent entity) {
        Assert.notNull(entity, "entity不可为空！");
        bizArticleContentMapper.insertSelective(entity.getBizArticleContent());
        return entity;
    }

    /**
     * 批量插入，支持批量插入的数据库可以使用，例如MySQL,H2等，另外该接口限制实体包含id属性并且必须为自增列
     *
     * @param entities
     */
    @Override
    public void insertList(List<ArticleContent> entities) {
        Assert.notNull(entities, "entities不可为空！");
        List<BizArticleContent> list = new ArrayList<>();
        for (ArticleContent entity : entities) {
            list.add(entity.getBizArticleContent());
        }
        bizArticleContentMapper.insertList(list);
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
        return bizArticleContentMapper.deleteByPrimaryKey(primaryKey) > 0;
    }

    /**
     * 根据主键更新实体全部字段，null值会被更新
     *
     * @param entity
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(ArticleContent entity) {
        Assert.notNull(entity, "ArticleContent不可为空！");
        return bizArticleContentMapper.updateByPrimaryKey(entity.getBizArticleContent()) > 0;
    }

    /**
     * 根据主键更新属性不为null的值
     *
     * @param entity
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateSelective(ArticleContent entity) {
        Assert.notNull(entity, "ArticleContent不可为空！");
        return bizArticleContentMapper.updateByPrimaryKeySelective(entity.getBizArticleContent()) > 0;
    }

    /**
     * 根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号
     *
     * @param primaryKey
     * @return
     */
    @Override
    public ArticleContent getByPrimaryKey(Long primaryKey) {
        Assert.notNull(primaryKey, "PrimaryKey不可为空！");
        BizArticleContent entity = bizArticleContentMapper.selectByPrimaryKey(primaryKey);
        return null == entity ? null : new ArticleContent(entity);
    }

    /**
     * 根据实体中的属性进行查询，只能有一个返回值，有多个结果时抛出异常，查询条件使用等号
     *
     * @param entity
     * @return
     */
    @Override
    public ArticleContent getOneByEntity(ArticleContent entity) {
        Assert.notNull(entity, "ArticleContent不可为空！");
        BizArticleContent bo = bizArticleContentMapper.selectOne(entity.getBizArticleContent());
        return null == bo ? null : new ArticleContent(bo);
    }

    /**
     * 查询全部结果，listByEntity(null)方法能达到同样的效果
     *
     * @return
     */
    @Override
    public List<ArticleContent> listAll() {
        List<BizArticleContent> entityList = bizArticleContentMapper.selectAll();

        if (CollectionUtils.isEmpty(entityList)) {
            return null;
        }
        List<ArticleContent> list = new ArrayList<>();
        for (BizArticleContent entity : entityList) {
            list.add(new ArticleContent(entity));
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
    public List<ArticleContent> listByEntity(ArticleContent entity) {
        Assert.notNull(entity, "ArticleContent不可为空！");
        List<BizArticleContent> entityList = bizArticleContentMapper.select(entity.getBizArticleContent());
        if (CollectionUtils.isEmpty(entityList)) {
            return null;
        }
        List<ArticleContent> list = new ArrayList<>();
        for (BizArticleContent po : entityList) {
            list.add(new ArticleContent(po));
        }
        return list;
    }
}
