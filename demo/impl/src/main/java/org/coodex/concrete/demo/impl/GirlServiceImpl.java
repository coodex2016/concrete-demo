package org.coodex.concrete.demo.impl;

import org.coodex.concrete.common.ConcreteHelper;
import org.coodex.concrete.common.IF;
import org.coodex.concrete.common.Token;
import org.coodex.concrete.demo.api.GirlService;
import org.coodex.concrete.demo.api.pojo.Girl;
import org.coodex.concrete.demo.api.pojo.NewGirlComing;
import org.coodex.concrete.demo.impl.copier.GirlCopier;
import org.coodex.concrete.message.*;
import org.coodex.concurrent.ExecutorsHelper;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Named
public class GirlServiceImpl implements GirlService {

    @Queue("girlComing")
    private Topic<Girl> girlComingTopic;

    @Queue("girlGone")
    private Topic<Girl> girlGoneTopic;

    // step 3.7.1
    @Queue("girlComing")
    private TokenBasedTopic<NewGirlComing> newGirlComing;

    @Inject
    private Token token;

    private final ScheduledExecutorService scheduledExecutorService =
            ExecutorsHelper.newSingleThreadScheduledExecutor();

    @Inject
    private GirlCopier girlCopier;

    private Map<String, Girl> girlsMap = new HashMap<String, Girl>();

    @Override
    public List<Girl> get() {
        return new ArrayList<Girl>(girlsMap.values());
    }

    @Override
    public Girl get(String name) {
        return IF.isNull(girlsMap.get(name), "Girl not found");
    }

    private static final String key_all_girls = "all girl message subscribed";

    private boolean isSubscribed(){
        Boolean b = token.getAttribute(key_all_girls, Boolean.class);
        return b != null && b;
    }
    @Override
    public void subscribe() {
        if(!isSubscribed()) {
            synchronized (this) {
                if(!isSubscribed()) {
                    token.setAttribute(key_all_girls, Boolean.valueOf(true));

                    newGirlComing.subscribe(new MessageFilter<NewGirlComing>() {
                        @Override
                        public boolean handle(NewGirlComing message) {
                            return true;
                        }
                    });
                }
            }
        }
    }

    @Override
    public void saveGirl(Girl girl) {
        IF.is(girlsMap.containsKey(girl.getName()), "Girl exists.");
        girlsMap.put(girl.getName(), girl);
        girlComingTopic.publish(girl);

        // step 3.7.1
        final NewGirlComing newGirl = new NewGirlComing();
        newGirl.setGirl(girl);
        scheduledExecutorService.schedule(new Runnable() {
            @Override
            public void run() {
                newGirlComing.publish(newGirl);
            }
        }, 5, TimeUnit.SECONDS);

    }

    @Override
    public void deleteByName(String name) {
        // step 3.6
        if(girlsMap.containsKey(name)) {
            Girl girl = girlsMap.get(name);
            girlsMap.remove(name);
            girlGoneTopic.publish(girl);
        }
    }

    @Override
    public void updateGirlInfo(String name, Girl girl) {
        Girl g = get(name);
        girlCopier.copy(girl, g);
    }

    @Override
    public Integer getStars(String name) {
        return get(name).getStars();
    }
}
