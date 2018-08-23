package org.coodex.concrete.demo.invoker;

import org.coodex.concrete.Client;
import org.coodex.concrete.demo.api.GirlService;
import org.coodex.concrete.demo.api.pojo.Girl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GirlServiceExample {
    private final static Logger log = LoggerFactory.getLogger(GirlServiceExample.class);

    public static void main(String [] args){
        GirlService girlService = Client.getInstance(GirlService.class, "demo");

        String [] girls = new String[]{"Lele", "Feifei", "Qingqing", "Yanyan", "Davidoff"};

        for(String name: girls){
            girlService.saveGirl(new Girl(name));
        }
        Girl davidoff = girlService.get("Davidoff");
        davidoff.setAge(41);
        girlService.updateGirlInfo("Davidoff", davidoff);
        girlService.get();
        girlService.deleteByName("Davidoff");
        girlService.get();
        Girl girl = girlService.get("Lele");
        girl.setStars(100);
        girlService.updateGirlInfo("Lele", girl);
        girlService.getStars("Lele");
    }
}
