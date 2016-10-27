package lpp.tools.cache.support;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import lpp.tools.cache.Cache;
import lpp.tools.cache.CacheFactory;
import lpp.tools.cache.URL;


/**
 * Author:lipanpan</br>
 * Date:2016年10月27日</br>
 * Description:</br>
 * Copyright (c) 2016 code</br> 
 */
public abstract class AbstractCacheFactory implements CacheFactory {

    private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<String, Cache>();

    public Cache getCache(URL url) {
        String key = url.toString();
        Cache cache = caches.get(key);
        if (cache == null) {
            caches.put(key, createCache(url));
            cache = caches.get(key);
        }
        return cache;
    }

    protected abstract Cache createCache(URL url);

}
