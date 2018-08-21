package com.cchcz.blog.dao.beans;

import com.cchcz.blog.model.object.AbstractDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <Description> 爬虫对象</Description>
 * <ClassName> SysSpiderConfig</ClassName>
 *
 * @author cchcz
 * @date 2018年03月09日 10:55
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysSpiderConfig extends AbstractDO {
    private String netName;
    private String netEng;
    private String parseTitle;
    private String parseKeywords;
    private String parseContent;
}
