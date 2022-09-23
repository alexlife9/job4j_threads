package ru.job4j.cache;

/**
 * @author Alex_life
 * @version 1.0
 * @since 19.09.2022
 */
public class OptimisticException extends RuntimeException {

    public OptimisticException(String message) {
        super(message);
    }
}
