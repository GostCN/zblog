package com.cchcz.blog.dao.beans;

import com.cchcz.blog.model.object.AbstractDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <ClassName>BizNav</ClassName>
 * <Description></Description>
 *
 * @Author cchcz
 * @Date 2018年07月02日 23:17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BizNav extends AbstractDO {
    /**
     * 工具类型ID
     */
    private Long typeId;
    /**
     * 名称
     */
    private String name;
    /**
     * 描述
     */
    private String description;
    /**
     * 图标
     */
    private String imageIcon;
    /**
     * 外链
     */
    private String outLink;


}
