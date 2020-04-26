package com.rendu.config.Thread;


import com.rendu.Application;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName ThreadPoolExecutorUtil
 * @Description: TODO
 * @Author: li
 * @Date: 2020/4/16 11:36
 * @Version v1.0
 **/
public class ThreadPoolExecutorUtil {
    private static AnnotationConfigApplicationContext application = new AnnotationConfigApplicationContext(Application.class);
    
    public static ThreadPoolExecutor getPoll(){
        AsyncTaskProperties properties = application.getBean(AsyncTaskProperties.class);
        return new ThreadPoolExecutor(
                properties.getCorePoolSize(),
                properties.getMaxPoolSize(),
                properties.getKeepAliveSeconds(),
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(properties.getQueueCapacity()),
                new ThreadFactoryName(),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }
}
