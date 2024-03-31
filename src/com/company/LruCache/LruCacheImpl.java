package com.company.LruCache;

public class LruCacheImpl<K, V> implements LruCache<K, V> {
    private final Queue<V, K> queue;
    private int current;
    private int limit;
    private V tmp = null;

    public LruCacheImpl(int limit) {
        queue = new Queue<>(limit);
        current = 0;
        this.limit = limit;
    }

    /**
     * Возвращает значение, соответствующее указанному ключу.
     * При этом элемент (пара ключ-значение) помечается
     * как последний использованный.
     *
     * @param key Ключ
     * @return Значение или {@code null},
     * если значение не найдено
     */
    @Override
    public V get(K key) {
        current++;
        Node<V, K> tmp = queue.SearchElem(key);
        if (tmp == null) return null;
        else return tmp.getValue();
    }

    /**
     * Добавляет элемент (пару ключ-значение) в коллекцию.
     * В случае, если элемент с таким ключом уже был
     * в коллекции, он заменяется.
     * При этом элемент помечается как последний использованный.
     * <p>
     * В случае, если до вставки размер коллекции был равен
     * максимальному, из нее удаляется элемент,
     * неиспользованный дольше всех.
     *
     * @param key   Ключ
     * @param value Значение
     */
    @Override
    public void set(K key, V value) {
        tmp = queue.addElem(value, key, current);
        current++;
    }

    /**
     * Возвращает текущий размер коллекции.
     */
    @Override
    public int getSize() {
        return queue.getCount();
    }

    /**
     * Возвращает максимальный размер коллекции.
     */
    @Override
    public int getLimit() {
        return limit;
    }

    /**
     * Возвращает удаляемое значение
     */
    public V getRemovable() {
        return tmp;
    }

    /**
     * печать коллекции
     **/
    public String print() {
        return queue.print();
    }
}

class Node<V, K> {
    private V value;
    private int priority; //использование
    private K key;
    Node next;

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

    public Node getNext() {
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

    void setNext(Node next) {
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
                result = DeleteLowPriority();
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

    public V DeleteLowPriority() {
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

    public Node<V, K> SearchElem(K key) {
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
        String res = "";
        Node<V, K> tmp = start;
        while (tmp != null) {
            res += "Ключ: " + tmp.getKey() + " Значение: " + tmp.getValue() + " Pr: " + tmp.getPriority() + "\n";
            tmp = tmp.getNext();
        }
        return res;
    }
}
