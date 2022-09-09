package ru.job4j.concurrent;

/**
 * Прерывание нити
 *
 * @author Alex_life
 * @version 1.0
 * @since 09.09.2022
 */
public class ThreadStop {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(
                () -> {
                    int count = 0;
                    while (!Thread.currentThread().isInterrupted()) { /* проверяем состояние флага */
                        System.out.println(count++);
                    }
                }
        );
        thread.start();
        Thread.sleep(100);
        /* Планировщик выделяет разное время для каждой нити, поэтому флаг выставляется в произвольное время */
        thread.interrupt(); /* метод выставляет флаг прерывания, но никаких дополнительных действий не совершает */
    }
}
