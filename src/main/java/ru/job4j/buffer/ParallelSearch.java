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
 * Класс Thread содержит в себе скрытое булево поле, которое называется флагом прерывания.
 * Установить этот флаг можно вызвав метод interrupt() потока.
 * Проверить же, установлен ли этот флаг, можно двумя способами.
 * Первый способ — вызвать метод bool isInterrupted() объекта потока,
 * второй — вызвать статический метод bool Thread.interrupted().
 *
 * Первый метод возвращает состояние флага прерывания и оставляет этот флаг нетронутым.
 * Второй метод возвращает состояние флага и сбрасывает его.
 *
 * Thread.interrupted() — статический метод класса Thread, и его вызов возвращает
 * значение флага прерывания того потока, из которого он был вызван.
 * Поэтому этот метод вызывается только изнутри потока и позволяет потоку проверить своё состояние прерывания.
 *
 * @author Alex_life
 * @version 3.0
 * @since 18.09.2022
 */
public class ParallelSearch {
    public static void main(String[] args) {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(10);
        final Thread consumer = new Thread(
                () -> {
                    while (!Thread.currentThread().isInterrupted()) {
                        try {
                            System.out.println(queue.consumer());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        new Thread(
                () -> {
                    for (int index = 0; index != 50; index++) {
                        try {
                            queue.producer(index);
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
