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
     * @param limit the maximum number of items in the cache
     */
    public LruCacheImpl(final int limit) {
        queue = new Queue<>(limit);
        current = 0;
        this.limit = limit;
    }

    /**
     * Retrieves an item from the cache.
     *
     * @param key the key of the item to retrieve
     * @return the value associated with the key, or null if not found
     */
    @Override
    public V get(final K key) {
        current++;
        Node<V, K> tmp = queue.searchElem(key);
        if (tmp == null) {
            return null;
        } else {
            return tmp.getValue();
        }
    }

    /**
     * Adds an item to the cache.
     *
     * @param key the key to store the item
     * @param value the value to be stored
     */
    @Override
    public void set(final K key, final V value) {
        tmp = queue.addElem(value, key, current);
        current++;
    }

    /**
     * Gets the size of the cache.
     *
     * @return the size
     */
    @Override
    public int getSize() {
        return queue.getCount();
    }

    /**
     * Gets the limit of the cache.
     *
     * @return the limit
     */
    @Override
    public int getLimit() {
        return limit;
    }

    /**
     * Gets the removable element of the cache.
     *
     * @return the removable element
     */
    public V getRemovable() {
        return tmp;
    }

    /**
     * Prints the cache.
     */
    public String print() {
        return queue.print();
    }
}

class Node<V, K> {
    private V value;
    private int priority; //использование
    private K key;
    Node<V, K> next;

    public Node() {
        next = null;
    }

    public Node(V value, K key, int priority) {
        this.key = key;
        this.value = value;
        this.priority = priority;
        this.next = null;
    }

    public V getValue() {
        return value;
    }

    public K getKey() {
        return key;
    }

    public int getPriority() {
        return priority;
    }

    public Node<V, K> getNext() {
        return next;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setKey(K key) {
        this.key = key;
    }

    void setNext(Node<V, K> next) {
        this.next = next;
    }
}

class Queue<V, K> {
    private int count;
    private Node<V, K> start; // начало очереди
    private final int limit;

    public Queue(int limit) {
        this.limit = limit;
        count = 0;
        start = null;
    }

    public int getCount() {
        return count;
    }

    public V addElem(V value, K key, int priority) {
        V result = null;
        if (start == null) {
            start = new Node<>(value, key, priority);
        } else {
            if (start.getKey().equals((key))) {
                start.setPriority(getHighPriority() + 1);
                start.setValue(value);
                return null;
            }
            Node<V, K> tmp = start;
            while (tmp.getNext() != null) {
                if (tmp.getNext().getKey().equals(key)) {
                    tmp.getNext().setPriority(getHighPriority() + 1);
                    tmp.getNext().setValue(value);
                    return null;
                }
                tmp = tmp.getNext();
            }
            if (count == limit) {
                result = deleteLowPriority();
            }
            tmp.setNext(new Node<>(value, key, priority));
        }
        count++;
        return result;
    }

    public Node<V, K> getLowPriority() {
        if (start == null)
            return null;
        Node<V, K> tmp = start;
        Node<V, K> entry = start.getNext();
        while (entry != null) {
            if (entry.getPriority() < tmp.getPriority()) {
                tmp = entry;
            }
            entry = entry.getNext();
        }
        return tmp;
    }

    public V deleteLowPriority() {
        Node<V, K> tmp = getLowPriority();
        if (start == tmp) {
            start = start.getNext();
        } else {
            Node<V, K> entry = start;
            while (entry.getNext() != tmp)
                entry = entry.getNext();
            Node<V, K> t = entry.getNext();
            entry.setNext((t.getNext()));
        }
        count--;
        return tmp.getValue();
    }

    public int getHighPriority() {
        if (start == null)
            return 0;
        Node<V, K> tmp = start;
        Node<V, K> entry = start.getNext();
        while (entry != null) {
            if (entry.getPriority() > tmp.getPriority()) {
                tmp = entry;
            }
            entry = entry.getNext();
        }
        return tmp.getPriority();
    }

    public Node<V, K> searchElem(K key) {
        Node<V, K> tmp = start;
        while (tmp != null) {
            if (tmp.getKey().equals(key)) {
                tmp.setPriority(getHighPriority() + 1);
                return tmp;
            }
            tmp = tmp.getNext();
        }
        return null;
    }

    public String print() {
        StringBuilder res = new StringBuilder();
        Node<V, K> tmp = start;
        while (tmp != null) {
            res.append("Ключ: ").append(tmp.getKey()).append(" Значение: ").append(tmp.getValue()).append(" Pr: ").append(tmp.getPriority()).append("\n");
            tmp = tmp.getNext();
        }
        return res.toString();
    }
}
