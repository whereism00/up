/**
 * Package containing interfaces and classes for the ring buffer.
 */
package org.example;

public interface RingBuffer<T> extends Iterable<T> {
    /**
     * Возвращает и удаляет элемент из начала очереди.
     *
     * @return Элемент или {@code null}, если очередь пуста
     */
    T poll();

    /**
     * Возвращает (но не удаляет) элемент из начала очереди.
     *
     * @return Элемент или {@code null}, если очередь пуста
     */
    T peek();

    /**
     * Добавляет элемент в конец очереди.
     * Затирает начало очереди в случае, если она заполнена.
     *
     * @param item новый элемент
     */
    void add(T item);

    /**
     * Возвращает размер коллекции.
     *
     * @return размер
     */
    int getSize();

}
