
package com.cchcz.blog.model.enums;

/**
* @author cchcz

 * @date 2018/4/16 16:26
 * @since 1.0
 */
public enum NoticeStatusEnum {
    RELEASE("已发布"),
    NOT_RELEASE("未发布");
    private String desc;

    NoticeStatusEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
