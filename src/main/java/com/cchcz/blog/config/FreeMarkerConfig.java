
package com.cchcz.blog.config;

import com.cchcz.blog.config.freemarker.ArticleTagDirective;
import com.cchcz.blog.config.freemarker.CustomTagDirective;
import com.cchcz.blog.service.SysConfigService;
import freemarker.template.TemplateModelException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * freemarker配置类
 *
 * @author cchcz
 * @version 1.0
 * @date 2018/4/16 16:26
 * @since 1.0
 */
@Slf4j
public class FreeMarkerConfig {

    @Autowired
    protected freemarker.template.Configuration configuration;
    @Autowired
    protected CustomTagDirective customTagDirective;
    @Autowired
    protected ArticleTagDirective articleTagDirective;
    @Autowired
    private SysConfigService configService;

    /**
     * 添加自定义标签
     */
    @PostConstruct
    public void setSharedVariable() {
        configuration.setSharedVariable("customTag", customTagDirective);
        configuration.setSharedVariable("articleTag", articleTagDirective);
        try {
            configuration.setSharedVariable("config", configService.get());
        } catch (TemplateModelException e) {
            log.error("freemarker配置TemplateDirectiveModel异常", e);
        }
    }
}
