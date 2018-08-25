package org.coodex.concrete.demo;

import org.coodex.pojomocker.AbstractMocker;
import org.coodex.util.Common;

public class WeightMocker extends AbstractMocker<WeightMock> {
    @Override
    public Object mock(WeightMock mockAnnotation, Class clazz) {
        if (Float.class.equals(clazz) || float.class.equals(clazz)) {
            return (float) Common.random(70.0, 140.9);
        } else if (Integer.class.equals(clazz) || int.class.equals(clazz)) {
            return Common.random(70, 140);
        } else
            return null;
    }
}
