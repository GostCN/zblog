package com.cchcz.blog.model.vo;

import com.cchcz.blog.model.entity.Tool;
import com.cchcz.blog.model.object.BaseConditionVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <ClassName>ToolConditionVO</ClassName>
 * <Description></Description>
 *
 * @Author cchcz
 * @Date 2018年07月03日 07:19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ToolConditionVO extends BaseConditionVO {
    private Tool tool;
    private Long id;
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
