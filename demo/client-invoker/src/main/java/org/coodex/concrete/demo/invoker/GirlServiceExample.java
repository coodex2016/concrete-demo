package org.coodex.concrete.demo.invoker;

import com.alibaba.fastjson.JSON;
import org.coodex.concrete.Client;
import org.coodex.concrete.demo.api.GirlService;
import org.coodex.concrete.demo.api.pojo.Girl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GirlServiceExample {
    private final static Logger log = LoggerFactory.getLogger(GirlServiceExample.class);

    private static <T> T trace(T object){
        log.debug(JSON.toJSONString(object));
        return object;
    }

    public static void main(String [] args){
        GirlService girlService = Client.getInstance(GirlService.class, "demo");

        String [] girls = new String[]{"Lele", "Feifei", "Qingqing", "Yanyan", "Davidoff"};

        for(String name: girls){
            girlService.saveGirl(new Girl(name));
        }
        Girl davidoff = trace(girlService.get("Davidoff"));
        davidoff.setAge(41);
        girlService.updateGirlInfo("Davidoff", davidoff);
        trace(girlService.get());
        girlService.deleteByName("Davidoff");
        trace(girlService.get());
        Girl girl = trace(girlService.get("Lele"));
        girl.setStars(100);
        girlService.updateGirlInfo("Lele", girl);
        trace(girlService.getStars("Lele"));
    }
}
