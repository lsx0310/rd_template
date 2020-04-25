package com.rendu.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName RedisUtils
 * @Description: TODO
 * @Author: li
 * @Date: 2020/4/16 18:58
 * @Version v1.0
 **/

@Component(value = "redisUtils")
@SuppressWarnings({"unchecked","all"})
public class RedisUtils {
    

    private RedisTemplate<Object,Object> redisTemplate;
    
    @Value("${audience.onlineKey}")
    private String onlineKey;
    
    public RedisUtils(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    
    /* 指定缓存失效时间
    * */
    
    public boolean expire(String key,long time){
        try {
            if (time > 0){
                redisTemplate.expire(key,time, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    /* 根据 key 获取过期时间
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
    * */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key,TimeUnit.SECONDS);
    }
    
    
    /*
    *  查找匹配key
    * */
    public List<String> scan(String pattern){
        ScanOptions options = ScanOptions.scanOptions().match(pattern).build();
        RedisConnectionFactory factory = redisTemplate.getConnectionFactory();
        RedisConnection rc = Objects.requireNonNull(factory.getConnection());
        Cursor<byte[]> cursor =  rc.scan(options);
        List<String> result = new ArrayList<>();
        while (cursor.hasNext()){
            result.add(new String((cursor.next())));
        }
    
        try {
            RedisConnectionUtils.releaseConnection(rc,factory,true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    /* 判断key是否存在
    * */
    public boolean hasKey(String key){
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    /**
     * 删除缓存
     * @param key 可以传一个值 或多个
     */
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }
    
    
    //====================================String=======================================
    /* 普通缓存获取
    * */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }
    
    
    /* 批量获取
    * */
    public List<Object> multiGet(List<String> keys) {
        Object obj = redisTemplate.opsForValue().multiGet(Collections.singleton(keys));
        return null;
    }
    
    /**
     * 普通缓存放入
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    
    public boolean set(String key,Object value){
        try {
            redisTemplate.opsForValue().set(key,value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 普通缓存放入并设置时间
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key,Object value,long time){
        try {
            if (time > 0){
                redisTemplate.opsForValue().set(key,value,time,TimeUnit.SECONDS);
            } else {
                set(key,value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 普通缓存放入并设置时间
     * @param key   键
     * @param value 值
     * @param time  时间
     * @param timeUnit 类型
     * @return true成功 false 失败
     */
    public boolean set(String key,Object value,long time,TimeUnit timeUnit){
        try {
            if (time > 0){
                redisTemplate.opsForValue().set(key,value,time,timeUnit);
            } else {
                set(key,value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
