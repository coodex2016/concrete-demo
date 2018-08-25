package org.coodex.concrete.demo.api.pojo;

import org.coodex.concrete.api.mockers.Name;
import org.coodex.concrete.demo.WeightMock;
import org.coodex.pojomocker.annotations.INTEGER;

public class Girl {

    private String name;
    private Integer height;
    private Integer weight;
    private Integer age;
    private Integer stars = 0;

    public Girl() {
    }

    public Girl(String name) {
        this.name = name;
    }

    @Name //随机姓名
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @INTEGER(min = 0, max = 100)
    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    @INTEGER(min = 150, max= 170)
    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    @WeightMock // 新建的Mock
    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    @INTEGER(min = 18, max = 36)
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
