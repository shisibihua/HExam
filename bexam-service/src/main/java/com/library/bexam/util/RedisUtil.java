package com.library.bexam.util;

import com.honghe.cache.RedisCache;
import jodd.util.StringUtil;
import redis.clients.jedis.Jedis;

public final class RedisUtil {
    //key值超时时长
    public static final Integer KEYTIMEOUT = 86400;

    private RedisUtil() {

    }
    /**
     * 设置 过期时间
     *
     * @param key
     * @param seconds 以秒为单位
     * @param value
     */
    public static Boolean setString(String key, int seconds, String value) {
        boolean reValue = false;
        Jedis jedis = null;
        try {
            jedis = RedisCache.INSTANCE.getJedis();
            if (value != null && !StringUtil.isEmpty(key)) {
                reValue = jedis.setex(key, seconds, value).equalsIgnoreCase("OK");
            }
        } catch (Exception e) {
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return reValue;
    }

    /**
     * 判断redis中是否包含该key
     *
     * @param key 需要判断的
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static Boolean exists(String key) {
        Jedis jedis = null;
        boolean reValue = false;
        try {
            jedis = RedisCache.INSTANCE.getJedis();
            reValue = jedis.exists(key);
        } catch (Exception e) {

        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return reValue;
    }

    /**
     * 获取String值
     *
     * @param key
     * @return value
     */
    public static String getString(String key) {
        String reValue = "";
        Jedis jedis = null;
        try {
            jedis = RedisCache.INSTANCE.getJedis();
            if (jedis.exists(key)) {
                reValue = jedis.get(key);
            }
        } catch (Exception e) {
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return reValue;
    }

    /**
     * 更新key的超时时间  大于一代表设置成功 redis 2.1.3版本后支持
     *
     * @param key
     * @param
     */
    public static Long updateTimeOut(String key, int seconds) {
        long reValue = 0;
        Jedis jedis = null;
        if (key != null) {
            try {
                jedis = RedisCache.INSTANCE.getJedis();
                if (jedis.exists(key)) {
                    reValue = jedis.expire(key, seconds);
                }
            } catch (Exception e) {

            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
        }
        return reValue;
    }
}
