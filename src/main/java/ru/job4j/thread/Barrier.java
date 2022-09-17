package ru.job4j.thread;

/**
 * Управление нитью через wait
 *
 * В блоке IO рассматривалась тема с Socket. Класс ServerSocket имеет метод accept.
 * Метод accept переводит программу в режим ожидания. Программа ждет, когда к порту присоединится клиент.
 * Очевидное решение такой задачи - это цикл с задержкой. Но такое решение не верно,
 * потому что клиент может прийти сразу, а может и через час.
 * Программа будет проверять состояние впустую. Тратить на это ресурсы.
 *
 * В Java есть механизм позволяющий разбудить нить, если состояние программы поменялось.
 * У объекта монитора есть методы wait, notifyAll.
 * --Метод notifyAll будит все нити, которые ждали изменения состояния.
 * --Метод wait переводит нить в состояние ожидания, если программа не может дальше выполняться.
 *
 * @author Alex_life
 * @version 1.0
 * @since 17.09.2022
 */
public class Barrier {
    /**
     * Переменная flag - это общий ресурс, поэтому мы с ней работаем только в критической секции.
     */
    private boolean flag = false;

    /**
     * Синхронизация и методы nofityAll/wait вызываются у объекта класса Barrier.
     * Следующая строчка кода сделана для наглядности объекта монитора.
     */
    private final Object monitor = this;

    /**
     * Метод on и off меняют флаг с true на false. После каждого изменения программа будит нити, которые ждут изменений
     */
    public void on() {
        synchronized (monitor) {
            flag = true;
            monitor.notifyAll();
        }
    }

    public void off() {
        synchronized (monitor) {
            flag = false;
            monitor.notifyAll();
        }
    }

    /**
     * Когда нить заходит в метод check, то она проверяет flag. Если флаг = false, то нить засыпает.
     * Когда другая нить выполнит метод on или off, то у объекта монитора выполняется метод notifyAll.
     * Метод notifyAll переводит все нити из состояния wait в runnable.
     *
     * Переключение нити из состояния wait в runnable операция не атомарная.
     * Это значит, что состояние программы может поменяться, когда нить начнет выполнять полезную работу.
     * Чтобы избежать проблем с согласованностью данных,
     * метод wait всегда вызывается в цикле while, а не в условном блоке if.
     *
     * Это позволяет сделать постпроверку, когда нить перешла в состояние Runnable.
     */
    public void check() {
        synchronized (monitor) {
            while (!flag) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
