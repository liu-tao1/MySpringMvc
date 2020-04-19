package com.my.springmvc.enjoy.test;

import com.my.springmvc.enjoy.annotation.LtController;
import com.my.springmvc.enjoy.annotation.LtRequestParam;
import com.my.springmvc.enjoy.controller.MyController;
import com.my.springmvc.enjoy.resolver.ArgumentResolver;
import com.my.springmvc.enjoy.resolver.impl.RequestParamArgResolver;

/**
 * Auto Created by IntelliJ IDEA.
 *
 * @author liutao
 * @since 2020/4/19
 */
public class Test {

    public static void main(String[] args) {

        System.out.println(LtRequestParam.class.isAssignableFrom(LtRequestParam.class));

        System.out.println(RequestParamArgResolver.class.isAssignableFrom(ArgumentResolver.class));

        System.out.println(ArgumentResolver.class.isAssignableFrom(ArgumentResolver.class));

        System.out.println(MyController.class.isAnnotationPresent(LtController.class));
    }
}

