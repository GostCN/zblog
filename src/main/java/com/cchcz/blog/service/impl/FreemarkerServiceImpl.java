package com.cchcz.blog.service.impl;

import com.cchcz.blog.model.entity.Config;
import com.cchcz.blog.service.FreemarkerService;
import com.cchcz.blog.service.SysConfigService;
import freemarker.template.TemplateModelException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * <ClassName>FreemarkerServiceImpl</ClassName>
 * <Description></Description>
 *
 * @Author cchcz
 * @Date 2018年06月27日 10:23
 */
@Service
@Slf4j
public class FreemarkerServiceImpl implements FreemarkerService {
    @Autowired
    protected freemarker.template.Configuration configuration;
    @Autowired
    private SysConfigService sysConfigService;

    @Override
    @Async
    public void updateConfig() {
        Config config = sysConfigService.get();
        if (null == config) {
            log.error("config为空");
            return;
        }
        try {
            configuration.setSharedVariable("config", config);
        } catch (TemplateModelException e) {
            log.error("更新freemarker异常", e);
        }
    }
}
