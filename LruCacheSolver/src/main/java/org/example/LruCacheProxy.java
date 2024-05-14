/**
 * Package containing interfaces and classes for the lru cache.
 */
package org.example;

import org.example.impl.RingBufferImpl;

public class LruCacheProxy<K, V> implements LruCache<K, V> {
    private final LruCache<K, V> lruCache;
    private final RingBuffer<K> ringBuffer;

    public LruCacheProxy(LruCache<K, V> lruCache) {
        int bufferCapacity = 10;
        this.lruCache = lruCache;
        ringBuffer = new RingBufferImpl<>(bufferCapacity);
    }

    @Override
    public V get(K key) {
        long startTime = System.nanoTime();
        ringBuffer.add(key);
        outputKeyHistory();
        V value = lruCache.get(key);
        long endTime = System.nanoTime();
        System.out.printf("Время обработки операции: %d\n", endTime - startTime);
        return value;
    }

    private void outputKeyHistory() {
        System.out.println("\n--------- 10 последних ключей ---------");
        for (K key : ringBuffer) {
            System.out.println(key.toString());
        }
        System.out.println("---------------------------------\n");
    }

    @Override
    public void set(K key, V value) {
        long startTime = System.nanoTime();

        lruCache.set(key, value);
        ringBuffer.add(key);
        V removableItem = lruCache.getRemovable();
        if (removableItem != null) {
            System.out.printf("Удаляемый элемент: %s\n", removableItem);
        }

        long endTime = System.nanoTime();
        System.out.printf("Время обработки операции: %d\n", endTime - startTime);
    }

    @Override
    public int getSize() {
        return lruCache.getSize();
    }

    @Override
    public int getLimit() {
        return lruCache.getLimit();
    }

    @Override
    public V getRemovable() {
        return lruCache.getRemovable();
    }
}