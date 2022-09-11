package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 * Скачивание файла с ограничением
 *
 * Задание: нужно написать консольную программу - аналог wget.
 * Программа должна скачивать файл из сети с ограничением по скорости скачки.
 * Чтобы ограничить скорость скачивания, нужно засечь время скачивания 1024 байт.
 * Если время меньше указанного, то нужно выставить паузу за счет Thread.sleep.
 * Пауза должна вычисляться, а не быть константой.
 *
 * @author Alex_life
 * @version 1.0
 * @since 11.09.2022
 */
public class Wget implements Runnable {
    private final String url; /* откуда качаем */
    private final int speed;  /* скорость скачивания */
    private final String target; /* куда качаем */

    public Wget(String url, int speed, String target) {
        this.url = url;
        this.speed = speed;
        this.target = target;
    }

    @Override
    public void run() {
        /* указываем источник и аутпут */
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(target)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;

            /* задаем переменную старта, равную текущему времени */
            long timeStart = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                long timeDownloads = System.currentTimeMillis() - timeStart;
                if (timeDownloads < speed) {
                    /* если общее время загрузки меньше заданного времени скачивания, то задаем паузу скачивания */
                    Thread.sleep(speed - timeDownloads);
                }
                timeStart = System.currentTimeMillis();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        /*String url = args[0];
        int speed = Integer.parseInt(args[1]);
        String target = args[2];*/
        String url = "https://raw.githubusercontent.com/peterarsentev/course_test/master/pom.xml";
        int speed = 100;
        String target = "out_file.xml";
        Thread wget = new Thread(new Wget(url, speed, target));
        wget.start();
        wget.join();
    }
}
