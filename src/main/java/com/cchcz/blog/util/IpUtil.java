
package com.cchcz.blog.util;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取IP的工具类
 *
 * @author cchcz
 * @version 1.0
 * @date 2018/4/18 11:48
 * @since 1.0
 */
public class IpUtil {

    /**
     * 获取真实IP
     *
     * @param request
     * @return
     */
    public static String getRealIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        return checkIp(ip) ? ip : (
                checkIp(ip = request.getHeader("Proxy-Client-IP")) ? ip : (
                        checkIp(ip = request.getHeader("WL-Proxy-Client-IP")) ? ip :
                                checkIp(ip = request.getRemoteAddr()) ? ip :
                                        checkIp(ip = request.getHeader("x-forward-for")) ? ip :
                                                checkIp(ip = request.getHeader("x-real-ip")) ? ip :
                                                        checkIp(ip = request.getHeader("remote-host")) ? ip :
                                                                request.getRemoteHost()));
    }

    /**
     * 校验IP
     *
     * @param ip
     * @return
     */
    private static boolean checkIp(String ip) {
        return !StringUtils.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip) && !"127.0.0.1".equals(ip);
    }
}