package org.coodex.concrete.generators;

import org.coodex.concrete.apitools.API;
import org.coodex.concrete.demo.api.DemoService;

import java.io.IOException;

public class XGen {

    public static void main(String [] args) throws IOException {

        API.generateFor("demo.doc",
                DemoService.class.getPackage().getName());

        //生成jQuery调用服务的代码
        API.generateFor("demo.jquery",
                DemoService.class.getPackage().getName());
    }
}
