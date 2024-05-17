package org.example;

import org.example.impl.LruCacheImpl;
import org.junit.jupiter.api.Test;

public class MainTest {
    @Test
    public void whenAddElementOverTheLimit_expectRemovedEarliestElement() {

        int lruCacheLimit = 2;
        LruCache<Integer, Integer> lruCache = new LruCacheProxy<>(new LruCacheImpl<>(lruCacheLimit));
        lruCache.set(1, 1);
        lruCache.set(2, 2);
        lruCache.set(3, 3);

        assert lruCache.get(1) == null;
        assert lruCache.get(3) == 3;
    }

    @Test
    public void whenAddElementByIndex_expectReplacedElement() {
        int lruCacheLimit = 2;
        LruCache<Integer, Integer> lruCache = new LruCacheProxy<>(new LruCacheImpl<>(lruCacheLimit));
        lruCache.set(1, 1);
        lruCache.set(2, 2);
        lruCache.set(2, 3);

        assert lruCache.get(2) == 3;
    }
}
