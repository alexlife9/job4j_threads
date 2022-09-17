package ru.job4j.thread;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Блокирующая очередь, ограниченная по размеру
 *
 * @author Alex_life
 * @version 1.0
 * @since 17.09.2022
 */
public class SimpleBlockingQueueTest {

    @Test
    public void whenSimpleRun() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(100);
        Thread producer = new Thread(() -> {
            for (int i = 0; i <= 100; i++) {
                queue.producer(i);
            }
        });
        Thread consumer = new Thread(() -> {
            for (int i = 0; i <= 100; i++) {
                queue.consumer();
            }
        });
        producer.start();
        consumer.start();
    }
}