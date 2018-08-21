
package com.cchcz.blog.model.enums;

/**
* @author cchcz

 * @date 2018/4/16 16:26
 * @since 1.0
 */
public enum LinkSourceEnum {

    AUTOMATIC("自动申请"),
    ADMIN("管理员添加"),
    OTHER("其他");
    private String desc;

    LinkSourceEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
