package com.cchcz.blog.spider;

import com.cchcz.blog.spider.util.SpiderConfigHolder;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;

/**
 * <Description> 爬虫解析器接口 </Description>
 * <ClassName> ISpider</ClassName>
 *
 * @author cchcz
 * @date 2018年03月08日 18:49
 */
public interface ISpiderParser {
    /**
     * <Title>parse</Title>
     * <Description> 解析网页默认实现</Description>
     *
     * @param content, originUrl
     * @return java.lang.String[]
     * @throws
     */
    default String[] parse(String content, String originUrl) {
        Document doc = Jsoup.parse(content);

        //标题
        List<String> titleExpression = getTitleExpression();
        Elements postTitle = getElements(doc, titleExpression);
        String titile = postTitle.html();
        //正文
        List<String> contentExpression = getContentExpression();
        Elements zblog_post_body = getElements(doc, contentExpression);
        zblog_post_body = zblog_post_body.removeAttr("class").attr("id", "zblog_post_body");
        //转载自
        zblog_post_body.append("<div>转载自：" + originUrl + "</div>");
        String contentStr = zblog_post_body.outerHtml();
        //关键字
        String keywords = getKeywords(doc);

        String[] result = new String[]{titile, contentStr, keywords};
        return filter(result);
    }

    String[] filter(String[] result);

    String getSpiderType();

    default String getKeywords(Document doc) {
        List<String> keyWordExpression = getKeyWordExpression();
        Elements keywordsElments = getElements(doc, keyWordExpression);
        return keywordsElments == null ? "" : keywordsElments.html();
    }

    default List<String> getTitleExpression() {
        return SpiderConfigHolder.get(getSpiderType(), "title");
    }

    default List<String> getKeyWordExpression() {
        return SpiderConfigHolder.get(getSpiderType(), "keyword");
    }

    default List<String> getContentExpression() {
        return SpiderConfigHolder.get(getSpiderType(), "content");
    }

    default Elements getElements(Document doc, List<String> expression) {
        for (int i = 0; i < expression.size(); i++) {
            Elements select = doc.select(expression.get(i));
            if (StringUtils.isNotEmpty(select.html())) {
                return select;
            }
        }
        return new Elements();
    }
}
