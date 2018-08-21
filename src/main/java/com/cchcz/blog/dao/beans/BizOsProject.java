package com.cchcz.blog.dao.beans;

import com.cchcz.blog.model.object.AbstractDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <ClassName>BizOsProject</ClassName>
 * <Description></Description>
 *
 * @Author cchcz
 * @Date 2018年07月02日 23:18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BizOsProject extends AbstractDO {
    /**
     * 名称
     */
    private String name;
    /**
     * 来源
     */
    private String source;
    /**
     * 描述
     */
    private String description;
    /**
     * 语言
     */
    private String language;
    /**
     * 协议
     */
    private String agreement;
    /**
     * 外链
     */
    private String outLink;
    private String status;
    private Integer watchCount;
    private Integer starCount;
    private Integer memberCount;

}
