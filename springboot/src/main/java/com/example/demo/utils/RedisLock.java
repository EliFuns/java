package com.example.demo.utils;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

public class RedisLock {
    private static final String LOCK_PREFIX = "REDIS_LOCK_";
    private static final long SLEEP_TIME = 50; // ms

    private RedisTemplate redisTemplate;

    public RedisLock(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Boolean tryLock(String key) {
        return tryLock(key,3L, TimeUnit.SECONDS);
    }

    public Boolean tryLock(String key, long timeout, TimeUnit unit) {
        key = LOCK_PREFIX + key;
        long expireAt = System.currentTimeMillis() + unit.toMillis(timeout) + 1;
        Boolean b = false;
        do {
            b = redisTemplate.boundValueOps(key).setIfAbsent(String.valueOf(expireAt), timeout, unit);
            try {
                TimeUnit.MILLISECONDS.sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
            }
        } while (!b && System.currentTimeMillis() < expireAt);
        return b;
    }

    public Boolean unlock(String key) {
        key = LOCK_PREFIX + key;
        return redisTemplate.delete(key);
    }
}
