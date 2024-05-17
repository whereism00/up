/**
 * Package containing implementation of the ring buffer.
 */
package org.example.impl;

import org.example.RingBuffer;

import java.util.Iterator;

/**
 * Implementation of the RingBuffer.
 *
 * @param <E> the type of elements held in this buffer
 */
public final class RingBufferImpl<E> implements RingBuffer<E> {
    private static class Node<E> {
        /**
         * Some info.
         */
        private E info;
        /**
         * Next element.
         */
        private Node<E> next = null;
    }

    /**
     * Start element.
     */
    private Node<E> start;
    /**
     * End element.
     */
    private Node<E> end;
    /**
     * Size.
     */
    private final int size;

    /**
     * Constructor.
     *
     * @param s the size
     */
    public RingBufferImpl(final int s) {
        size = s;
        start = null;
        end = new Node<>();
    }

    /**
     * Polls an element from the ring buffer.
     *
     * @return the polled element
     */
    public E poll() {
        if (getSize() == 0) {
            return null;
        }
        Node<E> result = start;
        start = start.next;
        return result.info;
    }

    /**
     * Peeks an element from the ring buffer.
     *
     * @return the peeked element
     */
    public E peek() {
        if (getSize() == 0) {
            return null;
        }
        return start.info;
    }

    /**
     * Adds an item to the ring buffer.
     *
     * @param item the item to be added
     */
    public void add(final E item) {
        if (start == null) {
            end.info = item;
            start = end;
        } else {
            if (getSize() == size) {
                end.next = start;
                start = start.next;
            } else {
                end.next = new Node<>();
            }
            end.next.info = item;
            end = end.next;
        }
    }

    /**
     * Gets the size of the ring buffer.
     *
     * @return the size of the ring buffer
     */
    public int getSize() {
        if (start == null) {
            return 0;
        }
        int count = 0;
        for (Node<E> i = start; i != end; i = i.next) {
            count++;
        }
        return count + 1;
    }

    /**
     * Returns an iterator over elements of the ring buffer.
     *
     * @return an iterator
     */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            private Node<E> i = start;
            private int count = 0;

            @Override
            public boolean hasNext() {
                return count != getSize();
            }

            @Override
            public E next() {
                E item = i.info;
                i = i.next;
                count++;
                return item;
            }
        };
    }
}
