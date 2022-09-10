package ru.job4j.concurrent;

/**
 * Прерывание нити
 *
 * @author Alex_life
 * @version 3.0
 * @since 10.09.2022
 */
public class ConsoleProgress implements Runnable {
    @Override
    public void run() {
        String[] strings = {"\\", "|", "/"};
        while (!Thread.currentThread().isInterrupted()) {
            for (String process : strings) {
                try {
                    Thread.sleep(500);
                    System.out.print("\r load: " + process);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();

                }
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(4000);
        progress.interrupt();
    }
}