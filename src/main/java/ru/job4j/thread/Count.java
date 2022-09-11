package ru.job4j.thread;

/**
 * Атомарность
 *
 * Случай, когда две нити имеют ссылку на один и тот же объект.
 *
 * @author Alex_life
 * @version 1.0
 * @since 11.09.2022
 */
public class Count {
    private int value;

    public void increment() {
        value++;
    }

    public int get() {
        return value;
    }
}
