
package com.cchcz.blog.controller.admin;

import com.cchcz.blog.model.entity.Link;
import com.cchcz.blog.model.enums.LinkSourceEnum;
import com.cchcz.blog.model.enums.ResponseStatus;
import com.cchcz.blog.model.enums.TemplateKeyEnum;
import com.cchcz.blog.model.object.PageResult;
import com.cchcz.blog.model.object.ResponseVO;
import com.cchcz.blog.model.vo.LinkConditionVO;
import com.cchcz.blog.util.ResultUtil;
import com.github.pagehelper.PageInfo;
import com.cchcz.blog.service.MailService;
import com.cchcz.blog.service.SysLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 友情链接
 *
 * @author cchcz
 * @version 1.0

 * @date 2018/4/24 14:37
 * @since 1.0
 */
@RestController
@RequestMapping("/admin/link")
public class SysLinkController {
    @Autowired
    private SysLinkService linkService;
    @Autowired
    private MailService mailService;

    @PostMapping("/list")
    public PageResult list(LinkConditionVO vo) {
        PageInfo<Link> pageInfo = linkService.findPageBreakByCondition(vo);
        return ResultUtil.tablePage(pageInfo);
    }

    @PostMapping(value = "/add")
    public ResponseVO add(Link link) {
        link.setSource(LinkSourceEnum.ADMIN);
        linkService.insert(link);
        mailService.send(link, TemplateKeyEnum.TM_LINKS);
        return ResultUtil.success("成功");
    }

    @PostMapping(value = "/remove")
    public ResponseVO remove(Long[] ids) {
        if (null == ids) {
            return ResultUtil.error(500, "请至少选择一条记录");
        }
        for (Long id : ids) {
            linkService.removeByPrimaryKey(id);
        }
        return ResultUtil.success("成功删除 [" + ids.length + "] 个友情链接");
    }

    @PostMapping("/get/{id}")
    public ResponseVO get(@PathVariable Long id) {
        return ResultUtil.success(null, this.linkService.getByPrimaryKey(id));
    }

    @PostMapping("/edit")
    public ResponseVO edit(Link link) {
        try {
            linkService.updateSelective(link);
        } catch (Exception e) {
            return ResultUtil.error("友情链接修改失败！" + e.getMessage());
        }
        return ResultUtil.success(ResponseStatus.SUCCESS);
    }

}
