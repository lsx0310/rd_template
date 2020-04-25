package com.rendu.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rendu.annotation.ServiceReturn;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @ClassName ServiceReturnAspect
 * @Description: TODO
 * @Author: li
 * @Date: 2020/4/21 11:02
 * @Version v1.0
 **/

@Aspect
@Component
public class ServiceReturnAspect {
    
    @Pointcut("@annotation(com.rendu.annotation.ServiceReturn)")
    public void pointCut(){}
    
    private final static String JSON_OBJECT = "jsonObject";
    
    /* 做一个环绕通知，对Service层的返回值  做统一的处理
    * */
    @Around(value = "pointCut()", argNames = "proceedingJoinPoint")
    public Object doAroundService(ProceedingJoinPoint proceedingJoinPoint){
        try {
            Object proceed = proceedingJoinPoint.proceed();
            MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
            Method method = signature.getMethod();
            ServiceReturn serviceReturn = method.getAnnotation(ServiceReturn.class);
            if (StringUtils.isBlank(serviceReturn.value()) && proceed instanceof List){
                return processOutPut2Array(proceed);
            }
            if (JSON_OBJECT.equalsIgnoreCase(serviceReturn.value())){
                return processOutPut2Object(proceed);
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return new Object();
    }
    
    /* 处理返回对象为Json数组
    * */
    public JSONArray processOutPut2Array(Object obj){
        return JSONArray.parseArray(JSON.toJSONString(obj));
    }
    
    /* 处理返回对象为Json对象
    * */
    public JSONObject processOutPut2Object(Object obj){
        return (JSONObject) JSONObject.toJSON(obj);
    }
}
