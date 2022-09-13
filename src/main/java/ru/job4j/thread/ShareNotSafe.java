package ru.job4j.thread;

/**
 * Thread без общих ресурсов
 *
 * Демонстрация проблемы с потокобезопасностью
 *
 * @author Alex_life
 * @version 1.0
 * @since 13.09.2022
 */
public class ShareNotSafe {
    public static void main(String[] args) throws InterruptedException {
        UserCache cache = new UserCache();
        User user = User.of("name");
        cache.add(user); /* Добавляем объект в кеш */
        Thread first = new Thread(
                () -> {
                    user.setName("rename"); /* Редактируем объект по ссылке */
                }
        );
        first.start();
        first.join();
        System.out.println(cache.findById(1).getName()); /*  Получаем значение */
        /* Оператор getName() может напечатать name или rename.*/
        /* Чтобы этого избежать, в кеш нужно добавлять копию объекта и возвращать копию -> см. UserCache */
        /* В этом случае нить first работает с локальным объект user.
        Изменение этого объекта не влечет изменений в самом кеше. */
        /* Теперь методы add и findById работают с копиями объекта User. */
    }
}
