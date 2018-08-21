package com.cchcz.blog.dao.mapper;


import com.cchcz.blog.dao.BaseMapper;
import com.cchcz.blog.dao.beans.SysSequence;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

/**
 * sequeuece表
 * t_sequence
 */
@Repository
public interface SysSequenceMapper extends BaseMapper<SysSequence> {
    int updateCAS(HashMap<String, Object> param);
}

