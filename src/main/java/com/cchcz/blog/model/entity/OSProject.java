
package com.cchcz.blog.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.cchcz.blog.dao.beans.BizOsProject;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.Date;

/**
 * @author cchcz
 * @date 2018/4/16 16:26
 * @since 1.0
 */
public class OSProject {
    private BizOsProject bizOsProject;

    public OSProject() {
        this.bizOsProject = new BizOsProject();
    }

    public OSProject(BizOsProject bizOsProject) {
        this.bizOsProject = bizOsProject;
    }

    public String getName() {
        return this.bizOsProject.getName();
    }

    public void setName(String name) {
        this.bizOsProject.setName(name);

    }

    public String getSource() {
        return this.bizOsProject.getSource();
    }

    public void setSource(String source) {
        this.bizOsProject.setSource(source);

    }

    public String getDescription() {
        return this.bizOsProject.getDescription();
    }

    public void setDescription(String description) {
        this.bizOsProject.setDescription(description);

    }

    public String getLanguage() {
        return this.bizOsProject.getLanguage();
    }

    public void setLanguage(String language) {
        this.bizOsProject.setLanguage(language);

    }

    public String getAgreement() {
        return this.bizOsProject.getAgreement();
    }

    public void setAgreement(String agreement) {
        this.bizOsProject.setAgreement(agreement);

    }

    public String getOutLink() {
        return this.bizOsProject.getOutLink();
    }

    public void setOutLink(String outLink) {
        this.bizOsProject.setOutLink(outLink);
    }

    public Integer getWatchCount() {
        return this.bizOsProject.getWatchCount();
    }

    public void setWatchCount(Integer watchCount) {
        this.bizOsProject.setWatchCount(watchCount);
    }

    public Integer getStarCount() {
        return this.bizOsProject.getStarCount();
    }

    public void setStarCount(Integer starCount) {
        this.bizOsProject.setStarCount(starCount);
    }

    public Integer getMemberCount() {
        return this.bizOsProject.getMemberCount();
    }

    public void setMemberCount(Integer memberCount) {
        this.bizOsProject.setMemberCount(memberCount);
    }

    public String getStatus() {
        return this.bizOsProject.getStatus();
    }

    public void setStatus(String status) {
        this.bizOsProject.setStatus(status);
    }

    @JsonIgnore
    public BizOsProject getBizOsProject() {
        return bizOsProject;
    }

    public Long getId() {
        return this.bizOsProject.getId();
    }

    public void setId(Long id) {
        this.bizOsProject.setId(id);
    }


    public Date getCreateTime() {
        return this.bizOsProject.getCreateTime();
    }

    public void setCreateTime(Date createTime) {
        this.bizOsProject.setCreateTime(createTime);
    }

    public Date getUpdateTime() {
        return this.bizOsProject.getUpdateTime();
    }

    public void setUpdateTime(Date updateTime) {
        this.bizOsProject.setUpdateTime(updateTime);
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}

