package ru.job4j.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * ForkJoinPool
 *
 * Задание:
 * Реализовать параллельный поиск индекса в массиве объектов.
 * В целях оптимизации, если размер массива не больше 10, использовать обычный линейный поиск.
 * Метод поиска должен быть обобщенным.
 *
 * @author Alex_life
 * @version 2.0
 * @since 04.10.2022
 */
public class ParallelFindIndex<T> extends RecursiveTask<Integer> {

    private final T[] array;
    private final T value;
    private final int from;
    private final int to;

    public ParallelFindIndex(T[] array, T value, int from, int to) {
        this.array = array;
        this.value = value;
        this.from = from;
        this.to = to;
    }

    public int findLine() {
        int rsl = -1;
        for (int i = from; i <= to; i++) {
            if (array[i].equals(value)) {
                rsl = i;
                break;
            }
        }
        return rsl;
    }

    @Override
    protected Integer compute() {
        if (to - from < 10) {
            findLine();
        }
        int mid = (from + to) / 2;
        ParallelFindIndex<T> leftSort = new ParallelFindIndex<>(array, value, from, mid);
        var rightSort = new ParallelFindIndex<>(array, value, mid +1, to);
        leftSort.fork();
        rightSort.fork();
        return Math.max(leftSort.join(), rightSort.join());
    }

    public static <T> int findIndex(T[] array, T value) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelFindIndex<>(array, value, 0, array.length - 1));
    }
}
