
package com.cchcz.blog.util;

import com.cchcz.blog.constant.CommonConstant;
import lombok.extern.slf4j.Slf4j;

/**
 * @date: 2017/12/15 17:03
 */
@Slf4j
public class PasswordUtil {

    /**
     * AES 加密
     *
     * @param password 未加密的密码
     * @param salt     盐值，默认使用用户名就可
     * @return
     * @throws Exception
     */
    public static String encrypt(String password, String salt) {
        try {
            return AesUtil.encrypt(Md5Util.MD5(salt + CommonConstant.LDZHAO_SECURITY_KEY), password);
        } catch (Exception e) {
            log.error("加密异常:", e);
        }
        return "";
    }

    /**
     * AES 解密
     *
     * @param encryptPassword 加密后的密码
     * @param salt            盐值，默认使用用户名就可
     * @return
     * @throws Exception
     */
    public static String decrypt(String encryptPassword, String salt) throws Exception {
        return AesUtil.decrypt(Md5Util.MD5(salt + CommonConstant.LDZHAO_SECURITY_KEY), encryptPassword);
    }
    public static void main(String[] args){
        System.out.println(encrypt("chengzhang","zhangcheng0725"));
    }
}
