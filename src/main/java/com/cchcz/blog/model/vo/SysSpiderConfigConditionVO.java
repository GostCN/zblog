package com.cchcz.blog.model.vo;

import com.cchcz.blog.dao.beans.SysSpiderConfig;
import com.cchcz.blog.model.object.BaseConditionVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <Description> 爬虫对象</Description>
 * <ClassName> SysSpiderConfigConditionVO</ClassName>
 *
 * @author cchcz
 * @date 2018年03月09日 10:55
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysSpiderConfigConditionVO extends BaseConditionVO {
    private SysSpiderConfig sysSpiderConfig;
}
