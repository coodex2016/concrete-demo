package org.coodex.concrete.demo.invoker;

import org.coodex.concrete.Client;
import org.coodex.concrete.demo.api.SignableService;
import org.coodex.concrete.demo.api.pojo.Girl;
import org.coodex.concrete.demo.api.pojo.VehiclePlate;
import org.coodex.util.Common;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SignatureExample {

    private final static Logger log = LoggerFactory.getLogger(SignatureExample.class);

    private static void invoke(String girlName, String carCode) {
        SignableService signableService = Client.getInstance(SignableService.class, girlName);
        Girl girl = new Girl(girlName);
        VehiclePlate vehiclePlate = new VehiclePlate();
        vehiclePlate.setCode(carCode);
        if (!Common.isBlank(carCode))
            vehiclePlate.setColor(2);
        log.info(signableService.example(girl, vehiclePlate));
    }

    public static void main(String[] args) {
        invoke("Lele", "中A8*228");
        invoke("Feifei", "国APX215");
        invoke("Nana", "牛B74110");
        invoke("Davidoff", null);
    }
}
