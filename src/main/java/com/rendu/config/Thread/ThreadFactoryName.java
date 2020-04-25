package com.rendu.config.Thread;

import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName ThreadFactoryName
 * @Description: TODO   自定义线程名称
 * @Author: li
 * @Date: 2020/4/16 11:18
 * @Version v1.0
 **/
@Component
public class ThreadFactoryName implements ThreadFactory {
    
    private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);
    private final ThreadGroup group;
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String namePrefix;
    
    
    public ThreadFactoryName() {
        this("waterSafety-pool");
    }
    
    public ThreadFactoryName(String name) {
         group = Thread.currentThread().getThreadGroup();
         this.namePrefix = name + POOL_NUMBER.getAndIncrement();
    }
    
    @Override
    public Thread newThread(Runnable r) {
        
        //此时线程的名字 就是 namePrefix + -thread- + 这个线程池中第几个执行的线程
        Thread t = new Thread(group, r, namePrefix + "-thread-" + threadNumber.getAndIncrement());
        if (t.isDaemon()) {
            t.setDaemon(false);
        }
        if (t.getPriority() != Thread.NORM_PRIORITY){
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    }
}
