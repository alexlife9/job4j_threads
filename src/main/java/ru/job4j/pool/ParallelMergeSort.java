package ru.job4j.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * ForkJoinPool
 *
 * смотри MergeSort
 *
 * RecursiveAction и RecursiveTask
 * Как было сказано, при работе с подобным пулом необходим рекурсивный алгоритм. При этом его нужно как-то прописать.
 * Для этого существуют два класса:
 * RecursiveAction - ничего не возвращает, аналогично Runnable,
 * RecursiveTask - возвращает тип, который мы указываем, аналогично Callable.
 *
 * Для реализации алгоритма необходимо написать свой класс, который расширяет один из этих классов и
 * имплементировать метод compute().
 * Теперь реализуем его с использованием пула. Т.к. нам нужно возвращать отсортированную часть массива,
 * то будем использовать RecursiveTask
 *
 * @author Alex_life
 * @version 1.0
 * @since 03.10.2022
 */
public class ParallelMergeSort extends RecursiveTask<int[]> {

    private final int[] array;
    private final int from;
    private final int to;

    public ParallelMergeSort(int[] array, int from, int to) {
        this.array = array;
        this.from = from;
        this.to = to;
    }

    /**
     * метод fork() организует асинхронное выполнение новой задачи и отправляет задачу в какой-либо поток,
     * но при этом не запускает её выполнение.
     * Для получения результата служит метод join().
     * метод join() ожидает завершения задачи и возвращает результат её выполнения,
     * но во время ожидания поток не блокируется, а может начать выполнение других задач.
     */
    @Override
    protected int[] compute() {
        if (from == to) {
            return new int[] {array[from]};
        }
        int mid = (from + to) / 2;
        /* создаем задачи для сортировки частей */
        ParallelMergeSort leftSort = new ParallelMergeSort(array, from, mid);
        ParallelMergeSort rightSort = new ParallelMergeSort(array, mid + 1, to);
        /* производим деление и оно будет происходить, пока в частях не останется по одному элементу */
        leftSort.fork();
        rightSort.fork();
        /* объединяем полученные результаты с помощью метода join */
        int[] left = leftSort.join();
        int[] right = rightSort.join();
        return MergeSort.merge(left, right);
    }

    public static int[] sort(int[] array) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        /* метод invoke(). Этот метод служит для запуска выполнения */
        return forkJoinPool.invoke(new ParallelMergeSort(array, 0, array.length - 1));
    }

}
