package com.my.springmvc.enjoy.resolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * Auto Created by IntelliJ IDEA.
 *
 * @author liutao
 * @since 2020/4/19
 */
public interface ArgumentResolver {

    boolean support(Class<?> type, int index, Method method);

    Object argumentResolver(HttpServletRequest request, HttpServletResponse response, Class<?> type, int index,
            Method method);
}
