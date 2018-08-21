
package com.cchcz.blog.service;


import com.cchcz.blog.model.entity.User;
import com.cchcz.blog.model.entity.UserPwd;
import com.cchcz.blog.model.object.AbstractService;
import com.cchcz.blog.model.vo.UserConditionVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 用户
 *
 * @author cchcz
 * @version 1.0

 * @date 2018/4/16 16:26
 * @since 1.0
 */
public interface SysUserService extends AbstractService<User, Long> {

    /**
     * 分页查询
     *
     * @param vo
     * @return
     */
    PageInfo<User> findPageBreakByCondition(UserConditionVO vo);

    /**
     * 更新用户最后一次登录的状态信息
     *
     * @param user
     * @return
     */
    User updateUserLastLoginInfo(User user);

    /**
     * 根据用户名查找
     *
     * @param userName
     * @return
     */
    User getByUserName(String userName);

    /**
     * 通过角色Id获取用户列表
     *
     * @param roleId
     * @return
     */
    List<User> listByRoleId(Long roleId);

    /**
     * 修改密码
     *
     * @param userPwd
     * @return
     */
    boolean updatePwd(UserPwd userPwd) throws Exception;


    /**
     * 用戶登录
     * @param username
     * @param password
     * @return
     */
    User login(String username, String password) throws Exception;
}
