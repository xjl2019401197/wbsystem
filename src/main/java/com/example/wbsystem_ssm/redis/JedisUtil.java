package com.example.wbsystem_ssm.redis;

import redis.clients.jedis.Jedis;

public class JedisUtil {
    static Jedis jedis;
    public static Jedis getJedisCon(){
        jedis= new Jedis("39.98.69.123", 6379);
        jedis.auth("xjl2550908862xjl011025.");
        return jedis;
    }

    public static void close(){
        jedis.close();
    }
}
