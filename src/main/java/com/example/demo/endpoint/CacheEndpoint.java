package com.example.demo.endpoint;

import com.example.demo.exceptions.DemoException;
import com.example.demo.service.CacheService;
import org.springframework.cache.Cache;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/admin/cache")
public class CacheEndpoint {

    private final CacheService cacheService;

    public CacheEndpoint(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @GetMapping
    public List<Cache> getAllCaches() {
        return cacheService.findAll();
    }

    @GetMapping("/{name}")
    public Cache getCacheByName(@PathVariable("name") String name) throws DemoException {
        return cacheService.findByName(name);
    }

    @DeleteMapping("/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCache(@PathVariable("name") String name) throws DemoException {
        cacheService.delete(name);
    }
}