package com.cchcz.blog.dao.mapper;

import com.cchcz.blog.dao.BaseMapper;
import com.cchcz.blog.dao.beans.BizNav;
import com.cchcz.blog.model.vo.NavConditionVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <ClassName>BizNavMapper</ClassName>
 * <Description></Description>
 *
 * @Author cchcz
 * @Date 2018年07月02日 23:34
 */
@Repository
public interface BizNavMapper extends BaseMapper<BizNav> {
    /**
     * 分页查询，关联查询文章标签、文章类型
     *
     * @param vo
     * @return
     */
    List<BizNav> findPageBreakByCondition(NavConditionVO vo);
}
