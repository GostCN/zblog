
package com.cchcz.blog.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

/**
 * @author cchcz
 * @version 1.0
 * @date 2018/4/18 11:48
 * @since 1.0
 */
@Slf4j
public class RequestUtil {
    /**
     * 获取request
     *
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        log.debug("getRequest -- Thread id :{}, name : {}", Thread.currentThread().getId(), Thread.currentThread().getName());
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 获取Response
     *
     * @return HttpServletRequest
     */
    public static HttpServletResponse getResponse() {
        log.debug("getResponse -- Thread id :{}, name : {}", Thread.currentThread().getId(), Thread.currentThread().getName());
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    /**
     * 获取session
     *
     * @return HttpSession
     */
    public static HttpSession getSession() {
        log.debug("getSession -- Thread id :{}, name : {}", Thread.currentThread().getId(), Thread.currentThread().getName());
        return getRequest().getSession();
    }

    /**
     * 获取session的Attribute
     *
     * @param name session的key
     * @return Object
     */
    public static Object getSession(String name) {
        log.debug("getSession -- Thread id :{}, name : {}", Thread.currentThread().getId(), Thread.currentThread().getName());
        return (RequestContextHolder.getRequestAttributes()).getAttribute(name, RequestAttributes.SCOPE_SESSION);
    }

    /**
     * 添加session
     *
     * @param name
     * @param value
     */
    public static void setSession(String name, Object value) {
        log.debug("setSession -- Thread id :{}, name : {}", Thread.currentThread().getId(), Thread.currentThread().getName());
        (RequestContextHolder.getRequestAttributes()).setAttribute(name, value, RequestAttributes.SCOPE_SESSION);
    }

    /**
     * 清除指定session
     *
     * @param name
     * @return void
     */
    public static void removeSession(String name) {
        log.debug("removeSession -- Thread id :{}, name : {}", Thread.currentThread().getId(), Thread.currentThread().getName());
        (RequestContextHolder.getRequestAttributes()).removeAttribute(name, RequestAttributes.SCOPE_SESSION);
    }

    /**
     * 获取所有session key
     *
     * @return String[]
     */
    public static String[] getSessionKeys() {
        log.debug("getSessionKeys -- Thread id :{}, name : {}", Thread.currentThread().getId(), Thread.currentThread().getName());
        return (RequestContextHolder.getRequestAttributes()).getAttributeNames(RequestAttributes.SCOPE_SESSION);
    }

    public static String getParameters() {
        HttpServletRequest request = getRequest();
        Enumeration<String> paraNames = request.getParameterNames();
        if (paraNames == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        while (paraNames.hasMoreElements()) {
            String paraName = paraNames.nextElement();
            sb.append("&").append(paraName).append("=").append(request.getParameter(paraName));
        }
        return sb.toString();
    }

    public static String getHeader(String headerName) {
        return getRequest().getHeader(headerName);
    }

    public static String getReferer() {
        return getHeader("Referer");
    }

    public static String getUa() {
        return getHeader("User-Agent");
    }

    public static String getIp() {
        HttpServletRequest request = getRequest();
        return IpUtil.getRealIp(request);
    }

    public static String getRequestUrl() {
        HttpServletRequest request = getRequest();
        return request.getRequestURL().toString();
    }

    public static String getMethod() {
        HttpServletRequest request = getRequest();
        return request.getMethod();
    }

}
