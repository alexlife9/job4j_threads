package ru.job4j.thread;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * CAS - операции
 *
 * @author Alex_life
 * @version 1.0
 * @since 19.09.2022
 */
class CASCountTest {

    @Test
    public void increment() {
        CASCount casCount = new CASCount();
        casCount.increment();
        casCount.increment();
        casCount.increment();
        assertThat(casCount.get()).isEqualTo(3);
    }

    @Test
    public void incrementFive() {
        CASCount casCount = new CASCount();
        casCount.incrementFive();
        casCount.incrementFive();
        assertThat(casCount.get()).isEqualTo(-10);
    }
}