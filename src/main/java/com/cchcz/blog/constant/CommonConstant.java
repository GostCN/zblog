
package com.cchcz.blog.constant;

/**
 * 程序中公用的常量类
 *
 * @author cchcz
 * @version 1.0
 * @date 2018/4/16 16:26
 * @since 1.0
 */
public interface CommonConstant {
    /**
     * 安全密码(UUID生成)，作为盐值用于用户密码的加密
     */
    String LDZHAO_SECURITY_KEY = "929123f8f17944e8b0a531045453e1f1";

    /**
     * 程序默认的错误状态码
     */
    int DEFAULT_ERROR_CODE = 500;

    /**
     * 程序默认的成功状态码
     */
    int DEFAULT_SUCCESS_CODE = 200;


    String INDEX_CAROUSEL = "carousel";


    String MUSIC_URL = "music.cchcz.com";
    String BOOK_URL = "book.cchcz.com";
    String NAV_URL = "nav.cchcz.com";
    String TOOL_URL = "tool.cchcz.com";
    String ADMIN_URL = "admin.cchcz.com";
    String BLOG_URL = "blog.cchcz.com";
    String WWW_URL = "www.cchcz.com";
    String DOMAIN_URL = "cchcz.com";

    String HTTP_PROTOL_PREFIX = "http://";

    String IP_GEO_LOCATION_KEY = "ipGeoLocationKey";
}
