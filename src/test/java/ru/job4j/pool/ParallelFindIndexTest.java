package ru.job4j.pool;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * @author Alex_life
 * @version 1.0
 * @since 03.10.2022
 */
class ParallelFindIndexTest {
    @Test
    public void whenLengthLessTen() {
        int[] array = {1, 2, 9, 4, 5, 15, 7, 8};
        int rsl = ParallelFindIndex.findIndex(array, 9);
        assertThat(rsl).isEqualTo(2);
    }

}