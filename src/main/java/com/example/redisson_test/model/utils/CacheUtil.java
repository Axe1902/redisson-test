package com.example.redisson_test.model.utils;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching
public class CacheUtil
{
    @Bean
    public CacheManager cacheManager(RedissonClient redissonClient)
    {
        Map<String, CacheConfig> config = new HashMap<>();

        CacheConfig cacheConfig = new CacheConfig(
                30 * 60 * 1000, // 30 минут TTL
                15 * 60 * 1000 // 15 минут maxIdleTime
        );
        cacheConfig.setMaxSize(1000);

        config.put("author", cacheConfig);

        return new RedissonSpringCacheManager(redissonClient, config);
    }

    @Bean
    public RedissonClient redissonClient()
    {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://localhost:5434");

        return Redisson.create(config);
    }
}
