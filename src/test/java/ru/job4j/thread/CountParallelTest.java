package ru.job4j.thread;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * JCIP. Настройка библиотеки
 *
 * Как мы видим все тестирование с нитями сводится к тестированию последовательных операций.
 * Этого мы достигаем за счет использования метода join.
 * Важно. Этот тест не является надежным. То есть он зависит от случая, один раз он может пройти, другой раз нет.
 * По этой причине подобные тесты мы не пишем. То есть это те тесты, где создаются нити.
 *
 * @author Alex_life
 * @version 1.0
 * @since 16.09.2022
 */
class CountParallelTest {

    @Test
    public void whenExecute2ThreadThen2() throws InterruptedException {
        var count = new CountParallel();
        var first = new Thread(count::increment);
        var second = new Thread(count::increment);
        /* Запускаем нити. */
        first.start();
        second.start();
        /* Заставляем главную нить дождаться выполнения наших нитей. */
        first.join();
        second.join();
        /* Проверяем результат. */
        assertThat(count.get()).isEqualTo(2);
    }
}