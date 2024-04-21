/**
 * Package containing interfaces and classes for the main.
 */
package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.impl.LruCacheImpl;

public class Main {
    private static final Logger log = LogManager.getLogger(Main.class);

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
        log.info("The start");
        int lruCacheLimit = 2;
        LruCache<Integer, Integer> lruCache = new LruCacheProxy<>(new LruCacheImpl<>(lruCacheLimit));
        lruCache.set(1, 1);

        log.info("The end");
    }
}
