
package com.cchcz.blog.controller.admin;

import com.cchcz.blog.model.entity.Comment;
import com.cchcz.blog.model.enums.ResponseStatus;
import com.cchcz.blog.model.enums.TemplateKeyEnum;
import com.cchcz.blog.model.object.PageResult;
import com.cchcz.blog.model.object.ResponseVO;
import com.cchcz.blog.model.vo.CommentConditionVO;
import com.cchcz.blog.util.ResultUtil;
import com.github.pagehelper.PageInfo;
import com.cchcz.blog.service.BizCommentService;
import com.cchcz.blog.service.MailService;
import com.cchcz.blog.exception.BlogCommentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 评论管理
 *
 * @author cchcz
 * @version 1.0
 * @date 2018/4/24 14:37
 * @since 1.0
 */
@RestController
@RequestMapping("/admin/comment")
public class BizCommentController {
    @Autowired
    private BizCommentService commentService;
    @Autowired
    private MailService mailService;

    @PostMapping("/list")
    public PageResult list(CommentConditionVO vo) {
        PageInfo<Comment> pageInfo = commentService.findPageBreakByCondition(vo);
        return ResultUtil.tablePage(pageInfo);
    }

    @PostMapping(value = "/reply")
    public ResponseVO reply(Comment comment) {
        try {
            commentService.commentForAdmin(comment);
        } catch (BlogCommentException e) {
            return ResultUtil.error(e.getMessage());
        }
        return ResultUtil.success("成功");
    }

    @PostMapping(value = "/remove")
    public ResponseVO remove(Long[] ids) {
        if (null == ids) {
            return ResultUtil.error(500, "请至少选择一条记录");
        }
        for (Long id : ids) {
            commentService.removeByPrimaryKey(id);
        }
        return ResultUtil.success("成功删除 [" + ids.length + "] 条评论");
    }

    @PostMapping("/get/{id}")
    public ResponseVO get(@PathVariable Long id) {
        return ResultUtil.success(null, this.commentService.getByPrimaryKey(id));
    }

    @PostMapping("/edit")
    public ResponseVO edit(Comment comment) {
        try {
            commentService.updateSelective(comment);
        } catch (Exception e) {
            return ResultUtil.error("评论修改失败！" + e.getMessage());
        }
        return ResultUtil.success(ResponseStatus.SUCCESS);
    }

    /**
     * 审核
     *
     * @param comment
     * @return
     */
    @PostMapping("/audit")
    public ResponseVO audit(Comment comment, String contentText, Boolean sendEmail) {
        try {
            commentService.updateSelective(comment);
            if (!StringUtils.isEmpty(contentText)) {
                comment.setContent(contentText);
                commentService.commentForAdmin(comment);
            }
            if (null != sendEmail && sendEmail) {
                Comment commentDB = commentService.getByPrimaryKey(comment.getId());
                mailService.send(commentDB, TemplateKeyEnum.TM_COMMENT_AUDIT, true);
            }
        } catch (Exception e) {
            return ResultUtil.error("评论审核失败！" + e.getMessage());
        }
        return ResultUtil.success(ResponseStatus.SUCCESS);
    }

    /**
     * 正在审核的
     *
     * @param comment
     * @return
     */
    @PostMapping("/listVerifying")
    public ResponseVO listVerifying(Comment comment) {
        return ResultUtil.success(null, commentService.listVerifying(10));
    }

}
