
package com.cchcz.blog.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.cchcz.blog.model.entity.Article;
import com.cchcz.blog.model.entity.Config;
import com.cchcz.blog.model.enums.BaiduPushTypeEnum;
import com.cchcz.blog.model.enums.ResponseStatus;
import com.cchcz.blog.model.object.PageResult;
import com.cchcz.blog.model.object.ResponseVO;
import com.cchcz.blog.model.vo.ArticleConditionVO;
import com.cchcz.blog.util.BaiduUtil;
import com.cchcz.blog.util.ResultUtil;
import com.cchcz.blog.util.UrlBuildUtil;
import com.github.pagehelper.PageInfo;
import com.cchcz.blog.service.BizArticleService;
import com.cchcz.blog.service.SysConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文章管理
 *
 * @author cchcz
 * @version 1.0
 * @date 2018/4/24 14:37
 * @since 1.0
 */
@Slf4j
@RestController
@RequestMapping("/admin/article")
public class BizArticleController {
    @Autowired
    private BizArticleService articleService;
    @Autowired
    private SysConfigService configService;

    @PostMapping("/list")
    public PageResult list(ArticleConditionVO vo) {
        PageInfo<Article> pageInfo = articleService.findPageBreakByCondition(vo);
        return ResultUtil.tablePage(pageInfo);
    }

    @PostMapping(value = "/remove")
    public ResponseVO remove(Long[] ids) {
        if (null == ids) {
            return ResultUtil.error(500, "请至少选择一条记录");
        }
        for (Long id: ids) {
            articleService.removeByPrimaryKey(id);
        }
        return ResultUtil.success("成功删除 [" + ids.length + "] 篇文章");
    }

    @PostMapping("/get/{id}")
    public ResponseVO get(@PathVariable Long id) {
        return ResultUtil.success(null, this.articleService.getByPrimaryKey(id));
    }

    @PostMapping("/save")
    public ResponseVO edit(Article article, Long[] tags, MultipartFile file) {
        articleService.publish(article, tags, file);
        return ResultUtil.success(ResponseStatus.SUCCESS);
    }

    @PostMapping("/update/{type}")
    public ResponseVO update(@PathVariable("type") String type, Long id) {
        articleService.updateTopOrRecommendedById(type, id);
        return ResultUtil.success(ResponseStatus.SUCCESS);
    }

    @PostMapping(value = "/pushToBaidu/{type}")
    public ResponseVO pushToBaidu(@PathVariable("type") BaiduPushTypeEnum type, Long[] ids) {
        if (null == ids) {
            return ResultUtil.error(500, "请至少选择一条记录");
        }
        Config config = configService.get();
        String siteUrl = config.getSiteUrl();
        StringBuilder params = new StringBuilder();
        for (Long id: ids) {
            params.append(siteUrl).append("/article/").append(id).append("\n");
        }
        // urls: 推送, update: 更新, del: 删除
        String url = UrlBuildUtil.getBaiduPushUrl(type.toString(), config.getSiteUrl().replaceAll("http://", "").replaceAll("https://", ""), config.getBaiduPushToken());
        /**
         * success	       	int	    成功推送的url条数
         * remain	       	int	    当天剩余的可推送url条数
         * not_same_site	array	由于不是本站url而未处理的url列表
         * not_valid	   	array	不合法的url列表
         */
        // {"remain":4999997,"success":1,"not_same_site":[],"not_valid":[]}
        /**
         * error	是	int	      错误码，与状态码相同
         * message	是	string	  错误描述
         */
        //{error":401,"message":"token is not valid"}
        String result = BaiduUtil.doPush(url, params.toString());
        log.info(result);
        JSONObject resultJson = JSONObject.parseObject(result);

        if (resultJson.containsKey("error")) {
            return ResultUtil.error(resultJson.getString("message"));
        }
        return ResultUtil.success(null, result);
    }
}
