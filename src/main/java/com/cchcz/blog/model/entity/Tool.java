
package com.cchcz.blog.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.cchcz.blog.dao.beans.BizTool;

import java.util.Date;

/**
 * @author cchcz
 * @date 2018/4/16 16:26
 * @since 1.0
 */
public class Tool {
    private BizTool bizTool;

    public Tool() {
        this.bizTool = new BizTool();
    }

    public Tool(BizTool bizTool) {
        this.bizTool = bizTool;
    }

    @JsonIgnore
    public BizTool getBizTool() {
        return bizTool;
    }

    public Long getId() {
        return this.bizTool.getId();
    }

    public void setId(Long id) {
        this.bizTool.setId(id);
    }

    public String getName() {
        return this.bizTool.getName();
    }

    public void setName(String name) {
        this.bizTool.setName(name);

    }

    public String getDescription() {
        return this.bizTool.getDescription();
    }

    public void setDescription(String description) {
        this.bizTool.setDescription(description);

    }


    public String getOutLink() {
        return this.bizTool.getOutLink();
    }

    public void setOutLink(String outLink) {
        this.bizTool.setOutLink(outLink);
    }

    public Date getCreateTime() {
        return this.bizTool.getCreateTime();
    }

    public void setCreateTime(Date createTime) {
        this.bizTool.setCreateTime(createTime);
    }

    public Date getUpdateTime() {
        return this.bizTool.getUpdateTime();
    }

    public void setUpdateTime(Date updateTime) {
        this.bizTool.setUpdateTime(updateTime);
    }

    public Long getTypeId() {
        return this.bizTool.getTypeId();
    }

    public void setTypeId(Long typeId) {
        this.bizTool.setTypeId(typeId);
    }

    public String getImageIcon() {
        return this.bizTool.getImageIcon();
    }

    public void setImageIcon(String imageIcon) {
        this.bizTool.setImageIcon(imageIcon);
    }
}

