package org.coodex.concrete.demo.invoker;

import org.coodex.concrete.Client;
import org.coodex.concrete.demo.api.DemoService;

public class ClientExample {

    public static void main(String [] args){
        DemoService demoService = Client.getInstance(DemoService.class, "demo");
        System.out.println(demoService.sayHello("davidoff"));
    }
}
