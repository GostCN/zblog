
package com.cchcz.blog.aspect.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志记录、自定义注解
 *
* @author cchcz

 * @date 2018/4/16 16:26
 * @since 1.0
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface BussinessLog {
    /**
     * 业务的名称
     */
    String value() default "";

}
