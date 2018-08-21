package com.cchcz.blog.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.cchcz.blog.dao.beans.SysSpiderConfig;

import java.util.Date;

/**
 * <ClassName>SpiderConfig</ClassName>
 * <Description></Description>
 *
 * @Author cchcz
 * @Date 2018年06月30日 22:31
 */
public class SpiderConfig {
    private SysSpiderConfig sysSpiderConfig;

    public SpiderConfig() {
        this.sysSpiderConfig = new SysSpiderConfig();
    }

    public SpiderConfig(SysSpiderConfig sysSpiderConfig) {
        this.sysSpiderConfig = sysSpiderConfig;
    }

    @JsonIgnore
    public SysSpiderConfig getSysSpiderConfig() {
        return sysSpiderConfig;
    }

    public Long getId() {
        return sysSpiderConfig.getId();
    }

    public void setId(Long id) {
        this.sysSpiderConfig.setId(id);
    }

    public String getNetName() {
        return sysSpiderConfig.getNetName();
    }

    public void setNetName(String netName) {
        this.sysSpiderConfig.setNetName(netName);
    }

    public String getNetEng() {
        return sysSpiderConfig.getNetEng();
    }

    public void setNetEng(String netEng) {
        this.sysSpiderConfig.setNetEng(netEng);
    }

    public String getParseTitle() {
        return sysSpiderConfig.getParseTitle();
    }

    public void setParseTitle(String parseTitle) {
        this.sysSpiderConfig.setParseTitle(parseTitle);
    }

    public String getParseKeywords() {
        return sysSpiderConfig.getParseKeywords();
    }

    public void setParseKeywords(String parseKeywords) {
        this.sysSpiderConfig.setParseKeywords(parseKeywords);
    }

    public String getParseContent() {
        return sysSpiderConfig.getParseContent();
    }

    public void setParseContent(String parseContent) {
        this.sysSpiderConfig.setParseContent(parseContent);
    }

    public Date getUpdateTime() {
        return sysSpiderConfig.getUpdateTime();
    }

    public void setUpdateTime(Date updateTime) {
        this.sysSpiderConfig.setUpdateTime(updateTime);
    }

    public Date getCreateTime() {
        return this.sysSpiderConfig.getCreateTime();
    }

    public void setCreateTime(Date createTime) {
        this.sysSpiderConfig.setCreateTime(createTime);
    }


}
