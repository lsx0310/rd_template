package com.rendu.annotation;

import java.lang.annotation.*;

/**
 * @ClassName JwtIgnore
 * @Description: TODO  JWT验证忽略注解 可作用于类和方法上
 * @Author: li
 * @Date: 2020/3/7 16:09
 * @Version v1.0
 **/

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JwtIgnore {
}
