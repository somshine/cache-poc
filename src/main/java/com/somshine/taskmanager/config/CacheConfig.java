package com.somshine.taskmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;

@Configuration
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        CachingProvider provider = Caching.getCachingProvider();
        return provider.getCacheManager();
    }
}
