package ru.job4j.thread;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

/**
 * CAS - операции
 *
 * Задание:
 * Реализовать неблокирующий счетчик.
 *
 * @author Alex_life
 * @version 1.0
 * @since 19.09.2022
 */
@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>(0); /* по умолчанию значение 0 */

    public void increment() throws UnsupportedOperationException {
        Integer valueCount = 1;
        int newCount;
        Integer refCount;
        do {
            refCount = count.get();
            newCount = refCount + valueCount;
        } while (!count.compareAndSet(refCount, newCount));
    }

    public void incrementFive() throws UnsupportedOperationException {
        Integer valueCount = -5;
        int newCount;
        Integer refCount;
        do {
            refCount = count.get();
            newCount = refCount + valueCount;
        } while (!count.compareAndSet(refCount, newCount));
    }

    public int get() throws UnsupportedOperationException {
        return count.get();
    }
}
