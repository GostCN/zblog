package com.cchcz.blog.model.vo;

import com.cchcz.blog.model.entity.NavType;
import com.cchcz.blog.model.object.BaseConditionVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <ClassName>NavConditionVO</ClassName>
 * <Description></Description>
 *
 * @Author cchcz
 * @Date 2018年07月03日 07:19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class NavTypeConditionVO extends BaseConditionVO {
    private NavType navType;
    private Long id;
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