package com.my.springmvc.enjoy.resolver.impl;

import com.my.springmvc.enjoy.annotation.LtRequestParam;
import com.my.springmvc.enjoy.annotation.LtService;
import com.my.springmvc.enjoy.resolver.ArgumentResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Auto Created by IntelliJ IDEA.
 *
 * @author liutao
 * @since 2020/4/19
 */
@LtService("requestParamArgResolver")
public class RequestParamArgResolver implements ArgumentResolver {
    @Override
    public boolean support(Class<?> type, int index, Method method) {
        Annotation[][] anno = method.getParameterAnnotations();
        Annotation[] paramAnnos = anno[index];

        for (Annotation an : paramAnnos) {
            // 判断一个类Class1和另一个类Class2是否相同或是另一个类的子类或接口。
            if (LtRequestParam.class.isAssignableFrom(an.getClass())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Object argumentResolver(HttpServletRequest request, HttpServletResponse response, Class<?> type, int index,
            Method method) {
        Annotation[][] anno = method.getParameterAnnotations();
        Annotation[] paramAnnos = anno[index];

        for (Annotation an : paramAnnos) {
            if (LtRequestParam.class.isAssignableFrom(an.getClass())) {
                LtRequestParam er = (LtRequestParam) an;

                String value = er.value();
                return request.getParameter(value);
            }
        }
        return null;
    }
}
