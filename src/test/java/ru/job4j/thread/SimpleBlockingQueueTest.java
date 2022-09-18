package ru.job4j.thread;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.assertj.core.api.Assertions.*;

/**
 * Полноценный Junit тест для блокирующей очереди
 *
 * В тесте должны быть две нити. Надо обеспечить, что бы главная нить ждала выполнение других нитей.
 * Дополнительно нам нужно обеспечивать параллельное выполнение потребителя и производителя.
 * Так же сложность возникает в блокировке потребителя или производителя, если очередь пустая или переполненная.
 * Если она пустая нужно произвести остановку нити и проверить результат.
 * Если очередь переполненная нужно приостановить производителя и дать возможность просчитать потребителю.
 *
 *
 *
 * @author Alex_life
 * @version 4.0
 * @since 19.09.2022
 */
public class SimpleBlockingQueueTest {


    @Test
    public void whenSimpleRun() throws InterruptedException {
        CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        Thread producer = new Thread(() -> {
            for (int i = 90; i <= 100; i++) {
                try {
                    queue.producer(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        producer.start();

        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.consumer());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        consumer.start();

        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer).isEqualTo(Arrays.asList(90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100));

    }

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        /* buffer - нужен, чтобы собрать все данные в список и проверить их в конце выполнения теста */
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(10);
        Thread producer = new Thread(
                () -> {
                    for (int i = 0; i < 5; i++) {
                        try {
                            queue.producer(i);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        producer.start();

        /* в цикле while проверяем, что очередь пустая или нить выключили.
        Если производитель закончил свою работу и сразу подаст сигнал об отключении потребителя,
        то мы не сможем прочитать все данные.
        С другой стороны, если мы успели прочитать все данные и находимся в режиме wait, то пришедший сигнал
        запустит нить и проверит состояние очереди и завершит цикл. Потребитель закончит свою работу. */
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.consumer());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        consumer.start();

        /* Сначала дожидаемся завершения работы производителя.*/
        producer.join();

        /* Далее посылаем сигнал, что потребителю можно остановиться. */
        consumer.interrupt();

        /* Ждем пока потребитель прочитает все данные и завершит свою работу. */
        consumer.join();
        assertThat(buffer).isEqualTo(Arrays.asList(0, 1, 2, 3, 4));
    }
}