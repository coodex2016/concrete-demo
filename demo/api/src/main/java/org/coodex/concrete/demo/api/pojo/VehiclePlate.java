package org.coodex.concrete.demo.api.pojo;

import org.coodex.concrete.api.Description;
import org.coodex.concrete.api.mockers.VehicleNum;
import org.coodex.pojomocker.annotations.INTEGER;

public class VehiclePlate {

    @VehicleNum
    private String code;

    @INTEGER(range = {0, 1, 2, 3, 4, 9})
    private Integer color;

    @Description(name = "车牌号码")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Description(name = "车牌颜色",
    description = "代码参照国家标准, 0: 白; 1: 黄; 2: 蓝; 3: 黑; 4: 绿; 9: 其他")
    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }
}
