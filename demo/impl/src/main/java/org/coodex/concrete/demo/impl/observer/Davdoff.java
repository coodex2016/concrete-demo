package org.coodex.concrete.demo.impl.observer;

import org.coodex.concrete.demo.api.pojo.Girl;
import org.coodex.concrete.message.MessageConsumer;
import org.coodex.concrete.message.Observer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;

@Named
@MessageConsumer(queue = "girlComing")
public class Davdoff implements Observer<Girl> {
    private final static Logger log = LoggerFactory.getLogger(Davdoff.class);

    @Override
    public void update(Girl message) throws Throwable {
        if (message != null) {
            if (!"Davidoff".equals(message.getName())) {
                log.info("Hi, {}.", message.getName());
            } else {
                log.info("......");
            }
        }
    }
}
