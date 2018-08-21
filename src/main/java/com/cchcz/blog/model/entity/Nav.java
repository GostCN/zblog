
package com.cchcz.blog.model.entity;

import com.cchcz.blog.dao.beans.BizNav;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

/**
 * <ClassName>Nav</ClassName>
 * <Description>网址导航</Description>
 *
 * @author cchcz
 * @date 2018/7/15 18:00
 */
public class Nav {
    private BizNav bizNav;

    public Nav() {
        this.bizNav = new BizNav();
    }

    public Nav(BizNav bizNav) {
        this.bizNav = bizNav;
    }

    @JsonIgnore
    public BizNav getBizNav() {
        return bizNav;
    }

    public Long getId() {
        return this.bizNav.getId();
    }

    public void setId(Long id) {
        this.bizNav.setId(id);
    }

    public String getName() {
        return this.bizNav.getName();
    }

    public void setName(String name) {
        this.bizNav.setName(name);

    }

    public String getDescription() {
        return this.bizNav.getDescription();
    }

    public void setDescription(String description) {
        this.bizNav.setDescription(description);

    }


    public String getOutLink() {
        return this.bizNav.getOutLink();
    }

    public void setOutLink(String outLink) {
        this.bizNav.setOutLink(outLink);
    }

    public Date getCreateTime() {
        return this.bizNav.getCreateTime();
    }

    public void setCreateTime(Date createTime) {
        this.bizNav.setCreateTime(createTime);
    }

    public Date getUpdateTime() {
        return this.bizNav.getUpdateTime();
    }

    public void setUpdateTime(Date updateTime) {
        this.bizNav.setUpdateTime(updateTime);
    }

    public Long getTypeId() {
        return this.bizNav.getTypeId();
    }

    public void setTypeId(Long typeId) {
        this.bizNav.setTypeId(typeId);
    }

    public String getImageIcon() {
        return this.bizNav.getImageIcon();
    }

    public void setImageIcon(String imageIcon) {
        this.bizNav.setImageIcon(imageIcon);
    }
}

