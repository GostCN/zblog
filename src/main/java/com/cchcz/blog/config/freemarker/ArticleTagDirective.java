
package com.cchcz.blog.config.freemarker;

import com.github.pagehelper.PageInfo;
import com.cchcz.blog.model.entity.Article;
import com.cchcz.blog.model.enums.ArticleStatusEnum;
import com.cchcz.blog.service.BizArticleService;
import com.cchcz.blog.model.vo.ArticleConditionVO;
import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * 文章相关的自定义标签
 *
 * @author cchcz
 * @version 1.0

 * @date 2018/4/16 16:26
 * @since 1.0
 */
@Component
public class ArticleTagDirective implements TemplateDirectiveModel {
    private static final String METHOD_KEY = "method";

    @Autowired
    private BizArticleService articleService;

    @Override
    public void execute(Environment environment, Map map, TemplateModel[] templateModels, TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
        DefaultObjectWrapperBuilder builder = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
        if (map.containsKey(METHOD_KEY)) {
            String method = map.get(METHOD_KEY).toString();
            int pageSize = 10;
            if (map.containsKey("pageSize")) {
                String pageSizeStr = map.get("pageSize").toString();
                pageSize = Integer.parseInt(pageSizeStr);
            }
            long typeId = -1;
            if (map.containsKey("typeId")) {
                String typeStr = map.get("typeId").toString();
                typeId = Long.parseLong(typeStr);
            }

            switch (method) {
                case "recentArticles":
                    // 近期文章
                    environment.setVariable("recentArticles", builder.build().wrap(articleService.listRecent(pageSize)));
                    break;
                case "recommendedList":
                    // 站长推荐
                    environment.setVariable("recommendedList", builder.build().wrap(articleService.listRecommended(pageSize)));
                    break;
                case "randomList":
                    // 随机文章
                    environment.setVariable("randomList", builder.build().wrap(articleService.listRandom(pageSize)));
                    break;
                case "hotList":
                    // 热门文章
                    environment.setVariable("hotList", builder.build().wrap(articleService.listHotArticle(pageSize)));
                    break;
                case "typeList":
                    // 按文章分类查询
                    ArticleConditionVO vo = new ArticleConditionVO();
                    vo.setTypeId(typeId);
                    // 已发布状态
                    vo.setStatus(ArticleStatusEnum.PUBLISHED.getCode());
                    vo.setPageSize(pageSize);
                    PageInfo<Article> pageInfo = articleService.findPageBreakByCondition(vo);
                    environment.setVariable("typeList", builder.build().wrap(null == pageInfo ? null : pageInfo.getList()));
                    break;
                default:
                    break;
            }
        }
        templateDirectiveBody.render(environment.getOut());
    }
}
