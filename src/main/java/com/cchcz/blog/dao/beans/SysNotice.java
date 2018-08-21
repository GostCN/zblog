
package com.cchcz.blog.dao.beans;

import com.cchcz.blog.model.object.AbstractDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* @author cchcz

 * @date 2018/4/16 16:26
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysNotice extends AbstractDO {
    private Long userId;
    private String status;
    private String title;
    private String content;
}
