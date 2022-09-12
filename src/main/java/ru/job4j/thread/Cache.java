package ru.job4j.thread;

/**
 * Синхронизация общих ресурсов
 *
 * @author Alex_life
 * @version 1.0
 * @since 12.09.2022
 */
public final class Cache {
    private static Cache cache;

    public static synchronized Cache instOf() {
        if (cache == null) {
            cache = new Cache();
        }
        return cache;
    }
    /* Добавляю коммит */
}
