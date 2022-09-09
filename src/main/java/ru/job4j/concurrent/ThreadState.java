package ru.job4j.concurrent;

/**
 * Состояние нити
 *
 * Нити имеют состояния выполнения:
 * NEW - нить создана, но не запущена
 * RUNNABLE - нить запущена и выполняется
 * BLOCKED - нить заблокирована
 * WAITING - нить ожидает уведомления
 * TIMED_WAITING - нить ожидает уведомление в течении определенного периода
 * TERMINATED - нить завершила работу
 *
 * По состояниям нити можно произвести диагностику, что происходит в нашей программе.
 *
 *
 * @author Alex_life
 * @version 1.0
 * @since 09.09.2022
 */
public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> {}
        );                                                     /* New - Создан объект нити */
        /* Чтобы получить состояние нити можно воспользоваться методом - getState()
        * Этот метод возвращает перечисления Thread.State
        * Тело цикла while выполняется произвольное количество раз!!!
        * За управление нитями в Java отвечает планировщик задач. Он решает, сколько времени отвести на выполнение одной
        * задачи. Это время зависит от текущей ситуации. Если задач много, то переключение между нитями будет частое.
        */
        System.out.println(first.getState()  + " - create new thread first");
        first.start();                                         /* Runnable - У объекта нити вызван метод start */
        /* В цикле мы проверяем состояние запущенной нити */
        while (first.getState() != Thread.State.TERMINATED) {  /* Terminated - Все операторы в методе run выполнены */
            System.out.println(first.getState() + " - run first");
        }
        System.out.println(first.getState() + " - terminated first");

        /* создаем вторую нить и выводим ее состояния в консоль */
        Thread second = new Thread(
                () -> {}
        );
        System.out.println(second.getState()  + " - it`s second");
        second.start();
        while (second.getState() != Thread.State.TERMINATED) {
            System.out.println(second.getState() + " - it`s second");
        }
        System.out.println(second.getState() + " - it`s second");

        System.out.println(Thread.currentThread().getName() + " the work is completed");
    }
}
