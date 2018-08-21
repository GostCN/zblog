
package com.cchcz.blog.dao.mapper;

import com.cchcz.blog.dao.BaseMapper;
import com.cchcz.blog.dao.beans.SysUpdateRecorde;
import com.cchcz.blog.model.vo.UpdateRecordeConditionVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author cchcz

 * @date 2018/4/16 16:26
 * @since 1.0
 */
@Repository
public interface SysUpdateRecordeMapper extends BaseMapper<SysUpdateRecorde> {

    /**
     * 分页查询
     * @param vo
     *
     * @return
     */
    List<SysUpdateRecorde> findPageBreakByCondition(UpdateRecordeConditionVO vo);
}
