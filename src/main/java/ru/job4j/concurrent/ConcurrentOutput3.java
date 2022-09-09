package ru.job4j.concurrent;

/**
 *  Запуск трех нитей. Thread
 *
 * @author Alex_life
 * @version 1.0
 * @since 10.09.2022
 */
public class ConcurrentOutput3 {
    public static void main(String[] args) {
        Thread another = new Thread(
                () -> System.out.println(Thread.currentThread().getName()) /* показываем имя нити another */
        );
        another.start();

        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName()) /* показываем имя нити second */
        );
        second.start();

        System.out.println(Thread.currentThread().getName()); /* показываем имя потока main */
    }
}
