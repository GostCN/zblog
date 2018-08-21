
package com.cchcz.blog.model.vo;

import com.cchcz.blog.model.entity.ArticleLove;
import com.cchcz.blog.model.object.BaseConditionVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author cchcz
 * @version 1.0

 * @date 2018/4/16 16:26
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ArticleLoveConditionVO extends BaseConditionVO {
	private ArticleLove articleLove;
}

