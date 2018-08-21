
package com.cchcz.blog.controller.admin;

/**
 * 页面渲染相关 -- 页面跳转
 *
 * @author cchcz
 * @version 1.0
 * @date 2018/4/24 14:37
 * @since 1.0
 */

import com.cchcz.blog.aspect.annotation.BussinessLog;
import com.cchcz.blog.model.entity.Article;
import com.cchcz.blog.util.ResultUtil;
import com.cchcz.blog.service.BizArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 页面跳转类
 *
 * @author cchcz
 * @version 1.0
 * @date 2018/4/24 14:37
 * @since 1.0
 */
@Controller
@RequestMapping("/admin")
public class RenderController {

    @Autowired
    private BizArticleService articleService;

    @BussinessLog("进入首页")
    @GetMapping(value = {""})
    public ModelAndView home() {
        return ResultUtil.viewForAdmin("index");
    }

    @BussinessLog("进入用户列表页")
    @GetMapping("/users")
    public ModelAndView user() {
        return ResultUtil.viewForAdmin("user/list");
    }

    @BussinessLog("进入资源列表页")
    @GetMapping("/resources")
    public ModelAndView resources() {
        return ResultUtil.viewForAdmin("resources/list");
    }

    @BussinessLog("进入角色列表页")
    @GetMapping("/roles")
    public ModelAndView roles() {
        return ResultUtil.viewForAdmin("role/list");
    }

    @BussinessLog("进入文章列表页")
    @GetMapping("/articles")
    public ModelAndView articles() {
        return ResultUtil.viewForAdmin("article/list");
    }

    @BussinessLog(value = "发表文章页[html]")
    @GetMapping("/article/publish")
    public ModelAndView publish(Model model) {
        model.addAttribute("updateFlag", "N");
        return ResultUtil.viewForAdmin("article/publish");
    }

    @BussinessLog(value = "发表文章页[markdown]")
    @GetMapping("/article/publishMd")
    public ModelAndView publishMd(Model model) {
        model.addAttribute("updateFlag", "N");
        return ResultUtil.viewForAdmin("article/publish-md");
    }

    @BussinessLog(value = "修改文章页[id={1}]")
    @GetMapping("/article/update/{id}")
    public ModelAndView edit(@PathVariable("id") Long id, Model model, String type) {
        model.addAttribute("id", id);
        model.addAttribute("updateFlag", "Y");
        Article article = articleService.getByPrimaryKey(id);
        if (StringUtils.isEmpty(type)) {
            if (article.getIsMarkdown()) {
                return ResultUtil.viewForAdmin("article/publish-md");
            }
            return ResultUtil.viewForAdmin("article/publish");
        } else {
            if ("md".equals(type)) {
                return ResultUtil.viewForAdmin("article/publish-md");
            } else {
                return ResultUtil.viewForAdmin("article/publish");
            }
        }
    }

    @BussinessLog("进入分类列表页")
    @GetMapping("/article/types")
    public ModelAndView types() {
        return ResultUtil.viewForAdmin("article/types");
    }

    @BussinessLog("进入标签列表页")
    @GetMapping("/article/tags")
    public ModelAndView tags() {
        return ResultUtil.viewForAdmin("article/tags");
    }

    @BussinessLog("进入系统参数列表页")
    @GetMapping("/kvs")
    public ModelAndView kvs() {
        return ResultUtil.viewForAdmin("kv/list");
    }

    @BussinessLog("进入工具类型列表页")
    @GetMapping("/toolTypes")
    public ModelAndView toolTypes() {
        return ResultUtil.viewForAdmin("tool/tooltypes");
    }

    @BussinessLog("进入网址导航类型列表页")
    @GetMapping("/navTypes")
    public ModelAndView navTypes() {
        return ResultUtil.viewForAdmin("nav/navtypes");
    }

    @BussinessLog("进入开源项目列表页")
    @GetMapping("/osprojects")
    public ModelAndView osprojects() {
        return ResultUtil.viewForAdmin("osproject/list");
    }

    @BussinessLog("进入书籍列表页")
    @GetMapping("/books")
    public ModelAndView books() {
        return ResultUtil.viewForAdmin("book/list");
    }

    @BussinessLog("进入工具列表页")
    @GetMapping("/tools")
    public ModelAndView tools() {
        return ResultUtil.viewForAdmin("tool/list");
    }

    @BussinessLog("进入网址导航列表页")
    @GetMapping("/navs")
    public ModelAndView navs() {
        return ResultUtil.viewForAdmin("nav/list");
    }

    @BussinessLog("进入链接页")
    @GetMapping("/links")
    public ModelAndView links() {
        return ResultUtil.viewForAdmin("link/list");
    }

    @BussinessLog("进入评论页")
    @GetMapping("/comments")
    public ModelAndView comments() {
        return ResultUtil.viewForAdmin("comment/list");
    }

    @BussinessLog("进入系统通知页")
    @GetMapping("/notices")
    public ModelAndView notices() {
        return ResultUtil.viewForAdmin("notice/list");
    }

    @BussinessLog("进入系统配置页")
    @GetMapping("/config")
    public ModelAndView config() {
        return ResultUtil.viewForAdmin("config");
    }

    @BussinessLog("进入模板管理页")
    @GetMapping("/templates")
    public ModelAndView templates() {
        return ResultUtil.viewForAdmin("template/list");
    }

    @BussinessLog("进入更新记录管理页")
    @GetMapping("/updates")
    public ModelAndView updates() {
        return ResultUtil.viewForAdmin("update/list");
    }

    @BussinessLog("进入歌单管理页")
    @GetMapping("/plays")
    public ModelAndView plays() {
        return ResultUtil.viewForAdmin("play/list");
    }

    @BussinessLog("进入静态页面管理页")
    @GetMapping("/sysWebpage")
    public ModelAndView sysWebpage() {
        return ResultUtil.viewForAdmin("sysWebpage/list");
    }

    @GetMapping("/icons")
    public ModelAndView icons() {
        return ResultUtil.viewForAdmin("icons");
    }

    @GetMapping("/visits")
    public ModelAndView visits() {
        return ResultUtil.viewForAdmin("visits");
    }
}
