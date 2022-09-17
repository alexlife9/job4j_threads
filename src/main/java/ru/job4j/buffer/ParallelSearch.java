package ru.job4j.buffer;

import ru.job4j.thread.SimpleBlockingQueue;

/**
 * Обеспечить остановку потребителя
 *
 * Задание:
 * Разработать механизм остановки потребителя, когда производитель закончил свою работу.
 * Представим утилиту по поиску текста в файловой системе.
 * Одна нить ищет файлы с подходящим именем. Вторая нить берет эти файлы и читает.
 * Эта схема хорошо описывается шаблоном Producer-Consumer. Однако есть один момент.
 * Когда первая нить заканчивает свою работу, потребители переходят в режим wait.
 *
 * @author Alex_life
 * @version 1.0
 * @since 17.09.2022
 */
public class ParallelSearch {

    public static void main(String[] args) {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(10);
        final Thread consumer = new Thread(
                () -> {
                    while (true) {
                        System.out.println(queue.consumer());
                    }
                }
        );
        consumer.start();

        new Thread(
                () -> {
                    for (int index = 0; index != 10; index++) {
                        queue.producer(index);
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    consumer.interrupt();
                }
        ).start();
    }
}
