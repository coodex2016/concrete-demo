package org.coodex.concrete.demo.api;

import org.coodex.concrete.api.ConcreteService;
import org.coodex.concrete.api.MicroService;
import org.coodex.util.Parameter;

@MicroService()
public interface DemoService extends ConcreteService {

    /**
     * x1 + x2 = ?
     *
     * @param x1
     * @param x2
     * @return x1 + x2
     */
    int add(@Parameter("x1") int x1,
            @Parameter("x2") int x2);

    /**
     * say hello to %name%
     *
     * @param name
     * @return hello %name%
     */
    String sayHello(@Parameter("name") String name);

}
