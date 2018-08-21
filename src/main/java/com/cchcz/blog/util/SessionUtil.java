
package com.cchcz.blog.util;

import com.cchcz.blog.model.entity.User;
import com.cchcz.blog.constant.SessionConstant;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * Session工具类
 *
 * @author cchcz
 * @version 1.0
 * @date 2018/4/18 11:48
 * @since 1.0
 */
public class SessionUtil {

    /**
     * 获取session中的用户信息
     *
     * @return User
     */
    public static User getUser() {
        return (User) RequestUtil.getSession(SessionConstant.USER_SESSION_KEY);
    }

    /**
     * 添加session
     *
     * @param user
     */
    public static void setUser(User user) {
        RequestUtil.setSession(SessionConstant.USER_SESSION_KEY, user);
    }

    /**
     * 删除session信息
     */
    public static void removeUser() {
        RequestUtil.removeSession(SessionConstant.USER_SESSION_KEY);
    }

    /**
     * 获取session中的Token信息
     *
     * @return String
     */
    public static String getToken(String key) {
        return (String) RequestUtil.getSession(key);
    }

    /**
     * 添加Token
     */
    public static void setToken(String key) {
        RequestUtil.setSession(key, UUID.randomUUID().toString());
    }

    /**
     * 删除Token信息
     */
    public static void removeToken(String key) {
        RequestUtil.removeSession(key);
    }

    /**
     * 获取验证码
     */
    public static String getKaptcha() {
        return (String) RequestUtil.getSession(SessionConstant.KAPTCHA_SESSION_KEY);
    }

    /**
     * 保存验证码
     */
    public static void setKaptcha(String kaptcha) {
        RequestUtil.setSession(SessionConstant.KAPTCHA_SESSION_KEY, kaptcha);
    }

    /**
     * 保存验证码
     */
    public static void removeKaptcha() {
        RequestUtil.removeSession(SessionConstant.KAPTCHA_SESSION_KEY);
    }

    /**
     * 删除所有的session信息
     */
    public static void removeAllSession() {
        String[] keys = RequestUtil.getSessionKeys();
        if (keys != null && keys.length > 0) {
            for (String key : keys) {
                RequestUtil.removeSession(key);
            }
        }
    }

    /**
     * 设置记住密码cookie
     *
     * @param uid
     */
    public static void setCookie(Long uid, boolean remember) {
        try {
            String val = BlogUtils.enAes(uid.toString(), SessionConstant.AES_SALT);
            boolean isSSL = false;
            Cookie cookie = new Cookie(SessionConstant.USER_IN_COOKIE, val);
            cookie.setPath("/");
            cookie.setDomain("cchcz.com");
            if (remember) {
                cookie.setMaxAge(60 * 60);
            } else {
                cookie.setMaxAge(60 * 10);
            }
            cookie.setSecure(isSSL);
            RequestUtil.getResponse().addCookie(cookie);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取cookie中的用户id
     *
     * @param request
     * @return
     */
    public static Long getCookieUid(HttpServletRequest request) {
        if (null != request) {
            Cookie cookie = cookieRaw(SessionConstant.USER_IN_COOKIE, request);
            if (cookie != null && cookie.getValue() != null) {
                try {
                    String uid = BlogUtils.deAes(cookie.getValue(), SessionConstant.AES_SALT);
                    return StringUtils.isNotBlank(uid) && BlogUtils.isNumber(uid) ? Long.valueOf(uid) : null;
                } catch (Exception e) {
                }
            }
        }
        return null;
    }

    /**
     * 从cookies中获取指定cookie
     *
     * @param name    名称
     * @param request 请求
     * @return cookie
     */
    private static Cookie cookieRaw(String name, HttpServletRequest request) {
        javax.servlet.http.Cookie[] servletCookies = request.getCookies();
        if (servletCookies == null) {
            return null;
        }
        for (javax.servlet.http.Cookie c : servletCookies) {
            if (c.getName().equals(name)) {
                return c;
            }
        }
        return null;
    }
}
