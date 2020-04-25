package com.rendu.config.Thread;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName AsyncTaskProperties
 * @Description: TODO   线程池配置属性类
 * @Author: li
 * @Date: 2020/4/15 16:58
 * @Version v1.0
 **/

@Data
@Component
@ConfigurationProperties(prefix = "task.pool")
public class AsyncTaskProperties {
    
    private int corePoolSize;
    private int maxPoolSize;
    private int keepAliveSeconds;
    private int queueCapacity;
}
