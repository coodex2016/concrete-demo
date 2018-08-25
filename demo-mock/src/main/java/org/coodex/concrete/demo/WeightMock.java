package org.coodex.concrete.demo;

import org.coodex.pojomocker.Mock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Mock // 声明该注解是用于mock的
public @interface WeightMock {
}
