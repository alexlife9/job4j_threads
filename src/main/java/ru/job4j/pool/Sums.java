package ru.job4j.pool;

import java.util.Objects;

/**
 * CompletableFuture
 *
 * @author Alex_life
 * @version 1.0
 * @since 05.10.2022
 */
public record Sums(int rowSum, int colSum) {

    public int getRowSum() {
        return rowSum;
    }

    public int getColSum() {
        return colSum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Sums sums = (Sums) o;
        return rowSum == sums.rowSum && colSum == sums.colSum;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rowSum, colSum);
    }

    @Override
    public String toString() {
        return "Sums{"
                + "rowSum=" + rowSum
                + ", colSum=" + colSum
                + '}';
    }
}
