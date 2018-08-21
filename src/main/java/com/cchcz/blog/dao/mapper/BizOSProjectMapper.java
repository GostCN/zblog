package com.cchcz.blog.dao.mapper;

import com.cchcz.blog.dao.BaseMapper;
import com.cchcz.blog.dao.beans.BizOsProject;
import com.cchcz.blog.model.vo.OSProjectConditionVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <ClassName>BizOSProjectMapper</ClassName>
 * <Description></Description>
 *
 * @Author cchcz
 * @Date 2018年07月02日 23:34
 */
@Repository
public interface BizOSProjectMapper extends BaseMapper<BizOsProject> {
    /**
     * 分页查询，关联查询文章标签、文章类型
     *
     * @param vo
     * @return
     */
    List<BizOsProject> findPageBreakByCondition(OSProjectConditionVO vo);
}
