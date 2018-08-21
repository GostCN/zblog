package com.cchcz.blog.model.vo;

import com.cchcz.blog.model.entity.NetEasePlay;
import com.cchcz.blog.model.object.BaseConditionVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class NetEasePlayConditionVO extends BaseConditionVO {
    private NetEasePlay netEasePlay;
}
