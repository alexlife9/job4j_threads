package ru.job4j.thread;

/**
 * Управление нитью через wait
 *
 * Задание:
 * Разработать класс, который блокирует выполнение по условию счетчика.
 *
 * @author Alex_life
 * @version 2.0
 * @since 18.09.2022
 */
public class CountBarrier {
    private final Object monitor = this;

    /**
     * Переменная total содержит количество вызовов метода count().
     */
    private final int total;

    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    /**
     * Метод count изменяет состояние программы. Это значит, что внутри метода count нужно вызывать метод notifyAll.
     */
    public void count() {
        synchronized (monitor) {
            count++;
            monitor.notifyAll();
        }
    }

    /**
     * Нити, которые выполняют метод await, могут начать работу если поле count >= total.
     * Если оно не равно, то нужно перевести нить в состояние wait.
     * Здесь нужно использовать цикл while для проверки состояния, а не оператор if.
     */
    public void await() {
        synchronized (monitor) {
            while (count < total) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
