package org.coodex.concrete.demo.impl;

import org.coodex.concrete.ConcreteClient;
import org.coodex.concrete.apm.APM;
import org.coodex.concrete.common.IF;
import org.coodex.concrete.common.Token;
import org.coodex.concrete.demo.api.DemoService;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Named
public class GirlServiceImpl implements GirlService {

    private final ScheduledExecutorService scheduledExecutorService =
            ExecutorsHelper.newSingleThreadScheduledExecutor();

    private final ExecutorService executorService = ExecutorsHelper.newFixedThreadPool(5);
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

    @Inject
    @ConcreteClient("demoClient")
    private DemoService demoService;

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

        // step 3.8
        List<Runnable> runnables = new ArrayList<Runnable>();
        for(int i = 0; i < 10; i ++){
            final int finalI = i;
            runnables.add(new Runnable() {
                @Override
                public void run() {
                    demoService.add(finalI,finalI);
                }
            });
        }

        APM.parallel(executorService, runnables.toArray(new Runnable[0]));
    }

    @Override
    public void deleteByName(String name) {
        // step 3.6
        if (girlsMap.containsKey(name)) {
            Girl girl = girlsMap.get(name);
            girlsMap.remove(name);
            girlGoneTopic.publish(girl);
        }

        // step 3.8
        // 串行示例
        demoService.add(6,6);

        List<Runnable> runnables = new ArrayList<Runnable>();
        for(int i = 0; i < 5; i ++){
            final int finalI = i;
            runnables.add(new Runnable() {
                @Override
                public void run() {
                    demoService.add(finalI,finalI);
                }
            });
        }
        APM.parallel(executorService, runnables.toArray(new Runnable[0]));
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
