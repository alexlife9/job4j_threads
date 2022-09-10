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
 * @version 3.0
 * @since 11.09.2022
 */
public class ThreadState {

    public static void main(String[] args) {
        /* Чтобы получить состояние нити можно воспользоваться методом - getState()
         * Этот метод возвращает перечисления Thread.State
         * Тело цикла while выполняется произвольное количество раз!!!
         * Поэтому и вывод состояний будет произвольное.
         * За управление нитями в Java отвечает планировщик задач.Он решает,сколько времени отвести на выполнение одной
         * задачи. Это время зависит от текущей ситуации. Если задач много, то переключение между нитями будет частое.
         */
        /* NEW - создаем две нити и выводим их состояния в консоль */
        Thread first = new Thread(
                () -> System.out.println(Thread.currentThread().getName() + " - нить first")
        );
        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName() + " - нить second")
        );

        /* RUNNABLE - У объектов нитей вызван метод start */
        first.start();
        second.start();

        /* В цикле проверяем состояние запущенных нитей */
        /* когда состояние одной из нитей становится Terminated - останавливаем метод run */
        while (first.getState() != Thread.State.TERMINATED
                || second.getState() != Thread.State.TERMINATED) {
            System.out.println("First thread state: " + first.getState()
                    + ". Second thread state: " + second.getState() + ".");
        }
        System.out.println(first.getState() + " - terminated first");
        System.out.println(second.getState() + " - terminated second");

        System.out.println(Thread.currentThread().getName() + " - нить main: работа завершена");
    }
}
