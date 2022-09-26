package ru.job4j.pool;

import ru.job4j.thread.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

/**
 * Реализация ThreadPool
 *
 * Пул - это хранилище для ресурсов, которые можно переиспользовать.
 * Клиент берет ресурс из пула, выполняет свою работу и возвращает обратно в пул.
 *
 * Задание:
 * 1. Инициализация пула должна быть по количеству ядер в системе.
 * Количество нитей всегда одинаковое и равно size. В каждую нить передается блокирующая очередь tasks.
 * В методе run мы должны получить задачу из очереди tasks.
 * tasks - это блокирующая очередь. Если в очереди нет элементов, то нить переводится в состояние Thread.State.WAITING.
 * Когда приходит новая задача, всем нитям в состоянии Thread.State.WAITING посылается сигнал проснуться и начать работу
 *
 * 2. Создать метод work(Runnable job) - этот метод должен добавлять задачи в блокирующую очередь tasks.
 *
 * 3. Создать метод shutdown() - этот метод завершит все запущенные задачи.
 *
 * @author Alex_life
 * @version 1.0
 * @since 26.09.2022
 */
public class ThreadPool {

    private final List<Thread> threads = new LinkedList<>();
    private final int size = Runtime.getRuntime().availableProcessors();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(size);

    /**
     * метод work добавляет задачи в очередь
     * size - это кол-во потоков, соответствующее кол-ву ядер в системе
     * для каждого ядра добавляем новый поток, в котором находится задача job
     * пока флаг isInterrupted не true - получаем задачу из очереди
     * tasks.producer(job) - заполняем очередь задачами
     *
     * @param job задача которую надо добавить
     */
    public void work(Runnable job) throws InterruptedException {
        for (int i = 0; i < size; i++) {
            threads.add(new Thread(
                    () -> {
                        try {
                            while (!Thread.currentThread().isInterrupted()) {
                                tasks.consumer().run();
                            }
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
            ));
        }
        tasks.producer(job);
    }

    /* завершаем все потоки */
    public void shutdown() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }
}
