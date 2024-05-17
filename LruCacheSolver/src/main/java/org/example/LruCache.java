package org.example;

/**
 * Коллекция, реализующая LRU кеш.
 *
 * @param <K> Тип ключей
 * @param <V> Тип значений
 */
public interface LruCache<K, V> {
    /**
     * Retrieves an item from the cache.
     *
     * @param key the key of the item to retrieve
     * @return the value associated with the key, or null if not found
     */
    V get(K key);

    /**
     * Adds an item to the cache.
     *
     * @param key the key to store the item
     * @param value the value to be stored
     */
    void set(K key, V value);

    /**
     * Gets the size of the cache.
     *
     * @return the size
     */
    int getSize();

    /**
     * Gets the limit of the cache.
     *
     * @return the limit
     */
    int getLimit();

    /**
     * Gets the removable element of the cache.
     *
     * @return the removable element
     */
    V getRemovable();
}
