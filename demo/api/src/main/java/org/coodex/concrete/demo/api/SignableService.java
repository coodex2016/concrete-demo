package org.coodex.concrete.demo.api;

import org.coodex.concrete.api.ConcreteService;
import org.coodex.concrete.api.MicroService;
import org.coodex.concrete.api.Signable;
import org.coodex.concrete.demo.api.pojo.Girl;
import org.coodex.concrete.demo.api.pojo.VehiclePlate;
import org.coodex.util.Parameter;

@Signable
@MicroService
public interface SignableService extends ConcreteService {

    String example(
            @Parameter("girl") Girl girl,
            @Parameter("plate")VehiclePlate vehiclePlate);
}
