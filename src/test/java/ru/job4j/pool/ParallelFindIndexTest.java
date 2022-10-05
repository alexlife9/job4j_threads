package ru.job4j.pool;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * ForkJoinPool
 *
 * @author Alex_life
 * @version 2.0
 * @since 04.10.2022
 */
class ParallelFindIndexTest {
    @Test
    public void whenLengthLessTen() {
        Integer[] array = {1, 2, 9, 4, 5, 15, 7, 8};
        int rsl = ParallelFindIndex.findIndex(array, 9);
        assertThat(rsl).isEqualTo(2);
    }

    @Test
    public void whenLength100() {
        Integer[] array = new Integer[100];
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        int rsl = ParallelFindIndex.findIndex(array, 40);
        assertThat(rsl).isEqualTo(40);
    }

}