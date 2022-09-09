package ru.job4j.concurrent;

/**
 * Симуляция процесса загрузки в консоли
 *
 * @author Alex_life
 * @version 1.0
 * @since 09.09.2022
 */
public class Wget {
    public static void main(String[] args) {
        Thread thread = new Thread(
                () -> {
                        System.out.println("Прогресс загрузки:");
                        try {
                            for (int i = 0; i < 100; i++) {
                                System.out.print("\rЗагрузка : " + i + "%");
                                Thread.sleep(1000);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
        );
        thread.start();
        System.out.println("Начинаем загрузку... ");

    }
}
