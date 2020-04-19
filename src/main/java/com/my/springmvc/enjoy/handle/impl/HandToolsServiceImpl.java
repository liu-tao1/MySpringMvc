package com.my.springmvc.enjoy.handle.impl;

import com.my.springmvc.enjoy.annotation.LtService;
import com.my.springmvc.enjoy.handle.HandToolsService;
import com.my.springmvc.enjoy.resolver.ArgumentResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Auto Created by IntelliJ IDEA.
 *
 * @author liutao
 * @since 2020/4/19
 */
@LtService("handTools")
public class HandToolsServiceImpl implements HandToolsService {
    @Override
    public Object[] hand(HttpServletRequest request, HttpServletResponse response, Method method,
            Map<String, Object> beans) {
        Class<?>[] paramClazzs = method.getParameterTypes();
        Object[] args = new Object[paramClazzs.length];

        Map<String, Object> argReslovers = getInstanceType(beans, ArgumentResolver.class);

        int index = 0;
        int i = 0;
        for (Class<?> paramClazz : paramClazzs) {
            for (Map.Entry<String, Object> entry : argReslovers.entrySet()) {
                ArgumentResolver ar = (ArgumentResolver) entry.getValue();
                if (ar.support(paramClazz, index, method)) {
                    args[i++] = ar.argumentResolver(request, response , paramClazz, index, method);
                }
            }
            index++;
        }

        return args;
    }

    private Map<String, Object> getInstanceType(Map<String, Object> beans, Class<?> type) {
        Map<String, Object> resultBeans = new HashMap<String, Object>();
        for (Map.Entry<String, Object> entry : beans.entrySet()) {
            Class<?>[] infs = entry.getValue().getClass().getInterfaces();
            if (infs != null && infs.length > 0) {
                for (Class<?> inf : infs) {
                    if (inf.isAssignableFrom(type)) {
                        resultBeans.put(entry.getKey(), entry.getValue());
                    }
                }
            }
        }

        return resultBeans;

    }
}
