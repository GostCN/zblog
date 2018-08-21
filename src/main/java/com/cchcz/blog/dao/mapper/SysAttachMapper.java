package com.cchcz.blog.dao.mapper;

import com.cchcz.blog.dao.BaseMapper;
import com.cchcz.blog.dao.beans.SysAttach;
import com.cchcz.blog.model.vo.SysAttachConditionVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysAttachMapper extends BaseMapper<SysAttach> {
    List<SysAttach> findPageBreakByCondition(SysAttachConditionVO vo);
}