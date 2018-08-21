package com.cchcz.blog.service.impl;

import com.cchcz.blog.aspect.annotation.RedisCache;
import com.cchcz.blog.dao.beans.SysAttach;
import com.cchcz.blog.dao.mapper.SysAttachMapper;
import com.cchcz.blog.model.vo.SysAttachConditionVO;
import com.cchcz.blog.util.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.cchcz.blog.service.SysAttachService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SysAttachServiceImpl implements SysAttachService {

    @Resource
    private SysAttachMapper sysAttachMapper;

    @Override
    public PageInfo<SysAttach> findPageBreakByCondition(SysAttachConditionVO vo) {
        PageHelper.startPage(vo.getPageNumber(), vo.getPageSize());
        List<SysAttach> sysAttaches = sysAttachMapper.findPageBreakByCondition(vo);
        if (CollectionUtils.isEmpty(sysAttaches)) {
            return null;
        }
        PageInfo bean = new PageInfo<>(sysAttaches);
        bean.setList(sysAttaches);
        return bean;
    }

    @Override
    public SysAttach selectById(Integer id) {
        if (null != id) {
            return sysAttachMapper.selectByPrimaryKey(id);
        }
        return null;
    }

    @Override
    @Transactional
    public void save(String fname, String fkey, String ftype, Integer author) {
        SysAttach attach = new SysAttach();
        attach.setFname(fname);
        attach.setAuthorId(author);
        attach.setFkey(fkey);
        attach.setFtype(ftype);
        attach.setCreated(DateUtil.getCurrentUnixTime());
        sysAttachMapper.insertSelective(attach);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        if (null != id) {
            sysAttachMapper.deleteByPrimaryKey(id);
        }
    }

    @Override
    public List<String> listMaterial() {
        SysAttach sysAttach = new SysAttach();
        sysAttach.setFtype("image");
        List<SysAttach> attachList = sysAttachMapper.select(sysAttach);
        return attachList.stream().map(attach -> attach.getFkey()).collect(Collectors.toList());
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
    public boolean updateSelective(SysAttach entity) {
        Assert.notNull(entity, "entity不可为空！");
        return sysAttachMapper.updateByPrimaryKeySelective(entity) > 0;
    }

}
