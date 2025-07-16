package com.somshine.taskmanager.service;

import org.springframework.stereotype.Service;

import javax.cache.CacheManager;
import javax.cache.Cache;
import java.util.Iterator;

@Service
public class CacheInspectorService {

    private final CacheManager cacheManager;

    public CacheInspectorService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void listCaches() {
        System.out.println("Registered cache names:");
        Iterator<String> cacheNames = cacheManager.getCacheNames().iterator();
        while (cacheNames.hasNext()) {
            String name = cacheNames.next();
            Cache<?, ?> cache = cacheManager.getCache(name);
            System.out.println(" → " + name + " (entries may not be iterable depending on provider)");
        }
    }
}
