package com.qf.springbootredis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author xiaoxinmin
 * @Date 2019/8/14
 */
@Configuration
public class RedisConfig {


    @Bean(name = "stringRedisTemplate1")
    public RedisTemplate<String,String> initRedisTemplate1(RedisConnectionFactory factory){
        RedisTemplate<String,String> redisTemplate=new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    @Bean(name = "objectRedisTemplate1")
    public RedisTemplate<String,Object> initRedisTemplate2(RedisConnectionFactory factory){
        RedisTemplate<String,Object> redisTemplate=new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }


}
