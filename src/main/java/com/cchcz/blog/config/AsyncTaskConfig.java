
package com.cchcz.blog.config;

import com.cchcz.blog.biz.VisitorRankBiz;
import com.cchcz.blog.spider.service.SyncNavInfoService;
import com.cchcz.blog.spider.service.SyncOpenSourceInfoService;
import com.cchcz.blog.spider.service.SyncToolInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;

/**
 * 异步线程配置
 *
 * @author cchcz
 * @version 1.0
 * @date 2018/4/28 11:04
 * @since 1.0
 */
@Slf4j
public class AsyncTaskConfig {
    @Autowired
    private SyncOpenSourceInfoService syncOpenSourceInfoService;
    @Autowired
    private SyncToolInfoService syncToolInfoService;
    @Autowired
    private SyncNavInfoService syncNavInfoService;
    @Autowired
    private VisitorRankBiz visitorRankBiz;


    @Scheduled(cron = "0 0 01 * * ?")
    private void syncGithubOpenSource() {
        try {
            syncOpenSourceInfoService.syncGithubOpenSources();
        } catch (Exception e) {
            log.error("syncOpenSourceInfoService.syncGithubOpenSources", e);
        }
    }

    @Scheduled(cron = "0 0 23 * * ?")
    private void syncGiteeOpenSource() {
        try {
            syncOpenSourceInfoService.syncGiteeOpenSources();
        } catch (Exception e) {
            log.error("syncOpenSourceInfoService.syncGiteeOpenSources", e);
        }
    }

    @Scheduled(cron = "0 0 22 * * ?")
    private void syncNav() {
        try {
            syncNavInfoService.syncNavs();
        } catch (Exception e) {
            log.error("syncNavInfoService.syncNavs", e);
        }
    }

    @Scheduled(cron = "0 0 21 * * ?")
    private void syncTool() {
        try {
            syncToolInfoService.syncTools();
        } catch (Exception e) {
            log.error("syncToolInfoService.syncTools", e);
        }
    }

    @Scheduled(cron = "0 0 */6 * * ?")
    private void syncVisitorRank() {
        try {
            visitorRankBiz.updateVisitorRank();
        } catch (Exception e) {
            log.error("syncToolInfoService.syncTools", e);
        }
    }

    @PostConstruct
    private void init() {
//        syncGiteeOpenSource();
//        syncGithubOpenSource();
//        syncTool();
//        syncNavInfoService.syncNavs();
    }
}
