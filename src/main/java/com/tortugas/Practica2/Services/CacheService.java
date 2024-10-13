package com.tortugas.Practica2.Services;
import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.stereotype.Service;

@Service
public class CacheService {

    @Autowired
    private CacheManager cacheManager;
    @Autowired
    public CacheService(CacheManager manager){
        cacheManager = manager;
    }
    // Method to add a key-value pair to the cache
    public void addToCache(String key, String value) {
        Cache<Object, Object> cache = getCaffeineCache();
        cache.put(key, value);
    }

    // Method to check if a key exists in the cache
    public boolean isKeyInCache(String key) {
        Cache<Object, Object> cache = getCaffeineCache();
        return cache.getIfPresent(key) != null;
    }

    // Method to get value from the cache by key
    public String getFromCache(String key) {
        Cache<Object, Object> cache = getCaffeineCache();
        Object value = cache.getIfPresent(key);
        return value != null ? value.toString() : null;
    }

    // Helper method to get the Caffeine Cache instance
    private Cache<Object, Object> getCaffeineCache() {
        CaffeineCache caffeineCache = (CaffeineCache) cacheManager.getCache("myCache");
        return caffeineCache.getNativeCache();
    }
}
