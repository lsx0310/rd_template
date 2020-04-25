package com.rendu.config.Thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName AsyncTaskExecutePool
 * @Description: TODO   异步任务线程池装配类
 * @Author: li
 * @Date: 2020/4/15 17:04
 * @Version v1.0
 **/
@Slf4j
@Configuration
public class AsyncTaskExecutePool implements AsyncConfigurer {
    
    /*注入配置类*/
    private final AsyncTaskProperties executeConfig;
    
    public AsyncTaskExecutePool(AsyncTaskProperties executeConfig) {
        this.executeConfig = executeConfig;
    }
    
    @Bean
    @Override
    public ExecutorService getAsyncExecutor() {
    
        //核心线程池大小
        //最大线程数
        //队列容量
        //活跃时间
        //线程名字前缀
        // setRejectedExecutionHandler：当pool已经达到max size的时候，如何处理新任务
        // CallerRunsPolicy：不在新线程中执行任务，而是由调用者所在的线程来执行

        return new ThreadPoolExecutor(executeConfig.getCorePoolSize(),
                executeConfig.getMaxPoolSize(),
                executeConfig.getKeepAliveSeconds(),
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(10),
                new ThreadFactoryBuilder().setNameFormat("WaterSafety-async-").setDaemon(true).build(),
                new ThreadPoolExecutor.CallerRunsPolicy());
    }
    
    //异步执行引发异常返回的javabean
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (throwable, method, objects) -> {
            log.error("===="+throwable.getMessage()+"====", throwable);
            log.error("exception method:"+method.getName());
        };
    }
}
