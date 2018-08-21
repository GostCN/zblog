
package com.cchcz.blog.dao.mapper;

import com.cchcz.blog.dao.BaseMapper;
import com.cchcz.blog.dao.beans.BizArticleLook;
import com.cchcz.blog.model.vo.ArticleLookConditionVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author cchcz

 * @date 2018/4/16 16:26
 * @since 1.0
 */
@Repository
public interface BizArticleLookMapper extends BaseMapper<BizArticleLook> {

    /**
     * 分页查询
     * @param vo
     *
     * @return
     */
    List<BizArticleLook> findPageBreakByCondition(ArticleLookConditionVO vo);
}
