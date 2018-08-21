
package com.cchcz.blog.service;


import com.cchcz.blog.model.entity.Template;
import com.cchcz.blog.model.enums.TemplateKeyEnum;
import com.cchcz.blog.model.object.AbstractService;
import com.cchcz.blog.model.vo.TemplateConditionVO;
import com.github.pagehelper.PageInfo;

/**
 * 系统模板
 *
 * @author cchcz
 * @version 1.0

 * @date 2018/4/16 16:26
 * @since 1.0
 */
public interface SysTemplateService extends AbstractService<Template, Long> {

    /**
     * 分页查询
     *
     * @param vo
     * @return
     */
    PageInfo<Template> findPageBreakByCondition(TemplateConditionVO vo);

    /**
     * 通过key获取模板信息
     *
     * @param key
     * @return
     */
    Template getTemplate(TemplateKeyEnum key);
}
