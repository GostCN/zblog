
package com.cchcz.blog.aspect;

import com.cchcz.blog.aspect.annotation.RedisCache;
import com.cchcz.blog.model.property.AppProperties;
import com.cchcz.blog.service.FreemarkerService;
import com.cchcz.blog.util.CacheKeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * Redis业务层数据缓存
 *
 * @author cchcz
 * @version 1.0
 * @date 2018/4/16 16:26
 * @since 1.0
 */
@Slf4j
@Aspect
@Component
public class RedisCacheAspect {

//    private static ConcurrentMap<String, Object> localCache = new ConcurrentHashMap();

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private AppProperties propertiesConfig;
    @Autowired
    private FreemarkerService freemarkerService;

    @Pointcut(value = "@annotation(com.cchcz.blog.aspect.annotation.RedisCache)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object handle(ProceedingJoinPoint point) throws Throwable {
        // 获取拦截的方法名
        Signature sig = point.getSignature();
        MethodSignature msig = null;
        if (!(sig instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        msig = (MethodSignature) sig;
        Object target = point.getTarget();
        Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
        if (!currentMethod.isAnnotationPresent(RedisCache.class)) {
            throw new RuntimeException("未指定RedisChache注解！");
        }
        StringBuilder key = new StringBuilder(point.getTarget().getClass().getName());
        // 获取操作名称
        RedisCache cache = currentMethod.getAnnotation(RedisCache.class);
        if (cache.flush()) {
            log.info("{}*-清空缓存", key);
            //清空本地缓存
//            clearLocalCache(key.toString());
            //清空redis
            Set<String> keys = redisTemplate.keys(key.toString() + "*");
            redisTemplate.delete(keys);
            log.info("Clear all the cached query result from redis");
            Object ret = point.proceed();
            if ("config".equals(cache.group())) {
                freemarkerService.updateConfig();
            }
            return ret;
        }
        key.append(".").append(currentMethod.getName());
        key.append(CacheKeyUtil.getMethodParamsKey(point.getArgs())).append(cache.key());
        String realKey = key.toString();
//        Object o = localCache.get(realKey);
//        if (o != null) {
//            return o;
//        } else {
        log.info("{}从缓存中获取数据", realKey);
        Object o = redisTemplate.opsForValue().get(realKey);
        if (o != null) {
//                localCache.put(realKey, o);
            return o;
        } else {
            // 先执行业务
            Object result = point.proceed();
            if (result != null) {
                if (cache.flush()) {
                    freemarkerService.updateConfig();
                }
                // 向Redis中添加数据，有效时间是30天
                redisTemplate.opsForValue().set(realKey, result, cache.expire(), cache.unit());
//                    localCache.put(realKey, result);
            }
            log.info("Put query result to redis");
            log.info("{}从数据库中获取数据", realKey);
            return result;
        }
    }
//    }

//    private void clearLocalCache(String key) {
//        localCache.keySet().stream().forEach(k -> {
//            if (k.startsWith(key)) {
//                localCache.remove(k);
//            }
//        });
//    }
}
