package com.qf.v16userservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.jca.cci.connection.ConnectionFactoryUtils;

/**
 * @author xiaoxinmin
 * @Date 2019/8/15
 */
@Configuration
public class RedisConfig {

    @Bean(name = "redisTemplate4String")
    public RedisTemplate<String,Object> initRedisTemplate(RedisConnectionFactory factory){
        RedisTemplate<String,Object> redisTemplate=new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }


}
