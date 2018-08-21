
package com.cchcz.blog.model.object;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * bootstrap table用到的返回json格式
 *
 * @author cchcz
 * @version 1.0

 * @date 2018/4/16 16:26
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PageResult {
    private Long total;
    private List rows;

    public PageResult(Long total, List rows) {
        this.total = total;
        this.rows = rows;
    }

    public PageResult() {
    }
}
