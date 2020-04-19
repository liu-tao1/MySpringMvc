package com.my.springmvc.enjoy.annotation;

import java.lang.annotation.*;

/**
 * 自定义Qualifier注解
 *
 * @author liutao
 * @since 2020/4/19
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LtQualifier {
    String value() default "";
}
