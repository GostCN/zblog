package com.cchcz.blog.model.vo;

import com.cchcz.blog.dao.beans.SysAttach;
import com.cchcz.blog.model.object.BaseConditionVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <ClassName>SysAttachConditionVO</ClassName>
 * <Description></Description>
 *
 * @Author cchcz
 * @Date 2018年06月24日 10:45
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysAttachConditionVO extends BaseConditionVO {
    private SysAttach sysAttach;

    public SysAttachConditionVO(SysAttach sysAttach) {
        if (sysAttach != null) {
            this.sysAttach = sysAttach;
        } else {
            this.sysAttach = new SysAttach();
        }
    }

    public SysAttachConditionVO() {
        this.sysAttach = new SysAttach();
    }
}
