package org.coodex.concrete.demo.impl;

import org.coodex.concrete.common.IF;
import org.coodex.concrete.demo.api.GirlService;
import org.coodex.concrete.demo.api.pojo.Girl;
import org.coodex.concrete.demo.impl.copier.GirlCopier;
import org.coodex.concrete.message.Queue;
import org.coodex.concrete.message.Topic;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
public class GirlServiceImpl implements GirlService {

    // step 3.6
    @Queue("girlComing")
    private Topic<Girl> girlComingTopic;

    // step 3.6
    @Queue("girlGone")
    private Topic<Girl> girlGoneTopic;

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
    public void saveGirl(Girl girl) {
        IF.is(girlsMap.containsKey(girl.getName()), "Girl exists.");
        girlsMap.put(girl.getName(), girl);
        // step 3.6
        girlComingTopic.publish(girl);
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
