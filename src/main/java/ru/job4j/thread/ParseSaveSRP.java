package ru.job4j.thread;

import java.io.*;

/**
 * Visibility. Общий ресурс вне критической секции
 *
 * @author Alex_life
 * @version 1.0
 * @since 16.09.2022
 */
public class ParseSaveSRP {
    private final File file;

    public ParseSaveSRP(File file) {
        this.file = file;
    }

    public void saveContent(String content) {
        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
            for (int i = 0; i < content.length(); i += 1) {
                out.write(content.charAt(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
