package ru.job4j.thread;

import java.io.*;

/**
 * Visibility. Общий ресурс вне критической секции
 *
 * Если у нас есть общий ресурс, то с ним нужно работать только в критической секции.
 * Если это не сделать, то мы получаем состояние гонки (race condition).
 * Как бы две нити соревнуются, кто первый получит доступ к ресурсу.
 *
 * Задание - Поправить ошибки в коде:
 * 1) Избавиться от get set за счет передачи File в конструктор.
 * 2) Ошибки в многопоточности. Сделать класс Immutable. Все поля final.
 * 3) Ошибки в IO. Не закрытые ресурсы. Чтение и запись файла без буфера.
 * 4) Нарушен принцип единой ответственности. Тут нужно сделать два класса.
 * 5) Методы getContent написаны в стиле копипаста.
 * Нужно применить шаблон стратегия. content(Predicate<Character> filter)
 *
 * Решение:
 * 1. Создаем два разных класса: ParseFileSRP и ParseSaveSRP. Этим убираем нарушение SRP.
 * 2. В классе ParseFileSRP убираем сеттеры и геттеры, File передаем в конструктор.
 * 3. Поля file делаем final, соответственно класс ParseFileSRP становится Immutable.
 * 4. Создаем отдельный метод content(Predicate<Character> filter) и переносим в него дублирующуюся логику.
 * 5. В метод content() добавляем чтение из буфера и закрываем ресурсы после работы
 * 6. В класс ParseSaveSRP вынесли метод сохранения файла,
 * сделали поле file - final, добавили конструктор и закрыли ресурсы
 *
 * @author Alex_life
 * @version 1.0
 * @since 16.09.2022
 */
public class ParseFile {
    private File file;

    public synchronized void setFile(File f) {
        file = f;
    }

    public synchronized File getFile() {
        return file;
    }

    public String getContent() throws IOException {
        InputStream i = new FileInputStream(file);
        String output = "";
        int data;
        while ((data = i.read()) > 0) {
            output += (char) data;
        }
        return output;
    }

    public String getContentWithoutUnicode() throws IOException {
        InputStream i = new FileInputStream(file);
        String output = "";
        int data;
        while ((data = i.read()) > 0) {
            if (data < 0x80) {
                output += (char) data;
            }
        }
        return output;
    }

    public void saveContent(String content) throws IOException {
        OutputStream o = new FileOutputStream(file);
        for (int i = 0; i < content.length(); i += 1) {
            o.write(content.charAt(i));
        }
    }
}
