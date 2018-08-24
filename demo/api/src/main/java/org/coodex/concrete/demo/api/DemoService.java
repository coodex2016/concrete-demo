package org.coodex.concrete.demo.api;

import org.coodex.concrete.api.*;
import org.coodex.concrete.demo.api.pojo.VehiclePlate;
import org.coodex.util.Parameter;

@Description(
        name = "demo模块",
        description = "本模块主要通过一些简单的示例，向大家介绍[concrete](https://concrete.coodex.org)的功能。blablabla..."
)
@MicroService("I/love/concrete")
public interface DemoService extends ConcreteService {

    @Description(
            name = "求两数之和",
            description = "不多说，都会"
    )
    int add(
            @Description(name = "被加数")
            @Parameter("x1") int x1,
            @Description(name = "加数")
            @Parameter("x2") int x2);

    @Description(
            name = "就是个示意，编不出来了 :("
    )
    // step 3.1
    @AccessAllow(roles = "A")
    @Safely
    String sayHello(
            @Description(name = "要say hello的名字")
            @Parameter("name") String name);

    @Description(
            name = "获取一个车牌",
            description = "一会演示mock用"
    )
    // step 3.1
    @AccessAllow(roles = {"A", "B"})
    VehiclePlate randomPlate();

}
