package org.coodex.concrete.demo.impl;

import org.coodex.concrete.common.IF;
import org.coodex.concrete.common.Token;
import org.coodex.concrete.demo.api.GirlService;
import org.coodex.concrete.demo.api.pojo.Girl;
import org.coodex.concrete.demo.api.pojo.NewGirlComing;
import org.coodex.concrete.demo.impl.copier.GirlCopier;
import org.coodex.concrete.message.Queue;
import org.coodex.concrete.message.TokenBasedTopic;
import org.coodex.concrete.message.Topic;
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

    private final ScheduledExecutorService scheduledExecutorService =
            ExecutorsHelper.newSingleThreadScheduledExecutor();
    @Inject
    @Queue("girlComing")
    private Topic<Girl> girlComingTopic;
    @Inject
    @Queue("girlGone")
    private Topic<Girl> girlGoneTopic;
    @Inject
    @Queue("girlComing")
    private TokenBasedTopic<NewGirlComing> newGirlComing;
    @Inject
    private Token token;
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


    @Override
    public void subscribe() {
        // 3.7.2
        token.setAttribute("a", "a");
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
        if (girlsMap.containsKey(name)) {
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
