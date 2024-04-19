package org.example;

import org.apache.log4j.Logger;
import org.example.impl.LruCacheImpl;

public class Main {
    private static final Logger log = Logger.getLogger(Main.class);

    /*
    Реализовать фабрику (или фабричный метод), которая позволяет получить объекты org.example.LruCache
    с указанными типами параметризации. При этом возращаться должен экземпляр класса-прокси, который
    добавляет к обычному классу следующее поведение:
    • Каждый раз при достижении максимального размера кеша во время вызова метода set() удаляемый элемент
    (неиспользованный дольше всех) должен выводиться в консоль с соотвествующим сообщением.
    • При получении значения по ключу, т.е. при вызове get() , в консоль должна выводиться история
    последних 10 обращений к ключам, а именно - хронологическая последовательность ключей, к которым были
    обращения.
    • Кроме того, в сообщениях из предыдущих дву требований должно содержаться время
    (в миллисекундах или наносекундах), которое потребовалось обработку операции.
     */

    public static void main(String[] args) {
        LruCache<Integer, Integer> lruCache;
        int lruCacheLimit = 2;
        lruCache = new LruCacheProxy<>(new LruCacheImpl<>(lruCacheLimit));
        System.out.println("---Добавление 1");
        lruCache.set(1, 1);
        System.out.println("---Добавление 2");
        lruCache.set(2, 2);
        System.out.println("---Добавление 3 - удаление 1");
        lruCache.set(3, 3);
        System.out.println("---Добавление 3 - замена 3");
        lruCache.set(3, 4);

        System.out.println("Получение 1: " + lruCache.get(1));
        System.out.println("Получение 3: " + lruCache.get(3));
        System.out.println("Получение 5: " + lruCache.get(5));
        System.out.println("Получение 2: " + lruCache.get(2));
        System.out.println("Получение 10: " + lruCache.get(10));
        System.out.println("Получение 1: " + lruCache.get(1));
        System.out.println("Получение 1: " + lruCache.get(1));
        System.out.println("Получение 5: " + lruCache.get(5));
        System.out.println("Получение 25: " + lruCache.get(25));
        System.out.println("Получение 30: " + lruCache.get(30));
        System.out.println("Получение 3: " + lruCache.get(3));

        log.info("The end");
    }
}
