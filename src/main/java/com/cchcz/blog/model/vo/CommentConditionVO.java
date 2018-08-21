
package com.cchcz.blog.model.vo;

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
public class CommentConditionVO extends BaseConditionVO {
	private Long userId;
	private Long sid;
	private Long pid;
	private String qq;
	private String email;
	private String url;
	private String status;
}

