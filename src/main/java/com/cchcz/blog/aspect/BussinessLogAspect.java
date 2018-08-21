
package com.cchcz.blog.aspect;

import com.cchcz.blog.aspect.annotation.BussinessLog;
import com.cchcz.blog.util.RegexUtils;
import com.cchcz.blog.util.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;

/**
 * AOP切面记录日志
 *
 * @author cchcz
 * @version 1.0

 * @date 2018/4/16 16:26
 * @since 1.0
 */
@Slf4j
@Aspect
@Component
public class BussinessLogAspect {

    @Pointcut(value = "@annotation(com.cchcz.blog.aspect.annotation.BussinessLog)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object writeLog(ProceedingJoinPoint point) throws Throwable {

        //先执行业务
        Object result = point.proceed();

        try {
            handle(point);
        } catch (Exception e) {
            log.error("日志记录出错!", e);
        }

        return result;
    }

    @AfterThrowing(pointcut = "pointcut()", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Throwable ex) throws Throwable {
        log.error("捕获到了异常...", ex);
    }

    private void handle(ProceedingJoinPoint point) throws Exception {
        //获取拦截的方法名
        Signature sig = point.getSignature();
        MethodSignature msig = null;
        if (!(sig instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        msig = (MethodSignature) sig;
        Object target = point.getTarget();
        //获取拦截方法的参数
        String className = point.getTarget().getClass().getName();
        Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
        String methodName = currentMethod.getName();
        //获取操作名称
        BussinessLog annotation = currentMethod.getAnnotation(BussinessLog.class);
        String bussinessName = parseContent(point.getArgs(), annotation.value());
        String ua = RequestUtil.getUa();

        log.info("{}-{}.{}", bussinessName, className, methodName);
        log.info("IP: {}, Method: {}, Request URL: {}", RequestUtil.getIp(), RequestUtil.getMethod(), RequestUtil.getRequestUrl());
        log.info("User-Agent: " + ua);
    }

    private String parseContent(Object[] params, String bussinessName) {
        if (bussinessName.contains("{") && bussinessName.contains("}")) {
            List<String> result = RegexUtils.match(bussinessName, "(?<=\\{)(\\d+)");
            for (String s : result) {
                int index = Integer.parseInt(s);
                bussinessName = bussinessName.replaceAll("\\{" + index + "\\}", String.valueOf(params[index - 1]));
            }
        }
        return bussinessName;
    }
}
