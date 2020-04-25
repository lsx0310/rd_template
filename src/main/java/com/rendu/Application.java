package com.rendu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @ClassName Application
 * @Description: TODO
 * @Author: li
 * @Date: 2020/3/27 16:01
 * @Version v1.0
 **/
@MapperScan("com.rendu.mapper")
@EnableCaching
@SpringBootApplication
@EnableAspectJAutoProxy
@EnableAsync
public class Application {
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
