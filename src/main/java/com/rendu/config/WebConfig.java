package com.rendu.config;

import com.rendu.component.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * @ClassName WebConfig
 * @Description: TODO
 * @Author: li
 * @Date: 2020/3/7 15:46
 * @Version v1.0
 **/
@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    private static final String[] excludePaths = {"/", "/index.html", "/user/login","/webjars/**","/swagger**","/swagger-resources/**", "/webjars/**", "/v2/**",
            "/swagger-ui.html/**","/csrf/**", "/static/**", "/static/*","/favicon.ico","/img/**","/pdf/**"};

    @Bean
    public JwtInterceptor jwtInterceptor(){
        return new JwtInterceptor();
    }
    
    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        WebMvcConfigurer webMvcConfigurer = new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(jwtInterceptor()).excludePathPatterns(excludePaths);
            }
            
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
            }
    
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedHeaders("*")
                        .allowCredentials(true)
                        .allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH", "OPTIONS", "HEAD")
                        .exposedHeaders("Authorization")
                        .maxAge(3600 * 24);
            }
        };
        return webMvcConfigurer;
    }
    
}
