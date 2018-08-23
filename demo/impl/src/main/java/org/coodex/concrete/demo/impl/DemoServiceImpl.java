package org.coodex.concrete.demo.impl;

import org.coodex.concrete.common.Subjoin;
import org.coodex.concrete.common.Token;
import org.coodex.concrete.demo.api.DemoService;
import org.coodex.concrete.demo.api.pojo.VehiclePlate;

import javax.inject.Inject;
import javax.inject.Named;

@Named // 使用javax.inject规范，把该class定义成一个可被注入的对象
public class DemoServiceImpl implements DemoService {

    // step 3.2
    @Inject
    private Token token;
    @Inject
    private Subjoin subjoin;

    @Override
    public int add(int x1, int x2) {
        System.out.println(String.format("%s, %s",
                token.currentAccount().getId().serialize(),
                subjoin.get("key")));
        return x1 + x2;
    }

    @Override
    public String sayHello(String name) {
        return String.format("Hello %s!", name);
    }

    @Override
    public VehiclePlate randomPlate() {
        return null;
    }
}
