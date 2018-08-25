package org.coodex.concrete.demo.api.pojo;

import org.coodex.concrete.message.MessageSubject;
import org.coodex.util.Common;

import java.io.Serializable;

@MessageSubject("newGirlComing")
public class NewGirlComing implements Serializable {
    private Girl girl;
    private String arrived = Common.now();

    public Girl getGirl() {
        return girl;
    }

    public void setGirl(Girl girl) {
        this.girl = girl;
    }

    public String getArrived() {
        return arrived;
    }

    public void setArrived(String arrived) {
        this.arrived = arrived;
    }
}
