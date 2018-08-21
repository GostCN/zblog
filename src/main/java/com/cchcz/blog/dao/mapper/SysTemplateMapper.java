
package com.cchcz.blog.dao.mapper;

import com.cchcz.blog.dao.BaseMapper;
import com.cchcz.blog.dao.beans.SysTemplate;
import com.cchcz.blog.model.vo.TemplateConditionVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author cchcz

 * @date 2018/4/16 16:26
 * @since 1.0
 */
@Repository
public interface SysTemplateMapper extends BaseMapper<SysTemplate> {

    /**
     * 分页查询
     * @param vo
     *
     * @return
     */
    List<SysTemplate> findPageBreakByCondition(TemplateConditionVO vo);
}
