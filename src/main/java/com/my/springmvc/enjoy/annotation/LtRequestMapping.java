package com.my.springmvc.enjoy.annotation;

import java.lang.annotation.*;

/**
 * 自定义RequestMapping注解
 *
 * @author liutao
 * @since 2020/4/19
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LtRequestMapping {
    String value() default "";
}
