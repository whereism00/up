/**
 * Package containing implementation of the lru cache.
 */
package org.example.impl;

import org.example.LruCache;

/**
 * Implementation of the LRU Cache.
 *
 * @param <K> the type of keys maintained by this cache
 * @param <V> the type of mapped values
 */
public class LruCacheImpl<K, V> implements LruCache<K, V> {
    /**
     * Lru cache.
     */
    private final Queue<V, K> queue;
    /** Current element. */
    private int current;
    /** Maximum limit for the cache. */
    private final int limit;
    /** Temporary element. */
    private V tmp = null;

    /**
     * Constructor to create an LRU cache with a given limit.
     *
     * @param newLimit the maximum number of items in the cache
     */
    public LruCacheImpl(final int newLimit) {
        queue = new Queue<V, K>(newLimit);
        current = 0;
        this.limit = newLimit;
    }

    /**
     * Retrieves an item from the cache.
     *
     * @param key the key of the item to retrieve
     * @return the value associated with the key, or null if not found
     */
    @Override
    public final V get(final K key) {
        current++;
        Node<V, K> newTmp = queue.searchElem(key);
        if (newTmp == null) {
            return null;
        } else {
            return newTmp.getValue();
        }
    }

    /**
     * Adds an item to the cache.
     *
     * @param key the key to store the item
     * @param value the value to be stored
     */
    @Override
    public final void set(final K key, final V value) {
        tmp = queue.addElem(value, key, current);
        current++;
    }

    /**
     * Gets the size of the cache.
     *
     * @return the size
     */
    @Override
    public final int getSize() {
        return queue.getCount();
    }

    /**
     * Gets the limit of the cache.
     *
     * @return the limit
     */
    @Override
    public final int getLimit() {
        return limit;
    }

    /**
     * Gets the removable element of the cache.
     *
     * @return the removable element
     */
    public final V getRemovable() {
        return tmp;
    }

    /**
     * Prints the cache.
     *
     * @return the printed cache
     *
     */
    public final String print() {
        return queue.print();
    }
}

/**
 * Class for elenemt of the LRU Cache.
 *
 * @param <K> the type of keys maintained by this cache
 * @param <V> the type of mapped values
 */
class Node<V, K> {
    /** Value. */
    private V value;
    /**
     * Usage.
     */
    private int priority;
    /** Key. */
    private K key;
    /**
     * Next element.
     */
    private Node<V, K> next;

    /**
     * Constructor to create an element of the cache.
     *
     * @param newValue    the value to set
     * @param newKey      the key for the value
     * @param newPriority the priority of the cache item
     */
    public Node(final V newValue, final K newKey, final int newPriority) {
        this.key = newKey;
        this.value = newValue;
        this.priority = newPriority;
        this.next = null;
    }

    /**
     * Returns value.
     *
     * @return value
     */
    public V getValue() {
        return value;
    }

    /**
     * Returns key.
     *
     * @return key
     */
    public K getKey() {
        return key;
    }

    /**
     * Returns usage.
     *
     * @return usage
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Returns next element.
     *
     * @return next element
     */
    public Node<V, K> getNext() {
        return next;
    }

    /**
     * Sets value.
     *
     * @param newValue value
     */
    public void setValue(final V newValue) {
        this.value = newValue;
    }

    /**
     * Sets usage.
     *
     * @param newPriority priority
     */
    public void setPriority(final int newPriority) {
        this.priority = newPriority;
    }

    /**
     * Sets key.
     *
     * @param newKey key
     */
    public void setKey(final K newKey) {
        this.key = newKey;
    }

    /**
     * Sets next element.
     *
     * @param newNext next element
     */
    void setNext(final Node<V, K> newNext) {
        this.next = newNext;
    }
}

/**
 * Basic structure for LRU cache.
 *
 * @param <K> the type of keys maintained by this cache
 * @param <V> the type of mapped values
 */
class Queue<V, K> {
    /** Count of elements. */
    private int count;
    /**
     * Start element.
     */
    private Node<V, K> start;
    /**
     * Limit.
     */
    private final int limit;

    /**
     * Constructor to create an LRU cache with a given limit.
     *
     * @param newLimit the maximum number of items in the cache
     */
    public Queue(final int newLimit) {
        this.limit = newLimit;
        count = 0;
        start = null;
    }

    /**
     * Returns number of elements.
     *
     * @return number of elements
     */
    public int getCount() {
        return count;
    }

    /**
     * Adds element to queue.
     *
     * @param value    value
     * @param key      key
     * @param priority priority
     * @return value of added element
     */
    public V addElem(final V value, final K key, final int priority) {
        V result = null;
        if (start == null) {
            start = new Node<V, K>(value, key, priority);
        } else {
            if (start.getKey().equals((key))) {
                start.setPriority(getHighPriority() + 1);
                start.setValue(value);
                return null;
            }
            Node<V, K> newTmp = start;
            while (newTmp.getNext() != null) {
                if (newTmp.getNext().getKey().equals(key)) {
                    newTmp.getNext().setPriority(getHighPriority() + 1);
                    newTmp.getNext().setValue(value);
                    return null;
                }
                newTmp = newTmp.getNext();
            }
            if (count == limit) {
                result = deleteLowPriority();
            }
            newTmp.setNext(new Node<V, K>(value, key, priority));
        }
        count++;
        return result;
    }

    /**
     * Returns element with the lowest usage.
     *
     * @return element
     */
    public Node<V, K> getLowPriority() {
        if (start == null) {
            return null;
        }
        Node<V, K> newTmp = start;
        Node<V, K> entry = start.getNext();
        while (entry != null) {
            if (entry.getPriority() < newTmp.getPriority()) {
                newTmp = entry;
            }
            entry = entry.getNext();
        }
        return newTmp;
    }

    /**
     * Deletes element with the lowest usage.
     *
     * @return value of deleted element
     */
    public V deleteLowPriority() {
        Node<V, K> newTmp = getLowPriority();
        if (start == newTmp) {
            start = start.getNext();
        } else {
            Node<V, K> entry = start;
            while (entry.getNext() != newTmp) {
                entry = entry.getNext();
            }
            Node<V, K> t = entry.getNext();
            entry.setNext((t.getNext()));
        }
        count--;
        return newTmp.getValue();
    }

    /**
     * Returns element with the highest usage.
     *
     * @return element
     */
    public int getHighPriority() {
        if (start == null) {
            return 0;
        }
        Node<V, K> newTmp = start;
        Node<V, K> entry = start.getNext();
        while (entry != null) {
            if (entry.getPriority() > newTmp.getPriority()) {
                newTmp = entry;
            }
            entry = entry.getNext();
        }
        return newTmp.getPriority();
    }

    /**
     * Returns element by given key.
     *
     * @param key key for search
     * @return element
     */
    public Node<V, K> searchElem(final K key) {
        Node<V, K> newTmp = start;
        while (newTmp != null) {
            if (newTmp.getKey().equals(key)) {
                newTmp.setPriority(getHighPriority() + 1);
                return newTmp;
            }
            newTmp = newTmp.getNext();
        }
        return null;
    }

    /**
     * Builds string with elements of queue.
     *
     * @return string
     */
    public String print() {
        StringBuilder res = new StringBuilder();
        Node<V, K> newTmp = start;
        while (newTmp != null) {
            res
                    .append("Ключ: ")
                    .append(newTmp.getKey())
                    .append(" Значение: ")
                    .append(newTmp.getValue()).append(" Pr: ")
                    .append(newTmp.getPriority()).append("\n");
            newTmp = newTmp.getNext();
        }
        return res.toString();
    }
}
