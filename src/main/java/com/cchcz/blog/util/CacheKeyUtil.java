
package com.cchcz.blog.util;

import com.alibaba.fastjson.JSON;
import org.springframework.util.StringUtils;

/**
 * 缓存key相关的工具类
 *
 * @author cchcz
 * @version 1.0

 * @date 2018/5/25 10:23
 * @since 1.0
 */
public class CacheKeyUtil {

    public static String getMethodParamsKey(Object... obj) {
        if (StringUtils.isEmpty(obj)) {
            return "";
        }
        return "(" + JSON.toJSONString(obj) + ")";
    }

}
