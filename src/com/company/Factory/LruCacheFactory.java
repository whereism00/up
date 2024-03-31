package com.company.Factory;

import com.company.LruCache.LruCache;

public abstract class LruCacheFactory<K, V> {
    public abstract LruCache<K, V> createLruCache(int limit);
}
