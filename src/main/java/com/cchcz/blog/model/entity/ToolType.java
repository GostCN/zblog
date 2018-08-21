
package com.cchcz.blog.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.cchcz.blog.dao.beans.BizToolType;

import java.util.Date;

/**
 * @author cchcz
 * @date 2018/4/16 16:26
 * @since 1.0
 */
public class ToolType {
    private BizToolType bizToolType;

    public ToolType() {
        this.bizToolType = new BizToolType();
    }

    public ToolType(BizToolType bizToolType) {
        this.bizToolType = bizToolType;
    }

    @JsonIgnore
    public BizToolType getBizToolType() {
        return bizToolType;
    }

    public Long getId() {
        return this.bizToolType.getId();
    }

    public void setId(Long id) {
        this.bizToolType.setId(id);
    }

    public String getName() {
        return this.bizToolType.getName();
    }

    public void setName(String name) {
        this.bizToolType.setName(name);
    }

    public String getIcon() {
        return this.bizToolType.getIcon();
    }

    public void setIcon(String icon) {
        this.bizToolType.setIcon(icon);
    }

    public String getStyle() {
        return this.bizToolType.getStyle();
    }

    public void setStyle(String style) {
        this.bizToolType.setStyle(style);
    }

    public Date getCreateTime() {
        return this.bizToolType.getCreateTime();
    }

    public void setCreateTime(Date createTime) {
        this.bizToolType.setCreateTime(createTime);
    }

    public Date getUpdateTime() {
        return this.bizToolType.getUpdateTime();
    }

    public void setUpdateTime(Date updateTime) {
        this.bizToolType.setUpdateTime(updateTime);
    }

}

