package com.my.springmvc.enjoy.controller;

import com.my.springmvc.enjoy.annotation.LtController;
import com.my.springmvc.enjoy.annotation.LtQualifier;
import com.my.springmvc.enjoy.annotation.LtRequestMapping;
import com.my.springmvc.enjoy.annotation.LtRequestParam;
import com.my.springmvc.enjoy.service.MyService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义MyController
 *
 * @author liutao
 * @since 2020/4/19
 */
@LtController
@LtRequestMapping("/liutao")
public class MyController {

    @LtQualifier("myService")
    private MyService myService;

    @LtRequestMapping("/query")
    public void query(HttpServletRequest request, HttpServletResponse response,
            @LtRequestParam(value = "name") String name, @LtRequestParam(value = "age") String age) {

        String result = myService.query(name, age);

        try {
            response.getWriter().write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
