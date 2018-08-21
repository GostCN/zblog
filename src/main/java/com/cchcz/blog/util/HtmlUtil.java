
package com.cchcz.blog.util;

import com.overzealous.remark.Options;
import com.overzealous.remark.Remark;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @version 1.0
 * @date 2018/1/19 10:32
 * @since 1.0
 */
public class HtmlUtil {
    /**
     * 获取首个Element信息
     *
     * @param htmlDocument
     * @param styleClassName
     * @return
     */
    public static Element getFirstElementByClass(Document htmlDocument, String styleClassName) {
        if (htmlDocument == null || styleClassName == null || "".equals(styleClassName)) {
            return null;
        }
        Elements elements = htmlDocument.getElementsByClass(styleClassName);
        if (elements == null || elements.size() <= 0) {
            return null;
        }
        return elements.get(0);
    }

    public static Element getFirstElementByClass(Element element, String styleClassName) {
        if (element == null || styleClassName == null || "".equals(styleClassName)) {
            return null;
        }
        Elements elements = element.getElementsByClass(styleClassName);
        if (elements == null || elements.size() <= 0) {
            return null;
        }
        return elements.get(0);
    }

    /**
     * 获取Elements
     *
     * @param htmlDocument
     * @param styleClassName
     * @return
     */
    public static Elements getElementsByClass(Document htmlDocument, String styleClassName) {
        if (htmlDocument == null || styleClassName == null || "".equals(styleClassName)) {
            return null;
        }
        return htmlDocument.getElementsByClass(styleClassName);
    }

    /**
     * 获取首个Element
     *
     * @param htmlDocument
     * @param tag
     * @return
     */
    public static Element getFirstElementByTag(Document htmlDocument, String tag) {
        if (htmlDocument == null || tag == null || "".equals(tag)) {
            return null;
        }
        Elements elements = htmlDocument.getElementsByTag(tag);
        if (elements == null || elements.size() <= 0) {
            return null;
        }
        return elements.get(0);
    }

    public static Element getFirstElementByTag(Element element, String tag) {
        if (element == null || tag == null || "".equals(tag)) {
            return null;
        }
        Elements elements = element.getElementsByTag(tag);
        if (elements == null || elements.size() <= 0) {
            return null;
        }
        return elements.get(0);
    }

    public static Elements getElementsByTag(Document htmlDocument, String tag) {
        if (htmlDocument == null || tag == null || "".equals(tag)) {
            return null;
        }
        return htmlDocument.getElementsByTag(tag);
    }

    public static Elements getElementsByTag(Element element, String tag) {
        if (element == null || tag == null || "".equals(tag)) {
            return null;
        }
        return element.getElementsByTag(tag);
    }

    /**
     * 获取Element
     *
     * @param htmlDocument
     * @param id
     * @return
     */
    public static Element getElementById(Document htmlDocument, String id) {
        if (htmlDocument == null || id == null || "".equals(id)) {
            return null;
        }
        return htmlDocument.getElementById(id);
    }

    /**
     * 替换所有标签
     *
     * @param content
     * @return
     */
    public static String html2Text(String content) {
        if (StringUtils.isEmpty(content)) {
            return null;
        }
        // 定义HTML标签的正则表达式
        String regEx_html = "<[^>]+>";
        content = content.replaceAll(regEx_html, "").replaceAll(" ", "");
        content = content.replaceAll("&quot;", "\"")
                .replaceAll("&nbsp;", "")
                .replaceAll("&amp;", "&")
                .replaceAll("\n", " ")
                .replaceAll("&#39;", "\'")
                .replaceAll("&lt;", "<")
                .replaceAll("&gt;", ">")
                .replaceAll("[ \\f\\t\\v]{2,}", "\t");

        String regEx = "<.+?>";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(content);
        content = matcher.replaceAll("");
        return content.trim();
    }

    public static String html2md(String content) {
        Remark remark = new Remark(Options.multiMarkdown());
        return remark.convertFragment(content);
    }
}
