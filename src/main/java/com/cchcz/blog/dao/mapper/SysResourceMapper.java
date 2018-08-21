
package com.cchcz.blog.dao.mapper;

import com.cchcz.blog.dao.BaseMapper;
import com.cchcz.blog.dao.beans.SysResources;
import com.cchcz.blog.model.vo.ResourceConditionVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
* @author cchcz

 * @date 2018/4/16 16:26
 * @since 1.0
 */
@Repository
public interface SysResourceMapper extends BaseMapper<SysResources> {

    /**
     * 分页查询
     *
     * @param vo
     * @return
     */
    List<SysResources> findPageBreakByCondition(ResourceConditionVO vo);

    List<SysResources> listUserResources(Map<String, Object> map);

    List<SysResources> queryResourcesListWithSelected(Long rid);

    List<SysResources> listUrlAndPermission();

    List<SysResources> listAllAvailableMenu();

    List<SysResources> listByUserId(Long userId);
}
