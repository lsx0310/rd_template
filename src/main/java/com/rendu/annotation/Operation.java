package com.rendu.annotation;

import java.lang.annotation.*;

/**
 * @ClassName Operation
 * @Description: TODO
 * @Author: li
 * @Date: 2020/4/1 10:17
 * @Version v1.0
 **/

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Operation {
    
    String value() default "";
}
