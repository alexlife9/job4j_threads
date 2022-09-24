package ru.job4j.cache;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Неблокирующий кеш
 *
 * @author Alex_life
 * @version 1.0
 * @since 24.09.2022
 */
class CacheTest {

    @Test
    public void whenAdd() {
        Base base = new Base(1, 1);
        Cache cache = new Cache();
        cache.add(base);
        assertThat(cache.get(1)).isEqualTo(base);
        assertThat(cache.add(base)).isFalse();
    }

    @Test
    public void whenUpdate() {
        Base base = new Base(1, 1);
        Cache cache = new Cache();
        cache.add(base);
        assertThat(cache.update(new Base(1, 2))).isTrue();
        assertThat(cache.get(1)).isEqualTo(new Base(1, 2));
    }

    @Test
    public void whenDelete() {
        Base base = new Base(1, 1);
        Cache cache = new Cache();
        cache.add(base);
        cache.delete(base);
        assertThat(cache.get(1)).isNull();
    }
}