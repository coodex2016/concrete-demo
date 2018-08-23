package org.coodex.concrete.demo.impl.copier;

import org.coodex.concrete.common.AbstractCopier;
import org.coodex.concrete.demo.api.pojo.Girl;

import javax.inject.Named;

@Named
public class GirlCopier extends AbstractCopier<Girl,Girl> {
    @Override
    public Girl copy(Girl src, Girl target) {
        target.setName(src.getName());
        if(src.getHeight() != null)
            target.setHeight(src.getHeight());
        if(src.getAge() != null)
            target.setAge(src.getAge());
        if(src.getWeight() != null)
            target.setWeight(src.getWeight());
        if(src.getStars() != null)
            target.setStars(src.getStars());
        return target;
    }
}
