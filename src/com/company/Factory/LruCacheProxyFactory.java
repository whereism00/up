package com.company.Factory;

import com.company.LruCache.LruCache;
import com.company.LruCache.LruCacheImpl;
import com.company.LruCache.LruCacheProxy;

public class LruCacheProxyFactory<K, V> extends LruCacheFactory<K, V> {
    @Override
    public LruCache<K, V> createLruCache(int limit) {
        return new LruCacheProxy<>(new LruCacheImpl<>(limit));
    }
}
