
package com.cchcz.blog.controller.web;

import com.alibaba.fastjson.JSONObject;
import com.cchcz.blog.model.entity.Article;
import com.cchcz.blog.model.entity.Book;
import com.cchcz.blog.model.enums.ArticleStatusEnum;
import com.cchcz.blog.model.vo.ArticleConditionVO;
import com.cchcz.blog.service.*;
import com.cchcz.blog.util.RestClientUtil;
import com.cchcz.blog.util.ResultUtil;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Splitter;
import com.cchcz.blog.constant.CommonConstant;
import com.cchcz.blog.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 页面跳转类
 *
 * @author cchcz
 * @version 1.0
 * @date 2018/4/18 11:48
 * @since 1.0
 */
@Controller
public class WebRenderController {
    /**
     * sidebar部分的推荐、近期和随机tab页中显示的文章数
     */
    private static final int SIDEBAR_ARTICLE_SIZE = 8;
    private static final String BLOG_INDEX_URL = "blogIndex";
    private static final String INDEX_URL = "index";

    @Autowired
    private BizArticleService bizArticleService;
    @Autowired
    private BizArticleArchivesService bizArticleArchivesService;
    @Autowired
    private SysLinkService sysLinkService;
    @Autowired
    private SysUpdateRecordeService updateRecordeService;
    @Autowired
    private BizKvService bizKvService;
    @Autowired
    private BizBookService bizBookService;

    /**
     * 加载首页的数据
     *
     * @param vo
     * @param model
     * @return
     */
    private void loadIndexPage(ArticleConditionVO vo, Model model) {
        vo.setStatus(ArticleStatusEnum.PUBLISHED.getCode());
        PageInfo<Article> pageInfo = bizArticleService.findPageBreakByCondition(vo);
        model.addAttribute("page", pageInfo);
        model.addAttribute("model", vo);
        model.addAttribute("indexLinkList", sysLinkService.listOfIndex());
    }

    /**
     * 首页
     *
     * @param vo
     * @param model
     * @return
     */
    @RequestMapping("/")
    public ModelAndView home(HttpServletRequest request, ArticleConditionVO vo, Model model) {

        StringBuffer requestURL = request.getRequestURL();
        if (requestURL.indexOf(CommonConstant.WWW_URL) > 0) {
            setHitokoto(model);
            return ResultUtil.view(INDEX_URL);
        }
        if (requestURL.indexOf(CommonConstant.BLOG_URL) > 0) {
            model.addAttribute("url", INDEX_URL);
            loadIndexPage(vo, model);
            Optional<List<Article>> carouselArticles = getCarouselArticles();
            if (carouselArticles.isPresent()) {
                model.addAttribute("carouselArticles", carouselArticles.get());
            }
            return ResultUtil.view(BLOG_INDEX_URL);
        }
        setHitokoto(model);
        return ResultUtil.view(INDEX_URL);
    }

    /**
     * 首页（分页）
     *
     * @param pageNumber
     * @param vo
     * @param model
     * @return
     */
    @RequestMapping("/index/{pageNumber}")
    public ModelAndView type(@PathVariable("pageNumber") Integer pageNumber, ArticleConditionVO vo, Model model) {
        vo.setPageNumber(pageNumber);
        model.addAttribute("url", INDEX_URL);
        loadIndexPage(vo, model);
        if (pageNumber == 1) {
            Optional<List<Article>> carouselArticles = getCarouselArticles();
            if (carouselArticles.isPresent()) {
                model.addAttribute("carouselArticles", carouselArticles);
            }
        }
        return ResultUtil.view(BLOG_INDEX_URL);
    }

    /**
     * 分类列表
     *
     * @param typeId
     * @param model
     * @return
     */
    @GetMapping("/type/{typeId}")
    public ModelAndView type(@PathVariable("typeId") String typeId, Model model) {
        ArticleConditionVO vo = new ArticleConditionVO();
        if (StringUtils.isNumeric(typeId)) {
            vo.setTypeId(Long.valueOf(typeId));
        } else {
            return ResultUtil.redirect(INDEX_URL);
        }
        model.addAttribute("url", "type/" + typeId);
        loadIndexPage(vo, model);

        return ResultUtil.view(BLOG_INDEX_URL);
    }

