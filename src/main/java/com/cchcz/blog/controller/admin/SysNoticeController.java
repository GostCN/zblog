
package com.cchcz.blog.controller.admin;

import com.cchcz.blog.model.entity.Notice;
import com.cchcz.blog.model.entity.User;
import com.cchcz.blog.model.enums.NoticeStatusEnum;
import com.cchcz.blog.model.enums.ResponseStatus;
import com.cchcz.blog.model.object.PageResult;
import com.cchcz.blog.model.object.ResponseVO;
import com.cchcz.blog.model.vo.NoticeConditionVO;
import com.cchcz.blog.util.ResultUtil;
import com.cchcz.blog.util.SessionUtil;
import com.github.pagehelper.PageInfo;
import com.cchcz.blog.service.SysNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统通知-- 首页菜单下方滚动显示
 *
 * @author cchcz
 * @version 1.0

 * @date 2018/4/24 14:37
 * @since 1.0
 */
@RestController
@RequestMapping("/admin/notice")
public class SysNoticeController {
    @Autowired
    private SysNoticeService noticeService;

    @PostMapping("/list")
    public PageResult list(NoticeConditionVO vo) {
        PageInfo<Notice> pageInfo = noticeService.findPageBreakByCondition(vo);
        return ResultUtil.tablePage(pageInfo);
    }

    @PostMapping(value = "/add")
    public ResponseVO add(Notice notice) {
        User user = SessionUtil.getUser();
        if (null != user) {
            notice.setUserId(user.getId());
        }
        noticeService.insert(notice);
        return ResultUtil.success("系统通知添加成功");
    }

    @PostMapping(value = "/remove")
    public ResponseVO remove(Long[] ids) {
        if (null == ids) {
            return ResultUtil.error(500, "请至少选择一条记录");
        }
        for (Long id : ids) {
            noticeService.removeByPrimaryKey(id);
        }
        return ResultUtil.success("成功删除 [" + ids.length + "] 个系统通知");
    }

    @PostMapping("/get/{id}")
    public ResponseVO get(@PathVariable Long id) {
        return ResultUtil.success(null, this.noticeService.getByPrimaryKey(id));
    }

    @PostMapping("/edit")
    public ResponseVO edit(Notice notice) {
        try {
            noticeService.updateSelective(notice);
        } catch (Exception e) {
            return ResultUtil.error("系统通知修改失败！" + e.getMessage());
        }
        return ResultUtil.success(ResponseStatus.SUCCESS);
    }

    @PostMapping("/release/{id}")
    public ResponseVO release(@PathVariable Long id) {
        try {
            Notice notice = new Notice();
            notice.setId(id);
            notice.setStatus(NoticeStatusEnum.RELEASE.toString());
            noticeService.updateSelective(notice);
        } catch (Exception e) {
            return ResultUtil.error("通知发布失败，状态不变！" + e.getMessage());
        }
        return ResultUtil.success("该通知已发布，可去前台页面查看效果！");
    }

    @PostMapping("/withdraw/{id}")
    public ResponseVO withdraw(@PathVariable Long id) {
        try {
            Notice notice = new Notice();
            notice.setId(id);
            notice.setStatus(NoticeStatusEnum.NOT_RELEASE.toString());
            noticeService.updateSelective(notice);
        } catch (Exception e) {
            return ResultUtil.error("通知撤回失败，状态不变！" + e.getMessage());
        }
        return ResultUtil.success("该通知已撤回，可修改后重新发布！");
    }

}
