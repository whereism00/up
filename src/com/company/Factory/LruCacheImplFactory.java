package com.company.Factory;

import com.company.LruCache.LruCache;
import com.company.LruCache.LruCacheImpl;

public class LruCacheImplFactory<K, V> extends LruCacheFactory<K, V> {
    @Override
    public LruCache<K, V> createLruCache(int limit) {
        return new LruCacheImpl<>(limit);
    }
}