    /**
     * 分类列表（分页）
     *
     * @param typeId
     * @param pageNumber
     * @param model
     * @return
     */
    @GetMapping("/type/{typeId}/{pageNumber}")
    public ModelAndView type(@PathVariable("typeId") String typeId, @PathVariable("pageNumber") Integer pageNumber, Model model) {
        ArticleConditionVO vo = new ArticleConditionVO();
        if (StringUtils.isNumeric(typeId)) {
            vo.setTypeId(Long.valueOf(typeId));
        } else {
            return ResultUtil.redirect(INDEX_URL);
        }
        vo.setPageNumber(pageNumber);
        model.addAttribute("url", "type/" + typeId);
        loadIndexPage(vo, model);

        return ResultUtil.view(BLOG_INDEX_URL);
    }

    /**
     * 标签列表
     *
     * @param tagId
     * @param model
     * @return
     */
    @GetMapping("/tag/{tagId}")
    public ModelAndView tag(@PathVariable("tagId") String tagId, Model model) {
        ArticleConditionVO vo = new ArticleConditionVO();
        if (StringUtils.isNumeric(tagId)) {
            vo.setTagId(Long.valueOf(tagId));
        } else {
            return ResultUtil.redirect(INDEX_URL);
        }
        model.addAttribute("url", "tag/" + tagId);
        loadIndexPage(vo, model);

        return ResultUtil.view(BLOG_INDEX_URL);
    }

    /**
     * 标签列表（分页）
     *
     * @param tagId
     * @param pageNumber
     * @param model
     * @return
     */
    @GetMapping("/tag/{tagId}/{pageNumber}")
    public ModelAndView tag(@PathVariable("tagId") String tagId, @PathVariable("pageNumber") Integer pageNumber, Model model) {
        ArticleConditionVO vo = new ArticleConditionVO();
        if (StringUtils.isNumeric(tagId)) {
            vo.setTagId(Long.valueOf(tagId));
        } else {
            return ResultUtil.redirect(INDEX_URL);
        }
        vo.setPageNumber(pageNumber);
        model.addAttribute("url", "tag/" + tagId);
        loadIndexPage(vo, model);

        return ResultUtil.view(BLOG_INDEX_URL);
    }

    /**
     * 文章详情
     *
     * @param model
     * @param articleId
     * @return
     */
    @GetMapping("/article/{articleId}")
    public ModelAndView article(Model model, @PathVariable("articleId") String articleId) {
        if (StringUtils.isNumeric(articleId)) {
            Article article = bizArticleService.getByPrimaryKey(Long.valueOf(articleId));
            if (article == null || ArticleStatusEnum.UNPUBLISHED.getCode() == article.getStatusEnum().getCode()) {
                return ResultUtil.redirect("/error/404");
            }
            model.addAttribute("article", article);
            // 上一篇下一篇
            model.addAttribute("other", bizArticleService.getPrevAndNextArticles(article.getCreateTime()));
            // 相关文章
            model.addAttribute("relatedList", bizArticleService.listRelatedArticle(SIDEBAR_ARTICLE_SIZE, article));
            model.addAttribute("articleDetail", true);
            return ResultUtil.view("article");
        } else {
            return ResultUtil.redirect(INDEX_URL);
        }
    }

    /**
     * 预览文章详情
     *
     * @param model
     * @param articleId
     * @return
     */
    @GetMapping("/previewArticle/{articleId}")
    public ModelAndView preview(Model model, @PathVariable("articleId") String articleId) {
        if (StringUtils.isNumeric(articleId)) {
            Article article = bizArticleService.getByPrimaryKey(Long.valueOf(articleId));
            if (article == null) {
                return ResultUtil.redirect("/error/404");
            }
            model.addAttribute("article", article);
            // 上一篇下一篇
            model.addAttribute("other", bizArticleService.getPrevAndNextArticles(article.getCreateTime()));
            // 相关文章
            model.addAttribute("relatedList", bizArticleService.listRelatedArticle(SIDEBAR_ARTICLE_SIZE, article));
            model.addAttribute("articleDetail", true);
            return ResultUtil.view("article");
        } else {
            return ResultUtil.redirect(INDEX_URL);
        }
    }

