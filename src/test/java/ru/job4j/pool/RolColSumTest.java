package ru.job4j.pool;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.*;
import static ru.job4j.pool.RolColSum.sum;

/**
 * CompletableFuture
 *
 * @author Alex_life
 * @version 1.0
 * @since 05.10.2022
 */
public class RolColSumTest {
    @Test
    public void rowSums() {
        int[][] array = {{1, 2}, {3, 4}};
        RolColSum.Sums[] sums = sum(array);
        assertThat(sums[0].getRowSum()).isEqualTo(3); /* сумма первой строчки 1+2=3 */
    }

    @Test
    public void colSums() {
        int[][] array = {{1, 2}, {3, 4}};
        RolColSum.Sums[] sums = sum(array);
        assertThat(sums[1].getColSum()).isEqualTo(6); /* сумма второго столбца 2+4=6 */
    }

    @Test
    public void whenAsyncSum() throws ExecutionException, InterruptedException {
        int[][] array = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        RolColSum.Sums[] sums = RolColSum.asyncSum(array);
        assertThat(sums[0].getRowSum()).isEqualTo(6); /* сумма первой строчки 1+2+3=6 */
        assertThat(sums[0].getColSum()).isEqualTo(12); /* сумма первого столбца 1+4+7=12 */
    }
}