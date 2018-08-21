
package com.cchcz.blog.controller.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cchcz.blog.model.entity.Comment;
import com.cchcz.blog.model.entity.Link;
import com.cchcz.blog.model.enums.CommentStatusEnum;
import com.cchcz.blog.model.object.ResponseVO;
import com.cchcz.blog.model.vo.CommentConditionVO;
import com.cchcz.blog.util.RestClientUtil;
import com.cchcz.blog.util.ResultUtil;
import com.google.common.collect.Maps;
import com.cchcz.blog.exception.BlogArticleException;
import com.cchcz.blog.exception.BlogCommentException;
import com.cchcz.blog.exception.BlogLinkException;
import com.cchcz.blog.service.BizArticleService;
import com.cchcz.blog.service.BizCommentService;
import com.cchcz.blog.service.SysLinkService;
import com.cchcz.blog.service.SysNoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 网站接口类，申请友链、评论、点赞等
 *
 * @author cchcz
 * @version 1.0
 * @date 2018/4/18 11:48
 * @since 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class WebRestApiController {

    @Autowired
    private SysLinkService sysLinkService;
    @Autowired
    private BizCommentService commentService;
    @Autowired
    private BizArticleService articleService;
    @Autowired
    private SysNoticeService noticeService;

    @PostMapping("/autoLink")
    public ResponseVO autoLink(@Validated Link link, BindingResult bindingResult) {
        log.info("申请友情链接......");
        log.info(JSON.toJSONString(link));
        if (bindingResult.hasErrors()) {
            return ResultUtil.error(bindingResult.getFieldError().getDefaultMessage());
        }
        try {
            sysLinkService.autoLink(link);
        } catch (BlogLinkException e) {
            log.error("客户端自助申请友链发生异常", e);
            return ResultUtil.error(e.getMessage());
        }
        return ResultUtil.success("已成功添加友链，祝您生活愉快！");
    }

    @PostMapping("/qq/{qq}")
    public ResponseVO qq(@PathVariable("qq") String qq) {
        if (StringUtils.isEmpty(qq)) {
            return ResultUtil.error("");
        }
        Map<String, String> resultMap = Maps.newHashMap();
        String nickname = "神秘人";
        String json = RestClientUtil.get("http://users.qzone.qq.com/fcg-bin/cgi_get_portrait.fcg?uins=" + qq, "GBK");
        if (!StringUtils.isEmpty(json)) {
            try {
                json = json.replaceAll("portraitCallBack|\\\\s*|\\t|\\r|\\n", "");
                json = json.substring(1, json.length() - 1);
                log.info(json);
                JSONObject object = JSONObject.parseObject(json);
                JSONArray array = object.getJSONArray(qq);
                nickname = array.getString(6);
            } catch (Exception e) {
                log.error("通过QQ号获取用户昵称发生异常", e);
            }
        }
        resultMap.put("avatar", "https://q1.qlogo.cn/g?b=qq&nk=" + qq + "&s=40");
        resultMap.put("nickname", nickname);
        resultMap.put("email", qq + "@qq.com");
        resultMap.put("url", "https://user.qzone.qq.com/" + qq);
        return ResultUtil.success(null, resultMap);
    }

    @PostMapping("/comments")
    public ResponseVO comments(CommentConditionVO vo) {
        vo.setStatus(CommentStatusEnum.APPROVED.toString());
        return ResultUtil.success(null, commentService.list(vo));
    }

    @PostMapping("/comment")
    public ResponseVO comment(Comment comment) {
        try {
            commentService.comment(comment);
        } catch (BlogCommentException e) {
            log.error("评论发生异常", e);
            return ResultUtil.error(e.getMessage());
        }
        return ResultUtil.success("评论发表成功，系统正在审核，请稍后刷新页面查看！");
    }

    @PostMapping("/doSupport/{id}")
    public ResponseVO doSupport(@PathVariable("id") Long id) {
        try {
            commentService.doSupport(id);
        } catch (BlogCommentException e) {
            log.error("评论点赞发生异常", e);
            return ResultUtil.error(e.getMessage());
        }
        return ResultUtil.success("");
    }

    @PostMapping("/doOppose/{id}")
    public ResponseVO doOppose(@PathVariable("id") Long id) {
        try {
            commentService.doOppose(id);
        } catch (BlogCommentException e) {
            log.error("评论点踩发生异常", e);
            return ResultUtil.error(e.getMessage());
        }
        return ResultUtil.success("");
    }

    @PostMapping("/doPraise/{id}")
    public ResponseVO doPraise(@PathVariable("id") Long id) {
        try {
            articleService.doPraise(id);
        } catch (BlogArticleException e) {
            log.error("文章点赞发生异常", e);
            return ResultUtil.error(e.getMessage());
        }
        return ResultUtil.success("");
    }

    @PostMapping("/listNotice")
    public ResponseVO listNotice() {
        return ResultUtil.success("成功", noticeService.listRelease());
    }

}
