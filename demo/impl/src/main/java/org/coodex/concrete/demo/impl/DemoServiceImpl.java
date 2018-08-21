package org.coodex.concrete.demo.impl;

import org.coodex.concrete.demo.api.DemoService;

import javax.inject.Named;

@Named // 使用javax.inject规范，把该class定义成一个可被注入的对象
public class DemoServiceImpl implements DemoService {
    @Override
    public int add(int x1, int x2) {
        return x1 + x2;
    }

    @Override
    public String sayHello(String name) {
        return String.format("Hello %s!", name);
    }
}
