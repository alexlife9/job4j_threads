package ru.job4j.thread;

/**
 * Управление нитью через wait
 *
 * @author Alex_life
 * @version 1.0
 * @since 17.09.2022
 */
public class MultiUser {
    public static void main(String[] args) {
        Barrier barrier = new Barrier();
        /* Пока нить master не выполнит метод on, нить slave не начнет работу. */
        Thread master = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    barrier.on();
                },
                "Master"
        );
        Thread slave = new Thread(
                () -> {
                    barrier.check();
                    System.out.println(Thread.currentThread().getName() + " started");
                },
                "Slave"
        );
        master.start();
        slave.start();
    }
}
