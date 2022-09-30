package ru.job4j.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ExecutorService рассылка почты
 *
 * В классе ThreadPool показано как в примитивном случае можно реализовать пул нитей.
 *
 * В JDK входит пакет concurrent в котором уже есть готовая реализация.
 * import java.util.concurrent.ExecutorService;
 * import java.util.concurrent.Executors;
 *
 * Пример реализации:
 * Создаем пул, добавляем в него две задачи через метод submit.
 *
 * @author Alex_life
 * @version 1.0
 * @since 30.09.2022
 */
public class PoolEx {
    public static void main(String[] args) {
        /* Создает пул нитей по количеству доступных ядер в процессоре */
        ExecutorService pool = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors()
        );

        /* метод submit добавляет задачу в пул и сразу ее выполняет */
        pool.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("Execute " + Thread.currentThread().getName());
            }
        });
        /* метод submit добавляет еще одну задачу в пул и сразу ее выполняет */
        pool.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("Execute " + Thread.currentThread().getName());
            }
        });

        /* Закрываем пул и ждем пока все задачи завершатся */
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Execute " + Thread.currentThread().getName());
    }

}
