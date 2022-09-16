package ru.job4j.thread;

import java.io.*;
import java.util.function.Predicate;

/**
 * Visibility. Общий ресурс вне критической секции
 *
 * @author Alex_life
 * @version 2.0
 * @since 16.09.2022
 */
public class ParseFileSRP {
    private final File file;

    public ParseFileSRP(File file) {
        this.file = file;
    }

    private String content(Predicate<Character> predicate) {
        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            int data;
            while ((data = reader.read()) != -1) { /* в документации read возвращает -1 */
                if (predicate.test((char) data)) {
                    output.append((char) data);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output.toString();
    }

    public String getContentWithoutUnicode() throws IOException {
        return content(data -> data < 0x80);
    }

    public String getContent() throws IOException {
        return content(data -> true);
    }
}
