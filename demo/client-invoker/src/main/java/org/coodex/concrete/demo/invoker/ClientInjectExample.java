package org.coodex.concrete.demo.invoker;

import org.coodex.concrete.ConcreteClient;
import org.coodex.concrete.demo.api.DemoService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.inject.Inject;

public class ClientInjectExample {

    @Inject
    @ConcreteClient("demo")
    private DemoService demoService;

    public void example(){
        System.out.println(demoService.sayHello("inject"));
    }


    public static void main(String [] args){
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:example.xml");
        ClientInjectExample injectExample = context.getBean(ClientInjectExample.class);
        injectExample.example();
    }
}
