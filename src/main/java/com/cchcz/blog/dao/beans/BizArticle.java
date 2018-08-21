
package com.cchcz.blog.dao.beans;

import com.cchcz.blog.model.object.AbstractDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Transient;
import java.util.List;

/**
* @author cchcz

 * @date 2018/4/16 16:26
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BizArticle extends AbstractDO {
    @Transient
    List<BizTags> tags;
    @Transient
    BizType bizType;
    private String title;
    private Long userId;
    private String coverImage;
    private String qrcodePath;
    private Boolean isMarkdown;
    @Transient
    private String content;
    @Transient
    private String contentMd;
    private Boolean top;
    private Long typeId;
    private Integer status;
    private Boolean recommended;
    private Boolean original;
    private String description;
    private String keywords;
    private Boolean comment;
    @Transient
    private Integer lookCount;
    @Transient
    private Integer commentCount;
    @Transient
    private Integer loveCount;
}
