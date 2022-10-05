package ru.job4j.pool;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * CompletableFuture
 *
 * Задание:
 * 1. Дан каркас класса. Ваша задача подсчитать суммы по строкам и столбцам квадратной матрицы.
 * - sums[i].rowSum - сумма элементов по i строке
 * - sums[i].colSum  - сумма элементов по i столбцу
 * 2. Реализовать последовательную версию программы
 * 3. Реализовать асинхронную версию программы
 *
 * @author Alex_life
 * @version 2.0
 * @since 05.10.2022
 */
public class RolColSum {

    /* метод counting подсчитывает суммы элементов в строках и столбцах
     * matrix - квадратная матрица
     * sums - массив сумм строк и столбцов
     * i - указатель ячейки
     * */
    private static void counting(int[][] matrix, Sums[] sums, int i) {
        int rowS = 0;
        int colS = 0;
        for (int j = 0; j < matrix.length; j++) {
            rowS = rowS + matrix[i][j];
            colS = colS + matrix[j][i];
        }
        sums[i] = new Sums(rowS, colS);
    }

    /**
     * последовательная версия программы
     * @param matrix квадратный массив
     * @return сумма значений элементов в строках и столбцах
     */
    public static Sums[] sum(int[][] matrix) {
        Sums[] sumsLine = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            counting(matrix, sumsLine, i);
        }
        return sumsLine;
    }

    /* асинхронная версия программы */
    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] sumsAsync = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            sumsAsync[i] = getTask(matrix, i).get();
        }
        return sumsAsync;
    }

    public static CompletableFuture<Sums> getTask(int[][] matrix, int i) {
        Sums[] sums = new Sums[matrix.length];
        return CompletableFuture.supplyAsync(
                () -> {
                    counting(matrix, sums, i);
                    return sums[i];
                }
        );
    }
}