package com.cchcz.blog.dao.mapper;

import com.cchcz.blog.dao.BaseMapper;
import com.cchcz.blog.dao.beans.BizNavType;
import com.cchcz.blog.model.vo.NavTypeConditionVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <ClassName>BizNavTypeMapper</ClassName>
 * <Description></Description>
 *
 * @Author cchcz
 * @Date 2018年07月02日 23:34
 */
@Repository
public interface BizNavTypeMapper extends BaseMapper<BizNavType> {
    /**
     * 分页查询，关联查询文章标签、文章类型
     *
     * @param vo
     * @return
     */
    List<BizNavType> findPageBreakByCondition(NavTypeConditionVO vo);
}
