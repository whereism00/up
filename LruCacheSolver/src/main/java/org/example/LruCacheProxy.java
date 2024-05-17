package org.example;

import org.example.impl.RingBufferImpl;

/**
 * Proxy for the LRU Cache.
 *
 * @param <K> the type of keys maintained by this cache
 * @param <V> the type of mapped values
 */
public class LruCacheProxy<K, V> implements LruCache<K, V> {
    /**
     * Lru cache.
     */
    private final LruCache<K, V> lruCache;
    /** Ring buffer. */
    private final RingBuffer<K> ringBuffer;
    /**
     * Buffer capacity.
     */
    private static final int BUFFER_CAPACITY = 10;

    /**
     * Constructor to create an LRU cache Proxy.
     *
     * @param newLruCache the lru cache
     */
    public LruCacheProxy(final LruCache<K, V> newLruCache) {
        this.lruCache = newLruCache;
        ringBuffer = new RingBufferImpl<K>(BUFFER_CAPACITY);
    }

    /**
     * Retrieves an item from the cache.
     *
     * @param key the key of the item to retrieve
     * @return the value associated with the key, or null if not found
     */
    @Override
    public final V get(final K key) {
        long startTime = System.nanoTime();
        ringBuffer.add(key);
        outputKeyHistory();
        V value = lruCache.get(key);
        long endTime = System.nanoTime();
        System.out.printf("Время обработки: %d\n",
                endTime - startTime);
        return value;
    }

    /**
     * Prints history of last keys.
     *
     */
    private void outputKeyHistory() {
        System.out.println("\n--------- 10 последних ключей");
        for (K key : ringBuffer) {
            System.out.println(key.toString());
        }
        System.out.println("---------------------------------\n");
    }

    /**
     * Adds an item to the cache.
     *
     * @param key the key to store the item
     * @param value the value to be stored
     */
    @Override
    public final void set(final K key, final V value) {
        long startTime = System.nanoTime();

        lruCache.set(key, value);
        ringBuffer.add(key);
        V removableItem = lruCache.getRemovable();
        if (removableItem != null) {
            System.out.printf("Удаляемый элемент: %s\n",
                    removableItem);
        }

        long endTime = System.nanoTime();
        System.out.printf("Время обработки: %d\n",
                endTime - startTime);
    }

    /**
     * Gets the size of the cache.
     *
     * @return the size
     */
    @Override
    public final int getSize() {
        return lruCache.getSize();
    }

    /**
     * Gets the limit of the cache.
     *
     * @return the limit
     */
    @Override
    public final int getLimit() {
        return lruCache.getLimit();
    }

    /**
     * Gets the removable element of the cache.
     *
     * @return the removable element
     */
    @Override
    public final V getRemovable() {
        return lruCache.getRemovable();
    }
}
