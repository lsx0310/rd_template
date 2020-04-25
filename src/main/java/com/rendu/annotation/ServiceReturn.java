package com.rendu.annotation;

import java.lang.annotation.*;

/**
 * @ClassName ServivceReturn
 * @Description: TODO                 用于统一处理数据的返回类型
 * @Author: li
 * @Date: 2020/4/21 11:03
 * @Version v1.0
 **/
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ServiceReturn {
    
    /*  可以配置返回的类型
        exp:  JsonObject...
        default JSONArray
    * */
    String value() default "";
}
