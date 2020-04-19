package com.my.springmvc.enjoy.service.impl;

import com.my.springmvc.enjoy.annotation.LtService;
import com.my.springmvc.enjoy.service.MyService;

/**
 * Auto Created by IntelliJ IDEA.
 *
 * @author liutao
 * @since 2020/4/19
 */
@LtService("myService")
public class MyServiceImpl implements MyService {
    @Override
    public String query(String name, String age) {
        return "my name = " + name + ";my age = " + age;
    }
}
