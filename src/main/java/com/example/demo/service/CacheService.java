package com.example.demo.service;

import com.example.demo.exceptions.DemoException;
import com.example.demo.exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CacheService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryService.class);

    private final CacheManager cacheManager;

    public CacheService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public List<Cache> findAll() {
        LOGGER.info("CacheService -> findAll -> Searched for all caches");
        return cacheManager.getCacheNames()
                .stream()
                .map(cacheManager::getCache)
                .collect(Collectors.toList());
    }

    public Cache findByName(String name) throws DemoException {
        Cache result = cacheManager.getCache(name);
        if (result == null) {
            LOGGER.error("CacheService -> findByName -> Cache not found for name = {}", name);
            throw new NotFoundException("Cache name not found");
        }
        LOGGER.info("CacheService -> findByName -> Searched for cache with name = {}", name);
        return result;
    }

    public void delete(String name) throws DemoException {
        Cache cacheToBeDeleted = cacheManager.getCache(name);
        if (cacheToBeDeleted == null) {
            LOGGER.error("CacheService -> delete -> Cache not found for name = {}", name);
            throw new NotFoundException("Cache name not found");
        }
        cacheToBeDeleted.clear();
        LOGGER.info("CacheService -> delete -> Deleted cache with name = {}", name);
    }
}