package org.coodex.concrete.demo.impl;

import org.coodex.concrete.common.IF;
import org.coodex.concrete.common.Subjoin;
import org.coodex.concrete.common.Token;
import org.coodex.concrete.demo.api.DemoService;
import org.coodex.concrete.demo.api.pojo.VehiclePlate;

import javax.inject.Inject;
import javax.inject.Named;

import static org.coodex.concrete.demo.api.DemoErrorCodes.TOO_HARD;

@Named // 使用javax.inject规范，把该class定义成一个可被注入的对象
public class DemoServiceImpl implements DemoService {

    // step 3.2
    @Inject
    private Token token;
    @Inject
    private Subjoin subjoin;

    @Override
    public int add(int x1, int x2) {
        // step 3.4
        IF.is(x1 > 10 || x2 > 10 || x1 < 0 || x2 < 0, TOO_HARD, x1, x2);
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
