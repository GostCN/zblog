
package com.cchcz.blog.service;


import com.cchcz.blog.model.entity.UpdateRecorde;
import com.cchcz.blog.model.object.AbstractService;
import com.cchcz.blog.model.vo.UpdateRecordeConditionVO;
import com.github.pagehelper.PageInfo;

/**
 * 更新记录
 *
 * @author cchcz
 * @version 1.0

 * @date 2018/4/16 16:26
 * @since 1.0
 */
public interface SysUpdateRecordeService extends AbstractService<UpdateRecorde, Long> {

    /**
     * 分页查询
     *
     * @param vo
     * @return
     */
    PageInfo<UpdateRecorde> findPageBreakByCondition(UpdateRecordeConditionVO vo);
}
