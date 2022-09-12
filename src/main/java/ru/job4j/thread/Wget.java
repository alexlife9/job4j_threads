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
 * @version 3.0
 * @since 12.09.2022
 */
public class Wget implements Runnable {
    private final String url; /* откуда качаем */
    private final int speed;  /* скорость скачивания - байт/сек */
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
            int bytesRead; /* прочитанные байты из исходника */
            long bytesWrited = 0; /* записанные байты в конечный файл */
            /* задаем переменную старта, равную текущему времени */
            long timeStart = System.currentTimeMillis();
            /* включаем цикл - пока есть что читать - будем записывать */
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                bytesWrited = bytesWrited + bytesRead; /* каждый виток цикла прибавляем кол-во записанных байт */
                if (bytesWrited >= speed) { /* если скорость записи будет больше или равна указанной в параметрах */
                    long timeDownloads = System.currentTimeMillis() - timeStart; /* то измеряем время ушедшее на это */
                    /* если общее время загрузки меньше заданного времени скачивания, то задаем паузу скачивания */
                    if (timeDownloads < 1000) { /* если время загрузки меньше 1 секунды */
                        Thread.sleep(1000 - timeDownloads); /* то вводим задержку равную остатку от секунды */
                    }
                }
                timeStart = System.currentTimeMillis(); /* перед завершением цикла устанавливаем текущее время */
                bytesWrited = 0; /* и обнуляем счетчик прочитанных байт */
            }
            System.out.println("Загрузка завершена!");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    public static void validateArgs(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("некорректное кол-во аргументов. "
                    + "Перед запуском мейна в конфигурацию в поле program arguments необходимо прописать 3 аргумента:\n"
                    + "источник откуда скачивать, скорость ограничения скачивания и куда скачивать");
        }

    }

    public static void main(String[] args) throws InterruptedException {
        /* перед запуском мейна прописываем в конфигурацию в поле program arguments следующую строку:
        * https:\\proof.ovh.net/files/10Mb.dat 1048576 out_file.dat */
        validateArgs(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        String target = args[2];
        Thread wget = new Thread(new Wget(url, speed, target));
        wget.start();
        System.out.println("Начинаем загрузку... ");
        wget.join();
    }
}
