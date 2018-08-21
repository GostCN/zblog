
package com.cchcz.blog.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.cchcz.blog.dao.beans.BizBook;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.Date;

/**
 * @author cchcz
 * @date 2018/4/16 16:26
 * @since 1.0
 */
public class Book {
    private BizBook bizBook;

    public Book() {
        this.bizBook = new BizBook();
    }

    public Book(BizBook bizBook) {
        this.bizBook = bizBook;
    }

    public String getTitle() {
        return this.bizBook.getTitle();
    }

    public void setTitle(String name) {
        this.bizBook.setTitle(name);

    }

    public String getDescription() {
        return this.bizBook.getDescription();
    }

    public void setDescription(String description) {
        this.bizBook.setDescription(description);

    }

    public String getOutLink() {
        return this.bizBook.getOutLink();
    }

    public void setOutLink(String outLink) {
        this.bizBook.setOutLink(outLink);
    }

    public Integer getWatchCount() {
        return this.bizBook.getWatchCount();
    }

    public void setWatchCount(Integer watchCount) {
        this.bizBook.setWatchCount(watchCount);
    }

    public Integer getStarCount() {
        return this.bizBook.getStarCount();
    }

    public void setStarCount(Integer starCount) {
        this.bizBook.setStarCount(starCount);
    }

    public String getStatus() {
        return this.bizBook.getStatus();
    }

    public void setStatus(String status) {
        this.bizBook.setStatus(status);
    }

    @JsonIgnore
    public BizBook getBizBook() {
        return bizBook;
    }

    public Long getId() {
        return this.bizBook.getId();
    }

    public void setId(Long id) {
        this.bizBook.setId(id);
    }


    public Date getCreateTime() {
        return this.bizBook.getCreateTime();
    }

    public void setCreateTime(Date createTime) {
        this.bizBook.setCreateTime(createTime);
    }

    public Date getUpdateTime() {
        return this.bizBook.getUpdateTime();
    }

    public void setUpdateTime(Date updateTime) {
        this.bizBook.setUpdateTime(updateTime);
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}

