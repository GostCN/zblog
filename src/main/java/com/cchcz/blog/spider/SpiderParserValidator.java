package com.cchcz.blog.spider;

import org.springframework.util.StringUtils;

/**
 * <Description> 结果校验器</Description>
 * <ClassName> SpiderParserValidator</ClassName>
 *
 * @author cchcz
 * @date 2018年03月12日 10:35
 */
public class SpiderParserValidator {
    public static void check(String[] res) {
        if (StringUtils.isEmpty(res[0])) {
            throw new RuntimeException("解析title失败");
        }
        if (StringUtils.isEmpty(res[1])) {
            throw new RuntimeException("解析文章内容失败");
        }
    }

    public static boolean checkWithoutException(String[] res) {
        if (StringUtils.isEmpty(res[0]) || StringUtils.isEmpty(res[1])) {
            return false;
        }
        return true;
    }
}
