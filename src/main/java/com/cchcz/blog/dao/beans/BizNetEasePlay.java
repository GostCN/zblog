package com.cchcz.blog.dao.beans;

import com.cchcz.blog.model.object.AbstractDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class BizNetEasePlay extends AbstractDO {
    private String playId;
    private String playName;
    private Integer status;
}
