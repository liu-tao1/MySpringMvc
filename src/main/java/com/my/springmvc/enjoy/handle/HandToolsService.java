package com.my.springmvc.enjoy.handle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Auto Created by IntelliJ IDEA.
 *
 * @author liutao
 * @since 2020/4/19
 */
public interface HandToolsService {

    Object[] hand(HttpServletRequest request, HttpServletResponse response, Method method, Map<String, Object> beans);
}
