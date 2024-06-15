package com.pandaer.framework.cache.redis.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

//    @ConditionalOnBean(RedisConnectionFactory.class)
    @Bean
    public RedisTemplate<String,Object> jsonRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        // 设置key序列化器
        template.setKeySerializer(new StringRedisSerializer());
        // 设置value序列化器
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        // 设置hash key序列化器
        template.setHashKeySerializer(new StringRedisSerializer());
        // 设置hash value序列化器
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }
}
