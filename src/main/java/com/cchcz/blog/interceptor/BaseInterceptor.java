package com.cchcz.blog.interceptor;

import com.cchcz.blog.dao.beans.SysUser;
import com.cchcz.blog.dao.mapper.SysUserMapper;
import com.cchcz.blog.model.entity.User;
import com.cchcz.blog.util.IpUtil;
import com.cchcz.blog.util.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义拦截器
 * Created by BlueT on 2017/3/9.
 */
@Component
@Slf4j
@Order(1)
public class BaseInterceptor extends HandlerInterceptorAdapter {
    private static final String USER_AGENT = "user-agent";
    @Value("${static.resource.url}")
    private String staticResourceUrl;

    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String uri = request.getRequestURI();
        log.info("UserAgent: {}", request.getHeader(USER_AGENT));
        log.info("用户访问地址: {}, 来路地址: {}, remoteHost：{}", uri, IpUtil.getRealIp(request), request.getRemoteHost());
        //请求拦截处理
        request.setAttribute("isLogin", "N");
        User user = SessionUtil.getUser();
        if (null == user) {
            Long uid = SessionUtil.getCookieUid(request);
            if (null != uid) {
                //这里还是有安全隐患,cookie是可以伪造的
                SysUser sysUser = sysUserMapper.selectByPrimaryKey(uid);
                if (sysUser != null) {
                    user = new User(sysUser);
                    SessionUtil.setUser(user);
                    request.setAttribute("isLogin", "Y");
                }
            }
        } else {
            request.setAttribute("isLogin", "Y");
        }
        if (uri.startsWith("/admin") && !uri.startsWith("/admin/passport/login") && !uri.startsWith("/admin/passport/signin") && null == user) {
            response.sendRedirect(request.getContextPath() + "/admin/passport/login");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        request.setAttribute("staticResourceUrl", staticResourceUrl);
    }
}
