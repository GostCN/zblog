package com.cchcz.blog.model.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * created by zhangcheng 2018/8/5
 */
public class VisitorRankVo {
    private String name;
    private long num;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getNum() {
        return num;
    }

    public void setNum(long num) {
        this.num = num;
    }

    public VisitorRankVo(String name, long num) {
        this.name = name;
        this.num = num;
    }

    public VisitorRankVo() {
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
