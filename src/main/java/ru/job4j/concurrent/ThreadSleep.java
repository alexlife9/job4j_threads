package ru.job4j.concurrent;

/**
 * Режим ожидания Thread
 *
 * В классе Thread есть статический метод sleep. Этот метод переводит нить в состояние TIMED_WAITING.
 * Так же этот метод может выкинуть исключение -InterruptedException.
 * Это связано с тем, что нить могут попросить прервать свое выполнение и программисту необходимо предусмотреть
 * дальнейшие действия, если такое случилось.
 *
 * Программа ThreadSleep ждет 3 секунды и печатает на консоль слово loaded.
 *
 * @author Alex_life
 * @version 1.0
 * @since 09.09.2022
 */
public class ThreadSleep {
    public static void main(String[] args) {
        Thread thread = new Thread(
                () -> {
                    try {
                        System.out.println("Start loading ... ");
                        Thread.sleep(3000);
                        System.out.println("Loaded.");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        thread.start();
        System.out.println("Main");
    }
}


