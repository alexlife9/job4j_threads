package ru.job4j.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ExecutorService рассылка почты
 *
 * ТЗ:
 * 1. Реализовать сервис для рассылки почты - EmailNotification.
 * 2. В классе будет метод emailTo(User user) - он должен через ExecutorService отправлять почту.
 * Добавить метод close() - он должен закрыть pool. То есть в классе EmailNotification должно быть поле pool,
 * которое используется в emailTo и close().
 * 3. Модель User описывают поля username, email.
 * 4. Метод emailTo должен брать данные пользователя и подставлять в шаблон:
 * - subject = Notification {username} to email {email}.
 * - body = Add a new event to {username}
 * 5. Создать метод public void send(String subject, String body, String email) - он должен быть пустой.
 * 6. Через ExecutorService создать задачу, которая будет создавать данные для юзера и передавать их в метод send.
 *
 * @author Alex_life
 * @version 1.0
 * @since 30.09.2022
 */
public class EmailNotification {

    private final ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    public void emailTo(User user) {
        String userName = user.getUserName();
        String eMail = user.getEMail();
        pool.submit(() -> {
            String subject = String.format("Notification %s to email %s", userName, eMail);
            String body = String.format("Add a new event to %s", userName);
            send(subject, body, eMail);
        });
    }

    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String subject, String body, String email) {
    }
}
