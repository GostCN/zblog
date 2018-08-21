
package com.cchcz.blog.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.cchcz.blog.dao.beans.BizKv;

import java.util.Date;

/**
 * @author cchcz
 * @date 2018/4/16 16:26
 * @since 1.0
 */
public class Kv {
    private BizKv bizKv;

    public Kv() {
        this.bizKv = new BizKv();
    }

    public Kv(BizKv bizKv) {
        this.bizKv = bizKv;
    }

    @JsonIgnore
    public BizKv getBizKv() {
        return bizKv;
    }

    public Long getId() {
        return this.bizKv.getId();
    }

    public void setId(Long id) {
        this.bizKv.setId(id);
    }

    public String getPkey() {
        return this.bizKv.getPkey();
    }

    public void setPkey(String key) {
        this.bizKv.setPkey(key);
    }

    public String getPvalue() {
        return this.bizKv.getPvalue();
    }

    public void setPvalue(String value) {
        this.bizKv.setPvalue(value);
    }

    public Date getCreateTime() {
        return this.bizKv.getCreateTime();
    }

    public void setCreateTime(Date createTime) {
        this.bizKv.setCreateTime(createTime);
    }

    public Date getUpdateTime() {
        return this.bizKv.getUpdateTime();
    }

    public void setUpdateTime(Date updateTime) {
        this.bizKv.setUpdateTime(updateTime);
    }

}

