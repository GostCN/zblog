package com.cchcz.blog.util;

import com.cchcz.blog.dao.beans.BizVisitorRank;

/**
 * created by zhangcheng 2018/8/21
 */
public class VisitorUtil {


    public static void buildBizVisitorRank(String[] array, BizVisitorRank vo) {
        if (array.length == 3) {
            if ("其它".equals(array[0])) {
                vo.setContinent("其它");
            } else {
                vo.setContinent("亚洲");
            }
            vo.setCountry(array[0]);
            vo.setProvice(array[1]);
            vo.setCity(array[2]);
        }
    }

}
