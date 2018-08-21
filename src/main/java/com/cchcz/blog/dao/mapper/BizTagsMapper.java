
package com.cchcz.blog.dao.mapper;

import com.cchcz.blog.dao.BaseMapper;
import com.cchcz.blog.dao.beans.BizTags;
import com.cchcz.blog.model.vo.TagsConditionVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author cchcz

 * @date 2018/4/16 16:26
 * @since 1.0
 */
@Repository
public interface BizTagsMapper extends BaseMapper<BizTags> {

    /**
     * 分页查询
     * @param vo
     *
     * @return
     */
    List<BizTags> findPageBreakByCondition(TagsConditionVO vo);
}
