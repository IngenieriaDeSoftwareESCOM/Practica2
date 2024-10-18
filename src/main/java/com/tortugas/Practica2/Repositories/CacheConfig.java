package com.tortugas.Practica2.Repositories;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration // Indicates that this class provides Spring configuration
@EnableCaching // Enables Spring's annotation-driven cache management capability
public class CacheConfig {

    // Bean definition for CaffeineCacheManager
    @Bean
    public CaffeineCacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(); // Creating a Caffeine cache manager
        cacheManager.setCaffeine(Caffeine.newBuilder() // Configuring Caffeine cache settings
                .initialCapacity(100) // Initial size of the cache
                .maximumSize(500) // Maximum size of the cache
                .expireAfterWrite(10, TimeUnit.MINUTES)); // Expiration time after the last write
        return cacheManager; // Returning the configured cache manager
    }
}
