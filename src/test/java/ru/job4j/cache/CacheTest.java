package ru.job4j.cache;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Неблокирующий кеш
 *
 * @author Alex_life
 * @version 2.0
 * @since 24.09.2022
 */
class CacheTest {

    @Test
    public void whenAdd() {
        Base base = new Base(1, 1);
        Cache cache = new Cache();
        assertThat(cache.add(base)).isTrue();
        assertThat(cache.get(1)).isEqualTo(new Base(1, 1));
    }

    @Test
    public void whenUpdateException() {
        Base base = new Base(1, 2);
        Base base2 = new Base(1, 3);
        Cache cache = new Cache();
        cache.add(base);
        assertThatExceptionOfType(OptimisticException.class).isThrownBy(() -> cache.update(base2));
        assertThat(cache.get(1)).isEqualTo(base);
    }

    @Test
    public void whenUpdateTrue() {
        Base base = new Base(1, 1);
        Cache cache = new Cache();
        cache.add(base);
        assertThat(cache.update(base)).isTrue();
        assertThat(cache.get(1)).isEqualTo(new Base(1, 2));
    }

    @Test
    public void whenUpdateFalse() {
        Cache cache = new Cache();
        Base base = new Base(1, 1);
        cache.add(base);
        assertThat(cache.update(new Base(2, 1))).isFalse();
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