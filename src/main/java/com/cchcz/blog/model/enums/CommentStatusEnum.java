
package com.cchcz.blog.model.enums;

/**
* @author cchcz

 * @date 2018/4/16 16:26
 * @since 1.0
 */
public enum CommentStatusEnum {
    VERIFYING("正在审核"),
    APPROVED("审核通过"),
    REJECT("审核失败"),
    DELETED("已删除");
    private String desc;

    CommentStatusEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
