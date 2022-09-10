package ru.job4j.concurrent;

/**
 * Прерывание блокированной нити
 *
 * Метод Thread.interrupt() не выставляет флаг прерывания, если нить находится в режиме WAIT, JOIN.
 * В этом случае методы sleep(), join(), wait() выкинут исключение.
 * Поэтому нужно дополнительно проставить флаг прерывания.
 *
 * Если используются методы sleep(), join() или wait(), то нужно в блоке catch вызвать прерывание!
 *
 * @author Alex_life
 * @version 1.0
 * @since 10.09.2022
 */
public class ThreadStopEternal {
    /* демонстрация вечного цикла*/
    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(
                () -> {
                    /*
                    while (!Thread.currentThread().isInterrupted()) {
                        try {
                            System.out.println("start ...");
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                            System.out.println(Thread.currentThread().isInterrupted());
                            System.out.println(Thread.currentThread().getState());
                        }
                    }
                     */

/* В блоке catch нужно дополнительно вызывать прерывание нити для того чтобы прерывания действительно произошло. */
                    while (!Thread.currentThread().isInterrupted()) {
                        try {
                            System.out.println("start ...");
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        progress.start();
        Thread.sleep(1000);
        progress.interrupt();
        /* Метод join() позволяет вызывающему потоку ждать поток, у которого этот метод вызывается.
        Проще говоря, в данном примере главный поток, который исполняет метод main будет ждать окончания
        выполнения потока progress. */
        progress.join();
    }
}
