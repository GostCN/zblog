package com.cchcz.blog.spider.spider;

import com.google.common.collect.Lists;
import com.cchcz.blog.model.entity.OSProject;
import com.cchcz.blog.service.BizKvService;
import com.cchcz.blog.spider.util.SpiderUrlConnection;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;

/**
 * <ClassName>GiteeSpider</ClassName>
 * <Description></Description>
 *
 * @Author cchcz
 * @Date 2018年07月02日 22:24
 */
@Slf4j
@Service
public class GithubSpider {
    private static String GITHUB_OS_COUNT = "github_os_size";
    @Autowired
    private BizKvService bizKvService;

    public List<OSProject> getProjectList() {
        String value = bizKvService.getValue(GITHUB_OS_COUNT);
        int count = 1;
        if (!StringUtils.isEmpty(value)) {
            count = Integer.valueOf(value);
        }
        List<OSProject> projectList = Lists.newArrayList();
        for (int i = 1; i <= count; i++) {
            try {
                String result = SpiderUrlConnection.get("https://github.com/GostCN?utf8=%E2%9C%93&tab=repositories&q=&type=source&language=&page=" + i);
                Document doc = Jsoup.parse(result);
                Elements projectContainer = doc.select("ul[data-filterable-for=your-repos-filter]");
                Elements projects = projectContainer.select("li[itemprop=owns]");
                projects.stream().forEach(x -> {
                    OSProject project = new OSProject();
                    project.setName(x.select("a[itemprop=name codeRepository]").attr("href"));
                    project.setLanguage(x.select("span[itemprop=programmingLanguage]").text());
                    project.setSource("github");
                    Elements spans = x.select("span[class=mr-3]");
                    if (spans.size() > 1) {
                        Element last = spans.last();
                        if (last != null) {
                            project.setAgreement(last.text());
                        }
                    }
                    project.setDescription(x.select("p[itemprop=description]").text());
                    project.setOutLink("https://github.com/" + project.getName());
                    try {
                        String detail = SpiderUrlConnection.get(project.getOutLink());
                        Document detailDoc = Jsoup.parse(detail);
                        Elements elements = detailDoc.select("ul[class=pagehead-actions] a");
                        String watch = elements.get(1).text();
                        String star = elements.get(3).text();
                        String fork = elements.get(5).text();
                        project.setWatchCount(Integer.valueOf(watch));
                        project.setStarCount(Integer.valueOf(star));
                        project.setMemberCount(Integer.valueOf(fork));
                    } catch (IOException e) {
                        log.error("GithubSpider.getProjectList:", e);
                    }
                    projectList.add(project);
                });
            } catch (Exception e) {
                log.error("GithubSpider.getProjectList:", e);
            }
        }
        return projectList;
    }
}
