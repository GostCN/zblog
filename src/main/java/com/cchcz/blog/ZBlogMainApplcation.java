
package com.cchcz.blog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 程序启动类
 *
 * @author cchcz
 * @version 1.0
 * @date 2018/4/24 14:37
 * @since 1.0
 */
@Slf4j
@SpringBootApplication(scanBasePackages = "com.cchcz.blog")
@ServletComponentScan
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableAsync
@EnableScheduling
@EnableCaching
@EnableTransactionManagement
@MapperScan("com.cchcz.blog.dao.mapper")
public class ZBlogMainApplcation {
    public static void main(String[] args) {
        try {
            SpringApplication.run(ZBlogMainApplcation.class, args);
        } catch (Exception e) {
            log.error("BlogAdminApplication启动异常", e);
        }
    }
}
