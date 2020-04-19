package com.my.springmvc.enjoy.annotation;

import java.lang.annotation.*;

/**
 * 自定义RequestParam注解
 *
 * @author liutao
 * @since 2020/4/19
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LtRequestParam {
    String value() default "";
}
