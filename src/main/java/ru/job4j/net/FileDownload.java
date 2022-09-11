package ru.job4j.net;

import java.io.*;
import java.net.URL;

/**
 * Скачивание файла с ограничением
 *
 * @author Alex_life
 * @version 1.0
 * @since 11.09.2022
 */
public class FileDownload {
    public static void main(String[] args) throws Exception {
        /* Пример кода для скачивания файла с задержкой в одну секунду. */
        String file = "https://raw.githubusercontent.com/peterarsentev/course_test/master/pom.xml";
        try (BufferedInputStream in = new BufferedInputStream(new URL(file).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("pom_tmp.xml")) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                Thread.sleep(1000);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
