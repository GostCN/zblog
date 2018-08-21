package com.cchcz.blog.dao.beans;

import com.cchcz.blog.model.object.AbstractDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <ClassName>BizBook</ClassName>
 * <Description></Description>
 *
 * @Author cchcz
 * @Date 2018年07月02日 23:18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BizBook extends AbstractDO {
    /**
     * 名称
     */
    private String title;
    /**
     * 描述
     */
    private String description;
    /**
     * 外链
     */
    private String outLink;
    private String status;
    private Integer watchCount;
    private Integer starCount;

}
