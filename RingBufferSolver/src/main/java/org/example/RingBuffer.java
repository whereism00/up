/**
 * Package containing interfaces and classes for the ring buffer.
 */
package org.example;

public interface RingBuffer<T> extends Iterable<T> {
    /**
     * Polls an element from the ring buffer.
     *
     * @return the polled element
     */
    T poll();

    /**
     * Peeks an element from the ring buffer.
     *
     * @return the peeked element
     */
    T peek();

    /**
     * Adds an item to the ring buffer.
     *
     * @param item the item to be added
     */
    void add(T item);

    /**
     * Gets the size of the ring buffer.
     *
     * @return the size of the ring buffer
     */
    int getSize();

}
