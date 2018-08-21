package com.cchcz.blog.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.cchcz.blog.dao.beans.BizNavType;

import java.util.Date;

/**
 * <ClassName>NavType</ClassName>
 * <Description>网址导航类型</Description>
 *
 * @author cchcz
 * @date 2018/7/15 18:01
 */
public class NavType {
    private BizNavType bizNavType;

    public NavType() {
        this.bizNavType = new BizNavType();
    }

    public NavType(BizNavType bizNavType) {
        this.bizNavType = bizNavType;
    }

    @JsonIgnore
    public BizNavType getBizNavType() {
        return bizNavType;
    }

    public Long getId() {
        return this.bizNavType.getId();
    }

    public void setId(Long id) {
        this.bizNavType.setId(id);
    }

    public String getName() {
        return this.bizNavType.getName();
    }

    public void setName(String name) {
        this.bizNavType.setName(name);
    }

    public String getIcon() {
        return this.bizNavType.getIcon();
    }

    public void setIcon(String icon) {
        this.bizNavType.setIcon(icon);
    }

    public String getStyle() {
        return this.bizNavType.getStyle();
    }

    public void setStyle(String style) {
        this.bizNavType.setStyle(style);
    }

    public Date getCreateTime() {
        return this.bizNavType.getCreateTime();
    }

    public void setCreateTime(Date createTime) {
        this.bizNavType.setCreateTime(createTime);
    }

    public Date getUpdateTime() {
        return this.bizNavType.getUpdateTime();
    }

    public void setUpdateTime(Date updateTime) {
        this.bizNavType.setUpdateTime(updateTime);
    }

}

