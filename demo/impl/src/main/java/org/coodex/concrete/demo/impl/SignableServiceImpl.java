package org.coodex.concrete.demo.impl;

import org.coodex.concrete.demo.api.SignableService;
import org.coodex.concrete.demo.api.pojo.Girl;
import org.coodex.concrete.demo.api.pojo.VehiclePlate;

import javax.inject.Named;

@Named
public class SignableServiceImpl implements SignableService {

    @Override
    public String example(Girl girl, VehiclePlate vehiclePlate) {
        return String.format("%s has a car: %s", girl.getName(), vehiclePlate.getCode());
    }
}
