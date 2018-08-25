package org.coodex.concrete.demo.impl.interceptor;

import org.coodex.concrete.common.Token;
import org.coodex.concrete.core.intercept.AbstractTokenBasedTopicSubscribeInterceptor;
import org.coodex.concrete.demo.api.pojo.NewGirlComing;
import org.coodex.concrete.message.MessageConsumer;
import org.coodex.concrete.message.MessageFilter;

import javax.inject.Inject;
import javax.inject.Named;

@Named
@MessageConsumer(queue = "girlComing")
public class AllGirlSubscribeInterceptor
        extends AbstractTokenBasedTopicSubscribeInterceptor<NewGirlComing> {

    private static final String key_all_girls = "all girl message subscribed";
    @Inject
    private Token token;

    private boolean isSubscribed() {
        Boolean b = token.getAttribute(key_all_girls, Boolean.class);
        return b != null && b;
    }

    @Override
    protected MessageFilter<NewGirlComing> subscribe() {
        token.setAttribute(key_all_girls, Boolean.valueOf(true));

        return new MessageFilter<NewGirlComing>() {
            @Override
            public boolean handle(NewGirlComing message) {
                return true;
            }
        };
    }

    @Override
    protected boolean check() {
        return !isSubscribed();
    }

    @Override
    protected boolean checkAccountCredible() {
        return false;
    }
}
