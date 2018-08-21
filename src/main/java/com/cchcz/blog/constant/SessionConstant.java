
package com.cchcz.blog.constant;

/**
 * @author cchcz
 * @date 2018/4/16 16:26
 * @since 1.0
 */
public interface SessionConstant {
    /**
     * User 的 session key;k
     */
    String USER_SESSION_KEY = "user";

    /**
     * kaptcha 的 session key
     */
    String KAPTCHA_SESSION_KEY = "KAPTCHA_SESSION_KEY";

    String USER_IN_COOKIE = "S_L_ID";
    /**
     * aes加密加盐
     */
    String AES_SALT = "0123456789abcdef";
}
