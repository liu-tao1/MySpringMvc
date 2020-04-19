package com.my.springmvc.enjoy.resolver.impl;

import com.my.springmvc.enjoy.annotation.LtService;
import com.my.springmvc.enjoy.resolver.ArgumentResolver;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * Auto Created by IntelliJ IDEA.
 *
 * @author liutao
 * @since 2020/4/19
 */
@LtService("httpServletRequestArgumentResolver")
public class HttpServletRequestArgumentResolver implements ArgumentResolver {
    public boolean support(Class<?> type, int index, Method method) {
        return ServletRequest.class.isAssignableFrom(type);
    }

    public Object argumentResolver(HttpServletRequest request, HttpServletResponse response, Class<?> type, int index,
            Method method) {
        return request;
    }
}
