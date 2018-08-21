
package com.cchcz.blog.aspect;

import com.cchcz.blog.model.entity.ArticleLook;
import com.cchcz.blog.service.BizArticleLookService;
import com.cchcz.blog.util.IpUtil;
import com.cchcz.blog.util.RequestUtil;
import com.cchcz.blog.util.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 文章浏览记录aop操作
 *
 * @author cchcz
 * @version 1.0
 * @date 2018/4/18 11:48
 * @since 1.0
 */
@Slf4j
@Component
@Aspect
@Order(1)
public class ArticleLookAspect {

    @Autowired
    private BizArticleLookService articleLookService;

    @Pointcut("execution(* com.cchcz.blog.controller.web.WebRenderController.article(..))")
    public void pointcut() {
        // 切面切入点
    }

    @Before("pointcut()")
    public void doBefore(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            String userIp = IpUtil.getRealIp(RequestUtil.getRequest());
            Long articleId = Long.valueOf(String.valueOf(args[1]));
            ArticleLook articleLook = new ArticleLook();
            articleLook.setArticleId(articleId);
            articleLook.setUserIp(userIp);
            articleLook.setLookTime(new Date());
            if (SessionUtil.getUser() != null) {
                articleLook.setUserId(SessionUtil.getUser().getId());
            }
            articleLookService.addArticleLook(articleLook);
        }
    }
}
