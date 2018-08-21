
package com.cchcz.blog.service;


import com.cchcz.blog.model.object.AbstractService;
import com.cchcz.blog.model.entity.UserRole;

/**
 * 用户角色
 *
 * @author cchcz
 * @version 1.0

 * @date 2018/4/16 16:26
 * @since 1.0
 */
public interface SysUserRoleService extends AbstractService<UserRole, Long> {

    /**
     * 添加用户角色
     *
     * @param userId
     * @param roleIds
     */
    void addUserRole(Long userId, String roleIds);

    /**
     * 根据用户ID删除用户角色
     *
     * @param userId
     */
    void removeByUserId(Long userId);
}
