package com.cchcz.blog.spider.spider;

import com.google.common.collect.Lists;
import com.cchcz.blog.model.entity.OSProject;
import com.cchcz.blog.service.BizKvService;
import com.cchcz.blog.spider.util.SpiderUrlConnection;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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
@Service
@Slf4j
public class GiteeSpider {
    private static String GITEE_OS_COUNT = "gitee_os_size";

    @Autowired
    private BizKvService bizKvService;

    public List<OSProject> getProjectList() {
        String value = bizKvService.getValue(GITEE_OS_COUNT);
        int count = 1;
        if (!StringUtils.isEmpty(value)) {
            count = Integer.valueOf(value);
        }
        List<OSProject> projectList = Lists.newArrayList();
        for (int i = 1; i <= count; i++) {
            try {
                String result = SpiderUrlConnection.get("https://gitee.com/xgost/projects?scope=personal&page=" + i);
                Document doc = Jsoup.parse(result);
                Elements projectContainer = doc.select("div[class=project-container]");
                Elements projects = projectContainer.select("div[class=project]");
                projects.stream().forEach(x -> {
                    OSProject project = new OSProject();
                    project.setName(x.attr("data-path"));
                    project.setLanguage(x.select("a[id=project-language]").text());
                    project.setSource("gitee");
                    project.setDescription(x.select("p[class=description]").text());
                    project.setOutLink("https://gitee.com/" + project.getName());
                    project.setWatchCount(Integer.valueOf(x.select("li[class=watch] a").last().text()));
                    project.setStarCount(Integer.valueOf(x.select("li[class=star] a").last().text()));
                    project.setMemberCount(Integer.valueOf(x.select("li[class=fork] a").last().text()));
                    try {
                        String detail = SpiderUrlConnection.get(project.getOutLink());
                        Document detailDoc = Jsoup.parse(detail);
                        String agreement = detailDoc.select("a[title=开源许可协议]").text();
                        project.setAgreement(agreement);
                    } catch (IOException e) {
                        log.error("GiteeSpider.getProjectList:", e);
                    }
                    projectList.add(project);
                });
            } catch (Exception e) {
                log.error("GiteeSpider.getProjectList:", e);
            }
        }
        return projectList;
    }
}
