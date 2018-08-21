
package com.cchcz.blog.service;


import com.cchcz.blog.model.entity.Role;
import com.cchcz.blog.model.object.AbstractService;
import com.cchcz.blog.model.vo.RoleConditionVO;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * 角色
 *
 * @author cchcz
 * @version 1.0

 * @date 2018/4/16 16:26
 * @since 1.0
 */
public interface SysRoleService extends AbstractService<Role, Long> {

    /**
     * 获取ztree使用的角色列表
     *
     * @param uid
     * @return
     */
    List<Map<String, Object>> queryRoleListWithSelected(Integer uid);

    /**
     * 分页查询
     *
     * @param vo
     * @return
     */
    PageInfo<Role> findPageBreakByCondition(RoleConditionVO vo);

    /**
     * 获取用户的角色
     *
     * @param userId
     * @return
     */
    List<Role> listRolesByUserId(Long userId);
}
