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
    /*1. Добавляю коммит
     * 2. Жмакнул коммит энд пуш, но на втором окне осознал ошибку в программе и не нажал пуш
     * 3. Теперь исправил ошибку, поставил галку амменд и нажал коммит, но не пуш
     * 3. Теперь в консоли ввожу git push origin +master и фиксирую изменения уже на гитхабе
     * 4. В итоге два последних коммита объединятся
     */
}
