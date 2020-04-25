package com.rendu.aspect;

import com.rendu.annotation.Operation;
import com.rendu.model.OperationLog;
import com.rendu.service.impl.OperationLogServiceImpl;
import com.rendu.utils.IpUtil;
import io.jsonwebtoken.Claims;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @ClassName OperationAspect
 * @Description: TODO
 * @Author: li
 * @Date: 2020/4/1 10:19
 * @Version v1.0
 **/

@Aspect
@Component
public class OperationAspect {


    
    @Autowired
    OperationLogServiceImpl operationLogService;
    
    @Pointcut("@annotation(com.rendu.annotation.Operation)")
    public void pointCut(){}
    
    @Transactional
    @AfterReturning("pointCut()")
    public void saveOperationLog(JoinPoint joinPoint){
        
        OperationLog operationLog = OperationLog.getInstance();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        ServletRequestAttributes sra =  (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sra.getRequest();
        Object[] objects = joinPoint.getArgs();
        StringBuffer stringBuffer = new StringBuffer();
        for (Object o : objects){
            stringBuffer.append(o.toString());
        }

        Map<String,Object> claims = (Claims) request.getAttribute("claims");
        System.out.println(claims.toString());
        operationLog.setUserName((String) claims.get("userName"));
        operationLog.setUserIp(IpUtil.getIpAddr(request));
        operationLog.setOperationText(stringBuffer.toString());
        

        
        //获取操作
        Operation operation = method.getAnnotation(Operation.class);
        if (operation != null){
            String value = operation.value();
            operationLog.setRequestDesc(value);
        }
    
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        operationLog.setCreateTime(df.format(new Date()));
        operationLogService.save(operationLog);
    }

}
