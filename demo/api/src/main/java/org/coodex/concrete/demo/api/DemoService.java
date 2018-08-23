package org.coodex.concrete.demo.api;

import org.coodex.concrete.api.ConcreteService;
import org.coodex.concrete.api.Description;
import org.coodex.concrete.api.MicroService;
import org.coodex.concrete.demo.api.pojo.VehiclePlate;
import org.coodex.concrete.jaxrs.Body;
import org.coodex.util.Parameter;

@Description(
        name = "demo模块",
        description = "本模块主要通过一些简单的示例，向大家介绍[concrete](https://concrete.coodex.org)的功能。blablabla..."
)
// step 3.0: 定义服务模块名
@MicroService("I/love/concrete")
public interface DemoService extends ConcreteService {

    @Description(
            name = "求两数之和",
            description = "不多说，都会"
    )
    // step 3.0: 定义接口名
    // step 3.0.1: 演示参数组合
    // @MicroService("andTeachMe/How/Much/is/{x1}/plus/{x2}/plz")
    int add(
            //step 3.0.1:参数组合
            @Body
            @Description(name = "被加数")
            @Parameter("x1") int x1,
            //step 3.0.1:参数组合
            @Body
            @Description(name = "加数")
            @Parameter("x2") int x2);

    @Description(
            name = "就是个示意，编不出来了 :("
    )
    String sayHello(
            // step 3.0.1: post数据
            @Body
            @Description(name = "要say hello的名字")
            @Parameter("name") String name);

    @Description(
            name = "获取一个车牌",
            description = "一会演示mock用"
    )
    VehiclePlate randomPlate();

}
