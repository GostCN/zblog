
package com.cchcz.blog.service;


import com.cchcz.blog.model.entity.Type;
import com.cchcz.blog.model.object.AbstractService;
import com.cchcz.blog.model.vo.TypeConditionVO;
import com.github.pagehelper.PageInfo;

/**
 * 分类
 *
 * @author cchcz
 * @version 1.0

 * @date 2018/4/16 16:26
 * @since 1.0
 */
public interface BizTypeService extends AbstractService<Type, Long> {

    /**
     * 分页查询
     *
     * @param vo
     * @return
     */
    PageInfo<Type> findPageBreakByCondition(TypeConditionVO vo);
}
