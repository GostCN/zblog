package com.cchcz.blog.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.cchcz.blog.dao.beans.BizNetEasePlay;

import java.util.Date;

public class NetEasePlay {
    private BizNetEasePlay bizNetEasePlay;

    public NetEasePlay() {
        this.bizNetEasePlay = new BizNetEasePlay();
    }

    public NetEasePlay(BizNetEasePlay bizNetEasePlay) {
        this.bizNetEasePlay = bizNetEasePlay;
    }

    @JsonIgnore
    public BizNetEasePlay getBizNetEasePlay() {
        return bizNetEasePlay;
    }

    public void setBizNetEasePlay(BizNetEasePlay bizNetEasePlay) {
        this.bizNetEasePlay = bizNetEasePlay;
    }

    public Long getId() {
        return this.bizNetEasePlay.getId();
    }

    public void setId(Long id) {
        this.bizNetEasePlay.setId(id);
    }

    public String getPlayId() {
        return this.bizNetEasePlay.getPlayId();
    }

    public void setPlayId(String name) {
        this.bizNetEasePlay.setPlayId(name);
    }

    public String getPlayName() {
        return this.bizNetEasePlay.getPlayName();
    }

    public void setPlayName(String name) {
        this.bizNetEasePlay.setPlayName(name);
    }

    public Integer getStatus() {
        return this.bizNetEasePlay.getStatus();
    }

    public void setStatus(Integer status) {
        this.bizNetEasePlay.setStatus(status);
    }

    public Date getCreateTime() {
        return this.bizNetEasePlay.getCreateTime();
    }

    public void setCreateTime(Date createTime) {
        this.bizNetEasePlay.setCreateTime(createTime);
    }

    public Date getUpdateTime() {
        return this.bizNetEasePlay.getUpdateTime();
    }

    public void setUpdateTime(Date updateTime) {
        this.bizNetEasePlay.setUpdateTime(updateTime);
    }
}
