
package com.cchcz.blog.service.impl;

import com.cchcz.blog.dao.beans.SysUser;
import com.cchcz.blog.dao.mapper.SysUserMapper;
import com.cchcz.blog.exception.BlogException;
import com.cchcz.blog.model.entity.User;
import com.cchcz.blog.model.entity.UserPwd;
import com.cchcz.blog.model.enums.UserNotificationEnum;
import com.cchcz.blog.model.enums.UserPrivacyEnum;
import com.cchcz.blog.model.enums.UserStatusEnum;
import com.cchcz.blog.model.vo.UserConditionVO;
import com.cchcz.blog.util.IpUtil;
import com.cchcz.blog.util.PasswordUtil;
import com.cchcz.blog.util.RequestUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.cchcz.blog.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 用户
 *
 * @author cchcz
 * @version 1.0
 * @date 2018/4/16 16:26
 * @since 1.0
 */
@Service
@Slf4j
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 用户登录次数计数  redisKey 前缀
     */
    private static final String USER_LOGIN_COUNT = "user_login_count_";
    /**
     * 用户登录是否被锁定    一小时 redisKey 前缀
     */
    private static final String USER_IS_LOCK = "user_is_lock";


    @Override
    @Transactional(rollbackFor = Exception.class)
    public User insert(User user) {
        Assert.notNull(user, "User不可为空！");
        user.setUpdateTime(new Date());
        user.setCreateTime(new Date());
        user.setRegIp(IpUtil.getRealIp(RequestUtil.getRequest()));
        user.setPrivacy(UserPrivacyEnum.PUBLIC.getCode());
        user.setNotification(UserNotificationEnum.DETAIL);
        user.setStatus(UserStatusEnum.NORMAL.getCode());
        sysUserMapper.insertSelective(user.getSysUser());
        return user;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertList(List<User> users) {
        Assert.notNull(users, "Users不可为空！");
        List<SysUser> sysUsers = new ArrayList<>();
        String regIp = IpUtil.getRealIp(RequestUtil.getRequest());
        for (User user : users) {
            user.setUpdateTime(new Date());
            user.setCreateTime(new Date());
            user.setRegIp(regIp);
            user.setPrivacy(UserPrivacyEnum.PUBLIC.getCode());
            user.setNotification(UserNotificationEnum.DETAIL);
            sysUsers.add(user.getSysUser());
        }
        sysUserMapper.insertList(sysUsers);
    }

    /**
     * 根据主键字段进行删除，方法参数必须包含完整的主键属性
     *
     * @param primaryKey
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByPrimaryKey(Long primaryKey) {
        return sysUserMapper.deleteByPrimaryKey(primaryKey) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(User user) {
        Assert.notNull(user, "User不可为空！");
        user.setUpdateTime(new Date());
        if (!StringUtils.isEmpty(user.getPassword())) {
            try {
                user.setPassword(PasswordUtil.encrypt(user.getPassword(), user.getUsername()));
            } catch (Exception e) {
                throw new BlogException("密码加密失败", e);
            }
        }
        return sysUserMapper.updateByPrimaryKey(user.getSysUser()) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateSelective(User user) {
        Assert.notNull(user, "User不可为空！");
        user.setUpdateTime(new Date());
        if (!StringUtils.isEmpty(user.getPassword())) {
            try {
                user.setPassword(PasswordUtil.encrypt(user.getPassword(), user.getUsername()));
            } catch (Exception e) {
                throw new BlogException("密码加密失败", e);
            }
        } else {
            user.setPassword(null);
        }
        return sysUserMapper.updateByPrimaryKeySelective(user.getSysUser()) > 0;
    }

    /**
     * 根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号
     *
     * @param primaryKey
     * @return
     */

    @Override
    public User getByPrimaryKey(Long primaryKey) {
        Assert.notNull(primaryKey, "PrimaryKey不可为空！");
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(primaryKey);
        return null == sysUser ? null : new User(sysUser);
    }

    /**
     * 根据实体中的属性进行查询，只能有一个返回值，有多个结果时抛出异常，查询条件使用等号
     *
     * @param entity
     * @return
     */
    @Override
    public User getOneByEntity(User entity) {
        Assert.notNull(entity, "User不可为空！");
        SysUser sysUser = sysUserMapper.selectOne(entity.getSysUser());
        return null == sysUser ? null : new User(sysUser);
    }

    @Override
    public List<User> listAll() {
        List<SysUser> sysUsers = sysUserMapper.selectAll();

        if (CollectionUtils.isEmpty(sysUsers)) {
            return null;
        }
        List<User> users = new ArrayList<>();
        for (SysUser sysUser : sysUsers) {
            users.add(new User(sysUser));
        }
        return users;
    }

    @Override
    public List<User> listByEntity(User user) {
        Assert.notNull(user, "User不可为空！");
        List<SysUser> sysUsers = sysUserMapper.select(user.getSysUser());
        if (CollectionUtils.isEmpty(sysUsers)) {
            return null;
        }
        List<User> users = new ArrayList<>();
        for (SysUser su : sysUsers) {
            users.add(new User(su));
        }
        return users;
    }

    /**
     * 分页查询
     *
     * @param vo
     * @return
     */
    @Override
    public PageInfo<User> findPageBreakByCondition(UserConditionVO vo) {
        PageHelper.startPage(vo.getPageNumber(), vo.getPageSize());
        List<SysUser> sysUsers = sysUserMapper.findPageBreakByCondition(vo);
        if (CollectionUtils.isEmpty(sysUsers)) {
            return null;
        }
        List<User> users = new ArrayList<>();
        for (SysUser su : sysUsers) {
            users.add(new User(su));
        }
        PageInfo bean = new PageInfo<SysUser>(sysUsers);
        bean.setList(users);
        return bean;
    }

    /**
     * 更新用户最后一次登录的状态信息
     *
     * @param user
     * @return
     */
    @Override
    public User updateUserLastLoginInfo(User user) {
        if (user != null) {
            user.setLoginCount(user.getLoginCount() + 1);
            user.setLastLoginTime(new Date());
            user.setLastLoginIp(IpUtil.getRealIp(RequestUtil.getRequest()));
            user.setPassword(null);
            this.updateSelective(user);
        }
        return user;
    }

    /**
     * 根据用户名查找
     *
     * @param userName
     * @return
     */
    @Override
    public User getByUserName(String userName) {
        User user = new User(userName, null);
        return getOneByEntity(user);
    }

    /**
     * 通过角色Id获取用户列表
     *
     * @param roleId
     * @return
     */
    @Override
    public List<User> listByRoleId(Long roleId) {
        List<SysUser> sysUsers = sysUserMapper.listByRoleId(roleId);
        if (CollectionUtils.isEmpty(sysUsers)) {
            return null;
        }
        List<User> users = new ArrayList<>();
        for (SysUser su : sysUsers) {
            users.add(new User(su));
        }
        return users;
    }

    /**
     * 修改密码
     *
     * @param userPwd
     * @return
     */
    @Override
    public boolean updatePwd(UserPwd userPwd) throws Exception {
        if (!userPwd.getNewPassword().equals(userPwd.getNewPasswordRepeat())) {
            throw new BlogException("新密码不一致！");
        }
        User user = this.getByPrimaryKey(userPwd.getId());
        if (null == user) {
            throw new BlogException("用户编号错误！请不要手动操作用户ID！");
        }

        if (!user.getPassword().equals(PasswordUtil.encrypt(userPwd.getPassword(), user.getUsername()))) {
            throw new BlogException("原密码不正确！");
        }
        user.setPassword(userPwd.getNewPassword());

        return this.updateSelective(user);
    }

    @Override
    public User login(String username, String password) throws Exception {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            throw new IllegalArgumentException("用户名和密码不能为空");
        }
        // 访问一次，计数一次
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        String loginCountKey = USER_LOGIN_COUNT + username;
        String isLockKey = USER_IS_LOCK + username;
        if (redisTemplate.hasKey(isLockKey)) {
            throw new BlogException("帐号[" + username + "]已被禁止登录！");
        }
        SysUser userq = new SysUser();
        userq.setUsername(username);
        long count = sysUserMapper.selectCount(userq);
        if (count < 1) {
            throw new BlogException("不存在该用户");
        }
        String inpassword = PasswordUtil.encrypt(username, password);
        userq.setPassword(inpassword);
        List<SysUser> userVos = sysUserMapper.select(userq);
        if (userVos.size() != 1) {
            opsForValue.increment(loginCountKey, 1);
            // 计数大于5时，设置用户被锁定一小时
            String loginCount = String.valueOf(opsForValue.get(loginCountKey));
            int retryCount = (5 - Integer.parseInt(loginCount));
            if (retryCount <= 0) {
                opsForValue.set(isLockKey, "LOCK");
                redisTemplate.expire(isLockKey, 1, TimeUnit.HOURS);
                redisTemplate.expire(loginCountKey, 1, TimeUnit.HOURS);
                throw new BlogException("由于密码输入错误次数过多，帐号[" + username + "]已被禁止登录！");
            }
            String msg = retryCount <= 0 ? "您的账号一小时内禁止登录！" : "您还剩" + retryCount + "次重试的机会";
            throw new BlogException("帐号或密码不正确！" + msg);
        }
        SysUser sysUser = userVos.get(0);
        User user = new User(sysUser);
        //获取用户的输入的账号.
        if (sysUser.getStatus() != null && UserStatusEnum.DISABLE.getCode().equals(sysUser.getStatus())) {
            throw new BlogException("帐号已被锁定，禁止登录！");
        }
        //清空登录计数
        redisTemplate.delete(loginCountKey);
        try {
            updateUserLastLoginInfo(user);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return user;
    }


}
