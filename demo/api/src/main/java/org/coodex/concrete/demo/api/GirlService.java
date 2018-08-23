package org.coodex.concrete.demo.api;

import org.coodex.concrete.api.ConcreteService;
import org.coodex.concrete.api.MicroService;
import org.coodex.concrete.demo.api.pojo.Girl;
import org.coodex.util.Parameter;

import java.util.List;

@MicroService("Girls")
public interface GirlService extends ConcreteService {

    List<Girl> get();

    Girl get(@Parameter("name") String name);

    @MicroService
    void saveGirl(@Parameter("girl") Girl girl);

    @MicroService
    void deleteByName(@Parameter("name") String name);

    @MicroService("{name}")
    void updateGirlInfo(@Parameter("name") String name,
                        @Parameter("girl") Girl girl);

    @MicroService("{name}/stars")
    Integer getStars(@Parameter("name") String name);
}
