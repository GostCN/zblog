package com.cchcz.blog.spider.service;

import com.cchcz.blog.model.entity.OSProject;
import com.cchcz.blog.service.BizOSProjectService;
import com.cchcz.blog.spider.spider.GiteeSpider;
import com.cchcz.blog.spider.spider.GithubSpider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <ClassName>SyncOpenSourceInfoTask</ClassName>
 * <Description></Description>
 *
 * @Author cchcz
 * @Date 2018年07月03日 14:30
 */
@Service
public class SyncOpenSourceInfoService {
    @Autowired
    private GiteeSpider giteeSpider;
    @Autowired
    private GithubSpider githubSpider;
    @Autowired
    private BizOSProjectService bizOSProjectService;

    public void syncGiteeOpenSources() {
        List<OSProject> projectList = giteeSpider.getProjectList();
        saveOpenSources(projectList);
    }

    public void syncGithubOpenSources() {
        List<OSProject> projectList = githubSpider.getProjectList();
        saveOpenSources(projectList);
    }

    private void saveOpenSources(List<OSProject> projectList) {
        projectList.stream().forEach(x -> {
            OSProject query = new OSProject();
            query.setName(x.getName());
            query.setSource(x.getSource());
            OSProject oneByEntity = bizOSProjectService.getOneByEntity(query);
            if (oneByEntity != null) {
                x.setId(oneByEntity.getId());
                bizOSProjectService.updateSelective(x);
            } else {
                bizOSProjectService.insert(x);
            }
        });
    }
}
