package ru.job4j.thread;

import net.jcip.annotations.NotThreadSafe;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Thread без общих ресурсов
 *
 * @author Alex_life
 * @version 1.0
 * @since 13.09.2022
 */
/* этот класс НЕ является потокобезопасным */
@NotThreadSafe
public class UserCache {
    private final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger();
/*
    public void add(User user) {
        users.put(id.incrementAndGet(), user);
    }

    public User findById(int id) {
        return users.get(id);
    }

 */

    /* поэтому делаем его потокобезопасным - в кеш добавляем копию объекта и возвращаем копию */
    public void add(User user) {
        users.put(id.incrementAndGet(), User.of(user.getName()));
    }

    public User findById(int id) {
        return User.of(users.get(id).getName());
    }

    /* добавили новый метод findAll */
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    /* чтобы сделать его потокобезопасным делаем копию аррэйлиста и возвращаем из метода именно ее */
    public List<User> findAllThreadSafe() {
        List<User> copyUsers = new ArrayList<>();
        for (User user: users.values()) {
            copyUsers.add(User.of(user.getName()));
        }
        return copyUsers;
    }


}