    /**
     * 关于
     *
     * @return
     */
    @GetMapping("/about")
    public ModelAndView about() {
        return ResultUtil.view("about");
    }

    /**
     * tool
     *
     * @return
     */
    @GetMapping("/tool")
    public ModelAndView tool() {
        return ResultUtil.view("tool");
    }

    /**
     * nav
     *
     * @return
     */
    @GetMapping("/nav")
    public ModelAndView nav() {
        return ResultUtil.view("nav");
    }

    /**
     * book
     *
     * @return
     */
    @GetMapping("/book")
    public ModelAndView book() {
        return ResultUtil.view("book");
    }

    @GetMapping("/book/{id}")
    public ModelAndView book(@PathVariable("id") Long id) {
        Book entity = bizBookService.getByPrimaryKey(id);
        if (entity == null) {
            return ResultUtil.view("book");
        } else {
            entity.setWatchCount(entity.getWatchCount() + 1);
            bizBookService.updateSelective(entity);
            return ResultUtil.redirect(entity.getOutLink());
        }
    }

    @GetMapping("/bookAdmire/{id}")
    public ModelAndView bookAdmire(@PathVariable("id") Long id) {
        Book entity = bizBookService.getByPrimaryKey(id);
        if (entity != null) {
            entity.setStarCount(entity.getStarCount() + 1);
            bizBookService.updateSelective(entity);
        }
        return ResultUtil.redirect("/book");
    }

    /**
     * osp
     *
     * @return
     */
    @GetMapping("/osp")
    public ModelAndView osp() {
        return ResultUtil.view("osp");
    }

    /**
     * 友情链接
     *
     * @param model
     * @return
     */
    @GetMapping("/links")
    public ModelAndView links(Model model) {
        model.addAttribute("link", sysLinkService.listAllByGroup());
        return ResultUtil.view("links");
    }

    /**
     * 留言板
     *
     * @return
     */
    @GetMapping("/guestbook")
    public ModelAndView guestbook() {
        return ResultUtil.view("guestbook");
    }

    /**
     * 归档目录
     *
     * @param model
     * @return
     */
    @GetMapping("/archives")
    public ModelAndView archives(Model model) {
        Map<String, List> map = bizArticleArchivesService.listArchives();
        model.addAttribute("archives", map);
        return ResultUtil.view("archives");
    }

    /**
     * 免责声明
     *
     * @return
     */
    @GetMapping("/disclaimer")
    public ModelAndView disclaimer() {
        return ResultUtil.view("disclaimer");
    }

    /**
     * 站长推荐
     *
     * @param model
     * @return
     */
    @GetMapping("/recommended")
    public ModelAndView recommended(Model model) {
        model.addAttribute("list", bizArticleService.listRecommended(100));
        return ResultUtil.view("recommended");
    }

    /**
     * 更新日志
     *
     * @param model
     * @return
     */
    @GetMapping("/updateLog")
    public ModelAndView updateLog(Model model) {
        model.addAttribute("list", updateRecordeService.listAll());
        return ResultUtil.view("updateLog");
    }

    /**
     * 测试websocket
     *
     * @return
     */
    @GetMapping("/testWebsocket")
    public ModelAndView testWebsocket() {
        return new ModelAndView("testWebsocket");
    }


    private Optional<List<Article>> getCarouselArticles() {
        String ids = bizKvService.getValue(CommonConstant.INDEX_CAROUSEL);
        if (StringUtils.isNotBlank(ids)) {
            List<Long> articleIds = Splitter.on(",")
                    .splitToList(ids)
                    .stream()
                    .map(s -> Long.valueOf(s))
                    .collect(Collectors.toList());
            return Optional.ofNullable(bizArticleService.queryByIds(articleIds));
        }
        return Optional.empty();
    }

    private void setHitokoto(Model model) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(RestClientUtil.get("http://open.iciba.com/dsapi/?date="));
            model.addAttribute("hitokoto_eng", jsonObject.getString("content"));
            model.addAttribute("hitokoto", jsonObject.getString("note"));
        } catch (Exception e) {
            model.addAttribute("hitokoto_eng", "Positive people are lucky people, they can see the roses while others see only the thorns.");
            model.addAttribute("hitokoto", "积极的人是幸运的人，当别人只看到刺的时候，他们却可以看到玫瑰。");
        }
    }
}
