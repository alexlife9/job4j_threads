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
public class User {

    private String userName;
    private String eMail;

    public User(String userName, String eMail) {
        this.userName = userName;
        this.eMail = eMail;
    }

    public String getUserName() {
        return userName;
    }

    public String getEMail() {
        return eMail;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

}
