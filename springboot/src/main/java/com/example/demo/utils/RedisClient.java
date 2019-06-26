package com.example.demo.utils;

import com.alibaba.fastjson.JSON;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

@Component
public class RedisClient /*implements InitializingBean */{
    @Autowired
    private StringRedisTemplate redisTemplate;

    private RedisLock lock;

    public RedisLock getLock() {
        return lock;
    }

    //    @Override
//    public void afterPropertiesSet() throws Exception {
//        lock = new RedisLock(redisTemplate);
//    }
    @PostConstruct
    public void initLock(){
        lock = new RedisLock(redisTemplate);
    }

    public void put(final String key, final Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, JSON.toJSONString(value), timeout, unit);
    }

    public void put(final String key, final Object value) {
        redisTemplate.opsForValue().set(key, JSON.toJSONString(value));
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public <T> T get(final String key, Class<T> clazz) {
        String s = redisTemplate.opsForValue().get(key);
        if (s == null) {
            return null;
        }
        return JSON.parseObject(s, clazz);
    }

    public Set<String> keys(final String pattern) {
        return redisTemplate.keys(pattern);
    }

    public Boolean expire(final String key, final long timeout, final TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    public Boolean delete(final String key) {
        return redisTemplate.delete(key);
    }

}
