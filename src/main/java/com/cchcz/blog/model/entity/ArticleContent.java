
package com.cchcz.blog.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.cchcz.blog.dao.beans.BizArticleContent;

/**
 * @author cchcz
 * @date 2018/4/16 16:26
 * @since 1.0
 */
public class ArticleContent {
    private BizArticleContent bizArticleContent;

    public ArticleContent() {
        this.bizArticleContent = new BizArticleContent();
    }

    public ArticleContent(BizArticleContent bizArticleContent) {
        this.bizArticleContent = bizArticleContent;
    }

    @JsonIgnore
    public BizArticleContent getBizArticleContent() {
        return this.bizArticleContent;
    }

    public Long getId() {
        return this.bizArticleContent.getId();
    }

    public void setId(Long id) {
        this.bizArticleContent.setId(id);
    }

    public String getContent() {
        return this.bizArticleContent.getContent();
    }

    public void setContent(String content) {
        this.bizArticleContent.setContent(content);
    }

    public String getContentMd() {
        return this.bizArticleContent.getContentMd();
    }

    public void setContentMd(String contentMd) {
        this.bizArticleContent.setContentMd(contentMd);
    }

}

