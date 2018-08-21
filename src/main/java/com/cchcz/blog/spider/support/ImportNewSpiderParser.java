package com.cchcz.blog.spider.support;

import com.cchcz.blog.spider.ISpiderParser;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <Description> 博客园爬虫</Description>
 * <ClassName> CNBlogsSpider</ClassName>
 *
 * @author cchcz
 * @date 2018年03月08日 18:50
 */
@Component
public class ImportNewSpiderParser implements ISpiderParser {
    public static final String SPIDER_TYPE = "importnew";

    /**
     * <Title>parse</Title>
     * <Description> 解析网页默认实现</Description>
     *
     * @param content, originUrl
     * @return java.lang.String[]
     * @throws
     */
    @Override
    public String[] parse(String content, String originUrl) {
        Document doc = Jsoup.parse(content);
        String[] split = originUrl.split("/");
        String number = split[split.length - 1].replaceAll(".html", "");
        //标题
        List<String> titleExpression = getTitleExpression();
        Elements postTitle = getElements(doc, titleExpression);
        String titile = postTitle.html();
        //正文
        List<String> contentExpression = getContentExpression();
        Elements zblog_post_body = getElements(doc, contentExpression, number);
        zblog_post_body = zblog_post_body.removeAttr("class").attr("id", "zblog_post_body");
        //转载自
        zblog_post_body.append("<div>转载自：" + originUrl + "</div>");
        String contentStr = zblog_post_body.outerHtml();
        //关键字
        String keywords = getKeywords(doc);

        String[] result = new String[]{titile, contentStr, keywords};
        return filter(result);
    }

    @Override
    public String[] filter(String[] result) {
        if (result[0].contains("ImportNew")) {
            result[0] = result[0].split("-")[0].trim();
        }
        return result;
    }

    public Elements getElements(Document doc, List<String> expression, String number) {
        for (int i = 0; i < expression.size(); i++) {
            Elements select = doc.select(expression.get(i).replaceAll("NO", number));
            if (StringUtils.isNotEmpty(select.html())) {
                return select;
            }
        }
        return new Elements();
    }

    @Override
    public String getSpiderType() {
        return SPIDER_TYPE;
    }

}