package com.cchcz.blog.dao.mapper;

import com.cchcz.blog.dao.BaseMapper;
import com.cchcz.blog.dao.beans.BizTool;
import com.cchcz.blog.model.vo.ToolConditionVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <ClassName>BizToolMapper</ClassName>
 * <Description></Description>
 *
 * @Author cchcz
 * @Date 2018年07月02日 23:34
 */
@Repository
public interface BizToolMapper extends BaseMapper<BizTool> {
    /**
     * 分页查询，关联查询文章标签、文章类型
     *
     * @param vo
     * @return
     */
    List<BizTool> findPageBreakByCondition(ToolConditionVO vo);
}
