package com.my.springmvc.enjoy.annotation;

import java.lang.annotation.*;

/**
 * 自定义Service注解
 *
 * @author liutao
 * @since 2020/4/19
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LtService {
    String value() default "";
}
