package org.coodex.concrete.demo.impl.observer;

import org.coodex.concrete.demo.api.pojo.Girl;
import org.coodex.concrete.message.MessageConsumer;
import org.coodex.concrete.message.Observer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;

@Named
@MessageConsumer(queue = "girlGone")
public class Qiqi implements Observer<Girl> {
    private final static Logger log = LoggerFactory.getLogger(Qiqi.class);

    @Override
    public void update(Girl message) throws Throwable {
        if (message != null) {
            log.info("Oh no, I can not let you go, {}", message.getName());
        }
    }
}
