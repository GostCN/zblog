package com.cchcz.blog.model.enums;

/**
 * @version 1.0
 * @date 2018/3/7 13:58
 * @since 1.0
 */
public enum MusicStatusEnum {

    DISABLE(-1),
    DEFAULT(1),
    NORMAL(0);
    private int code;

    MusicStatusEnum(int code) {
        this.code = code;
    }

    public static MusicStatusEnum getStatus(Integer code) {
        if (null == code) {
            return null;
        }
        MusicStatusEnum[] statusArr = MusicStatusEnum.values();
        for (MusicStatusEnum musicStatusEnum : statusArr) {
            if (musicStatusEnum.getCode() == code) {
                return musicStatusEnum;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }
}
