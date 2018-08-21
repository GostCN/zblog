package com.cchcz.blog.dao.beans;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * <ClassName>SysSequence</ClassName>
 * <Description>sequence对象</Description>
 *
 * @Author cchcz
 * @Date 2018年03月09日 07:25
 */
public class SysSequence implements Serializable {
    private static final long serialVersionUID = 8283864143764465347L;
    private Integer id;
    private String tag;
    private Integer curValue;
    private Integer offset;
    private Date updateTime;
    private Integer limitStart;
    private Integer limitSize;

    public SysSequence() {
    }

    public SysSequence(Integer id, String tag, Integer curValue, Integer offset, Date updateTime) {
        this.id = id;
        this.tag = tag;
        this.curValue = curValue;
        this.offset = offset;
        this.updateTime = updateTime;
    }

    public Integer getLimitStart() {
        return this.limitStart;
    }

    public void setLimitStart(Integer limitStart) {
        this.limitStart = limitStart;
    }

    public Integer getLimitSize() {
        return this.limitSize;
    }

    public void setLimitSize(Integer limitSize) {
        this.limitSize = limitSize;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getCurValue() {
        return curValue;
    }

    public void setCurValue(Integer curValue) {
        this.curValue = curValue;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
