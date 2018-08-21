package com.cchcz.blog.spider.service;

import com.cchcz.blog.model.entity.Article;
import com.cchcz.blog.service.BizArticleService;
import com.cchcz.blog.util.HtmlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <Description> 保存爬取结果到服务器 </Description>
 * <ClassName> SpideResultSaveService </ClassName>
 *
 * @author cchcz
 * @date 2018年03月08日 22:17
 */
@Service
public class SpiderResultSaveService {

    @Autowired
    private BizArticleService bizArticleService;

    public boolean save(String[] res, Long uid) {
        Article article = new Article();
        article.setTitle(res[0]);
        article.setUserId(uid);
        article.setCoverImage("");
        article.setQrcodePath("");
        article.setIsMarkdown(false);
        article.setContent(res[1]);
        article.setContentMd(HtmlUtil.html2md(res[1]));
        article.setTop(false);
        article.setTypeId(3L);//其他
        article.setStatus(0);//草稿
        article.setRecommended(false);
        article.setOriginal(false);
        article.setDescription("");
        article.setKeywords(res[2]);
        article.setCreateTime(new Date());
        article.setUpdateTime(new Date());
        article.setComment(true);
        return bizArticleService.publish(article, new Long[]{5L}, null);
    }
}
