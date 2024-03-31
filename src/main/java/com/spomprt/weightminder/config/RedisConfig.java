package com.spomprt.weightminder.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Slf4j
@Configuration
public class RedisConfig {

    @Value("${cache.redis.host}")
    private String host;

    @Value("${cache.redis.port}")
    private int port;

    @Value("${cache.redis.user}")
    private String redisUser;

    @Value("${cache.redis.password}")
    private String redisPassword;

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(
            host,
            port
        );
        redisStandaloneConfiguration.setUsername(redisUser);
        redisStandaloneConfiguration.setPassword(redisPassword);
        log.info("Host: {}", host);
        log.info("Port: {}", port);
        log.info("User: {}", redisUser);
        log.info("Password: {}", redisPassword);
        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(
        JedisConnectionFactory jedisConnectionFactory
    ) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        return redisTemplate;
    }

}
