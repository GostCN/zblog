
package com.cchcz.blog.model.enums;

/**
* @author cchcz

 * @date 2018/4/16 16:26
 * @since 1.0
 */
public enum LinkStatusEnum {
    ENABLE("可用"),
    DISABLE("禁用");
    private String desc;

    LinkStatusEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
