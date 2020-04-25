package com.rendu.component.interceptor;

import com.rendu.annotation.JwtIgnore;
import com.rendu.component.Audience;
import com.rendu.utils.JwtUtils.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName JwtInterceptor
 * @Description: TODO
 * @Author: li
 * @Date: 2020/3/7 16:00
 * @Version v1.0
 **/
@Slf4j
public class JwtInterceptor extends HandlerInterceptorAdapter {
    
    @Autowired
    private Audience audience;
    
    private static final String[] excludePaths = {"/", "/index", "/static/index.html", "/user/login","/swagger-ui.html/**"};
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //忽略带JwtIgnore注解的请求，不作后续token认证效验
        System.out.println(request.getRequestURI());
        System.out.println(request.getRequestURL());
        if (handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            JwtIgnore jwtIgnore = handlerMethod.getMethodAnnotation(JwtIgnore.class);
            if (jwtIgnore != null){
                return true;
            }
        }
        
        /*Ajax 发送跨越请求时会先发送一个类型为HttpMethod.OPTIONS 的请求，进行验证是否能够通过
        * */
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())){
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }

        String token = request.getHeader(JwtTokenUtil.AUTH_HEADER_KEY);

        if (audience == null){
            BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
            audience = (Audience) factory.getBean("audience");
        }

        //验证token是否有效--无效已做异常抛出，由全局异常处理后返回对应信息
        Claims claims = JwtTokenUtil.parseJWT(token,audience.getBase64Secret());

        //验证token的有效时间,如果离过期还有5分钟那么更新token
        if (JwtTokenUtil.checkToken(claims)){
            token = JwtTokenUtil.createJWT((Integer) claims.get("userId"),
                    (String) claims.get("username"),
                    (String) claims.get("role"),
                    (String) claims.get("roleName"),
                    (String) claims.get("introduction"),audience);
            response.setHeader(JwtTokenUtil.AUTH_HEADER_KEY,JwtTokenUtil.TOKEN_PREFIX + token);
        }
        request.setAttribute("claims",claims);
        
        return true;
    }
}
