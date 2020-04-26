package com.rendu.config;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * \* User: li
 * \* Date: 2020/1/13
 * \* Time: 20:13
 * \* Description:
 */

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<Object,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<Object,Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
    
        FastJsonRedisSerializer<Object> fastJsonRedisSerializer =  new FastJsonRedisSerializer<>(Object.class);
    
        //设置全局
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        template.setValueSerializer(fastJsonRedisSerializer);
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(fastJsonRedisSerializer);
        template.setEnableTransactionSupport(true);
        template.afterPropertiesSet();
        
        return template;
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory){
        RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig();
        RedisCacheConfiguration teacherConfig = defaultConfig.entryTtl(Duration.ofMinutes(1))
                .disableCachingNullValues();
        RedisCacheConfiguration studentConfig = defaultConfig.entryTtl(Duration.ofMinutes(120));

        Set<String> cacheNames = new HashSet<>();
        cacheNames.add("teacherCache");
        cacheNames.add("studentCache");

        Map<String,RedisCacheConfiguration> configMap = new HashMap<>();
        configMap.put("teacherCache",teacherConfig);
        configMap.put("studentCache",studentConfig);

        RedisCacheManager cacheManager = RedisCacheManager.builder(redisConnectionFactory)
                .initialCacheNames(cacheNames)
                .withInitialCacheConfigurations(configMap)
                .build();

        return cacheManager;
    }

    @Bean("RKeyGenerator")
    public KeyGenerator keyGenerator(){
        KeyGenerator keyGenerator = new KeyGenerator() {
            @Override
            public Object generate(Object o, Method method, Object... objects) {
                return o.toString() + "[" + objects[0].toString() + "]";
            }
        };
        return keyGenerator;
    }
}
