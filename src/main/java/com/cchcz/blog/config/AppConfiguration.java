package com.cchcz.blog.config;

import com.cchcz.blog.interceptor.BaseInterceptor;
import com.cchcz.blog.interceptor.VisitorInterceptor;
import com.cchcz.blog.util.BlogUtils;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.annotation.Resource;

/**
 * <ClassName>AppConfiguration</ClassName>
 * <Description>应用配置类</Description>
 *
 * @Author cchcz
 * @Date 2018年07月07日 10:28
 */
@Configuration
@Import({AsyncTaskConfig.class, RedisConfig.class, FreeMarkerConfig.class})
public class AppConfiguration implements WebMvcConfigurer {

    @Resource
    private BaseInterceptor baseInterceptor;
    @Resource
    private VisitorInterceptor visitorInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(baseInterceptor);
        registry.addInterceptor(visitorInterceptor);
    }

    /**
     * 添加静态资源文件，外部可以直接访问地址
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**").addResourceLocations("file:" + BlogUtils.getUplodFilePath() + "upload/");
        registry.addResourceHandler("/uploadForMd/**").addResourceLocations("file:" + BlogUtils.getUplodFilePath() + "upload/");
    }

    /**
     * <Title>errorPageRegistrar</Title>
     * <Description> 注册错误页面</Description>
     *
     * @return ErrorPageRegistrar
     * @see org.springframework.boot.web.server.ErrorPageRegistrar
     */
    @Bean
    public ErrorPageRegistrar errorPageRegistrar() {
        return registry -> {
            registry.addErrorPages(new ErrorPage(HttpStatus.BAD_REQUEST, "/error/400"));
            registry.addErrorPages(new ErrorPage(HttpStatus.UNAUTHORIZED, "/error/401"));
            registry.addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN, "/error/403"));
            registry.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/error/404"));
            registry.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500"));
            registry.addErrorPages(new ErrorPage(Throwable.class, "/error/500"));
        };
    }

    @Override
    public Validator getValidator() {
        return new LocalValidatorFactoryBean();
    }

    /**
     * <Title>serverEndpointExporter</Title>
     * <Description> websocket配置类</Description>
     *
     * @return ServerEndpointExporter
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
