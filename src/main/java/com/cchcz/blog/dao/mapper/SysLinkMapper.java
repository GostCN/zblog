
package com.cchcz.blog.dao.mapper;

import com.cchcz.blog.dao.BaseMapper;
import com.cchcz.blog.dao.beans.SysLink;
import com.cchcz.blog.model.vo.LinkConditionVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author cchcz

 * @date 2018/4/16 16:26
 * @since 1.0
 */
@Repository
public interface SysLinkMapper extends BaseMapper<SysLink> {

    /**
     * 分页查询
     * @param vo
     *
     * @return
     */
    List<SysLink> findPageBreakByCondition(LinkConditionVO vo);
}
