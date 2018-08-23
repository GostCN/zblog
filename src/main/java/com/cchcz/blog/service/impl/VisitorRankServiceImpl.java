package com.cchcz.blog.service.impl;

import com.cchcz.blog.dao.beans.BizVisitorRank;
import com.cchcz.blog.dao.mapper.VisitorRankMapper;
import com.cchcz.blog.service.VisitorRankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * created by zhangcheng 2018/8/17
 */
@Service
@Slf4j
public class VisitorRankServiceImpl implements VisitorRankService {

    @Autowired
    private VisitorRankMapper visitorRankMapper;

    @Override
    public BizVisitorRank insert(BizVisitorRank entity) {
        return null;
    }

    @Override
    public void insertList(List<BizVisitorRank> entities) {
        Assert.notNull(entities, "entities不可为空！");
        visitorRankMapper.insertList(entities);
    }

    @Override
    public boolean removeByPrimaryKey(Long primaryKey) {
        return false;
    }

    //根据主键更新实体全部字段，null值会被更新
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(BizVisitorRank entity) {
        Assert.notNull(entity, "entities不可为空！");
        return visitorRankMapper.updateByPrimaryKey(entity) > 0;
    }

    //根据主键更新属性不为null的值
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateSelective(BizVisitorRank entity) {
        Assert.notNull(entity, "entities不可为空！");
        return visitorRankMapper.updateByPrimaryKeySelective(entity) > 0;
    }

    @Override
    public BizVisitorRank getByPrimaryKey(Long primaryKey) {
        return null;
    }

    @Override
    public BizVisitorRank getOneByEntity(BizVisitorRank entity) {
        return null;
    }

    @Override
    public List<BizVisitorRank> listAll() {
        return visitorRankMapper.selectAll();
    }

    @Override
    public List<BizVisitorRank> listByEntity(BizVisitorRank entity) {
        return null;
    }
}
