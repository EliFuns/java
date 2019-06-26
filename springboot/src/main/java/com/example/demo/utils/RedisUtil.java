package com.example.demo.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import redis.clients.jedis.Jedis;

/**
 * ClassName:RedisUtil
 * Description:
 */
public class RedisUtil {
    private static final Logger logger = LogManager.getLogger(RedisUtil.class);
    // redis连接池
//    private static ShardedJedisPool pool;
//    static {
//        JedisPoolConfig config = new JedisPoolConfig();
//        config.setMaxTotal(1000);
//        config.setMaxIdle(500);
//        config.setMaxWaitMillis(3000);
//        config.setTestOnBorrow(true);
//        config.setTestOnReturn(true);
//        // 集群
//        JedisShardInfo jedisShardInfo1 = new JedisShardInfo("127.0.0.1", 6379);
//        jedisShardInfo1.setPassword("lianer123456");
//        List<JedisShardInfo> list = new LinkedList<JedisShardInfo>();
//        list.add(jedisShardInfo1);
//        pool = new ShardedJedisPool(config, list);
//    }

    private static final Integer Lock_Timeout = 3;

    private Jedis jedis;


//    public RedisUtil setJedis(Jedis jedis) {
//        this.jedis = jedis;
//        return this;
//    }

    /**
     * 外部调用加锁的方法
     * @param lockKey 锁的名字
     * @param timeout 超时时间（放置时间长度，如：3L）
     * @return
     */
    public boolean tryLock(String lockKey, Long timeout) {
        try {
            //获取当前系统时间作为：开始加锁的时间
            Long currentTime = System.currentTimeMillis();
            // 默认为没有获取到锁
            boolean result = false;
            // 设置一个死循环，不断去获取锁，直接超过设置的 超时时间 为止
            while (true) {
                //当前时间超过了设定的超时时间，循环终止
                if ((System.currentTimeMillis() - currentTime) / 1000 > timeout) {
                    break;
                } else {
                    // 通过setnx()和判断上一把锁是否超时，来获取锁。获取则true
                    result = innerTryLock(lockKey);
                    if (result) {
                        break;
                    } else {
                        // 休眠0.1秒，降低服务器压力
                        Thread.sleep(100);
                    }
                }
            }
            return result;
        } catch (Exception e) {
            logger.info("e===="+ e);
            return false;
        }
    }

    /**
     * 释放锁
     * @param lockKey 锁的名字
     */
    public void realseLock(String lockKey) {
        // 如果当前时间已经超过 超时时间，则释放锁
        if(!checkIfLockTimeout(System.currentTimeMillis(), lockKey)){
            jedis.del(lockKey);
        }
    }

    /**
     * 内部获取锁的实现方法
     * @param lockKey 锁的名字
     * @return
     */
    private boolean innerTryLock(String lockKey) {
        //当前时间
        long currentTime = System.currentTimeMillis();
        //设置锁的持续时间
        String lockTimeDuration = String.valueOf(currentTime + Lock_Timeout + 1);
        Long result = jedis.setnx(lockKey, lockTimeDuration);

        if (result == 1) {
            return true;
        } else {
            if (checkIfLockTimeout(currentTime, lockKey)) {
                String preLockTimeDuration = jedis.getSet(lockKey, lockTimeDuration);
                if (currentTime > Long.valueOf(preLockTimeDuration)) {
                    return true;
                }
            }
            return false;
        }

    }

    /**
     * 判断加锁是否超时
     * @param currentTime 当前时间
     * @param lockKey 锁的名字
     * @return
     */
    private boolean checkIfLockTimeout(Long currentTime, String lockKey) {
        //如果当前时间超过锁的持续时间，则默认之前的锁已经失效，返回true
        if (currentTime > Long.valueOf(jedis.get(lockKey))) {
            return true;
        } else {
            return false;
        }
    }
}
