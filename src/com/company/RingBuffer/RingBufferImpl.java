package com.company.RingBuffer;

import java.util.Iterator;

public class RingBufferImpl<E> implements RingBuffer<E> {
    private static class Node<E> {
        E info;
        Node next = null;
    }

    private Node<E> start, end;
    private final int size;

    public RingBufferImpl(int s) {
        size = s;
        start = null;
        end = new Node<>();
    }

    public E poll() {
        if (getSize() == 0) {
            return null;
        }
        Node<E> result = start;
        start = start.next;
        return result.info;
    }

    public E peek() {
        if (getSize() == 0) {
            return null;
        }
        return start.info;
    }

    public void add(E item) {
        if (start == null) {
            end.info = item;
            start = end;
        } else {
            if (getSize() == size) {
                end.next = start;
                start = start.next;
            } else {
                end.next = new Node<E>();
            }
            end.next.info = item;
            end = end.next;
        }
    }

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
