package org.coodex.concrete.demo.impl;

import org.coodex.concrete.common.IF;
import org.coodex.concrete.demo.api.GirlService;
import org.coodex.concrete.demo.api.pojo.Girl;
import org.coodex.concrete.demo.impl.copier.GirlCopier;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
public class GirlServiceImpl implements GirlService {

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
    }

    @Override
    public void deleteByName(String name) {
        girlsMap.remove(name);
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
