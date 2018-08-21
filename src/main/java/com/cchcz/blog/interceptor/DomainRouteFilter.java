package com.cchcz.blog.interceptor;

import com.cchcz.blog.constant.CommonConstant;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <ClassName>DomainRouteFilter</ClassName>
 * <Description></Description>
 *
 * @Author cchcz
 * @Date 2018年07月17日 23:23
 */
@WebFilter
@Slf4j
public class DomainRouteFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        StringBuffer requestURL = request.getRequestURL();
        log.info("requestURL:{}", requestURL);
        if (requestURL.indexOf("/" + CommonConstant.DOMAIN_URL) > 0) {
            response.sendRedirect(CommonConstant.HTTP_PROTOL_PREFIX + CommonConstant.WWW_URL);
            return;
        }
        if (requestURL.indexOf(CommonConstant.ADMIN_URL) > 0) {
            if (requestURL.indexOf(CommonConstant.ADMIN_URL + "/admin") < 0) {
                response.sendRedirect(CommonConstant.HTTP_PROTOL_PREFIX + CommonConstant.ADMIN_URL + "/admin");
                return;
            }
        } else if (requestURL.indexOf(CommonConstant.MUSIC_URL) > 0) {
            if (requestURL.indexOf(CommonConstant.MUSIC_URL + "/music/") < 0) {
                response.sendRedirect(CommonConstant.HTTP_PROTOL_PREFIX + CommonConstant.MUSIC_URL + "/music/");
                return;
            }
        } else if (requestURL.indexOf(CommonConstant.TOOL_URL) > 0) {
            if (requestURL.indexOf(CommonConstant.TOOL_URL + "/tool") < 0) {
                response.sendRedirect(CommonConstant.HTTP_PROTOL_PREFIX + CommonConstant.TOOL_URL + "/tool");
                return;
            }
        } else if (requestURL.indexOf(CommonConstant.BOOK_URL) > 0) {
            if (requestURL.indexOf(CommonConstant.BOOK_URL + "/book") < 0) {
                response.sendRedirect(CommonConstant.HTTP_PROTOL_PREFIX + CommonConstant.BOOK_URL + "/book");
                return;
            }
        } else if (requestURL.indexOf(CommonConstant.NAV_URL) > 0) {
            if (requestURL.indexOf(CommonConstant.NAV_URL + "/nav") < 0) {
                response.sendRedirect(CommonConstant.HTTP_PROTOL_PREFIX + CommonConstant.NAV_URL + "/nav");
                return;
            }
        } else if (requestURL.indexOf(CommonConstant.BLOG_URL) > 0) {
            if (requestURL.indexOf(CommonConstant.BLOG_URL + "/tool") > 0) {
                response.sendRedirect(CommonConstant.HTTP_PROTOL_PREFIX + CommonConstant.TOOL_URL + "/tool");
                return;
            }
            if (requestURL.indexOf(CommonConstant.BLOG_URL + "/admin") > 0) {
                response.sendRedirect(CommonConstant.HTTP_PROTOL_PREFIX + CommonConstant.ADMIN_URL + "/admin");
                return;
            }
            if (requestURL.indexOf(CommonConstant.BLOG_URL + "/music") > 0) {
                response.sendRedirect(CommonConstant.HTTP_PROTOL_PREFIX + CommonConstant.MUSIC_URL + "/music/");
                return;
            }
            if (requestURL.indexOf(CommonConstant.BLOG_URL + "/book") > 0) {
                response.sendRedirect(CommonConstant.HTTP_PROTOL_PREFIX + CommonConstant.BOOK_URL + "/book");
                return;
            }
            if (requestURL.indexOf(CommonConstant.BLOG_URL + "/nav") > 0) {
                response.sendRedirect(CommonConstant.HTTP_PROTOL_PREFIX + CommonConstant.NAV_URL + "/nav");
                return;
            }
        } else if (requestURL.indexOf(CommonConstant.WWW_URL) > 0) {
            if (requestURL.indexOf(CommonConstant.WWW_URL + "/tool") > 0) {
                response.sendRedirect(CommonConstant.HTTP_PROTOL_PREFIX + CommonConstant.TOOL_URL + "/tool");
                return;
            }
            if (requestURL.indexOf(CommonConstant.WWW_URL + "/admin") > 0) {
                response.sendRedirect(CommonConstant.HTTP_PROTOL_PREFIX + CommonConstant.ADMIN_URL + "/admin");
                return;
            }
            if (requestURL.indexOf(CommonConstant.WWW_URL + "/music") > 0) {
                response.sendRedirect(CommonConstant.HTTP_PROTOL_PREFIX + CommonConstant.MUSIC_URL + "/music/");
                return;
            }
            if (requestURL.indexOf(CommonConstant.WWW_URL + "/book") > 0) {
                response.sendRedirect(CommonConstant.HTTP_PROTOL_PREFIX + CommonConstant.BOOK_URL + "/book");
                return;
            }
            if (requestURL.indexOf(CommonConstant.WWW_URL + "/nav") > 0) {
                response.sendRedirect(CommonConstant.HTTP_PROTOL_PREFIX + CommonConstant.NAV_URL + "/nav");
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
