package com.school.wz_growth.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.PostConstruct;

@Component
public class JedisUtil {

    private static final JedisUtil jedisUtil=new JedisUtil();

    @Qualifier("jedisPool")
    @Autowired
    public static JedisPool jedisPool;
    public static Jedis jedis;

    private JedisUtil(){
    }

    @Autowired
    public void setJedisPool(JedisPool jedisPool) {
    	JedisUtil.jedisPool = jedisPool;
    }

    public JedisPool getJedisPool() {
        return JedisUtil.jedisPool;
    }

    @PostConstruct
    public static JedisUtil getInstance() {
        return jedisUtil;
    }
    
}
