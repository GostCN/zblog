package com.cchcz.blog.dao.beans;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Id;

/**
 * <ClassName>BizArticleContent</ClassName>
 * <Description></Description>
 *
 * @Author cchcz
 * @Date 2018年06月30日 18:18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BizArticleContent {
    @Id
    private Long id;
    private String content;
    private String contentMd;
}
