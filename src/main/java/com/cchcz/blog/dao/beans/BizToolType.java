package com.cchcz.blog.dao.beans;

import com.cchcz.blog.model.object.AbstractDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <ClassName>BizToolType</ClassName>
 * <Description></Description>
 *
 * @Author cchcz
 * @Date 2018年07月02日 23:17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BizToolType extends AbstractDO {
    /**
     * 名称
     */
    private String name;
    /**
     * font awesome 图标
     */
    private String icon;
    /**
     * '样式'
     */
    private String style;
}
