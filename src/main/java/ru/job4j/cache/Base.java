package ru.job4j.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Неблокирующий кеш
 *
 * Знакомство с CAS методами потокобезопасной коллекции ConcurrentHashMap.
 * Коллекции Map используют для создания кешей. Кеш - это элемент программы,
 * позволяющий увеличить скорость работы за счет хранения данных в памяти.
 * Например, если данные о пользователя храниться в базе данных и мы часто их используем,
 * то что бы увеличить скорость работы можно загрузить информацию о пользователях в память и читать сразу из памяти.
 * Это даст значительный прирост скорости.
 *
 * @author Alex_life
 * @version 1.0
 * @since 19.09.2022
 */
public class Base {
    /**
     * ID - уникальный идентификатор. В системе будет только один объект с таким ID.
     * version - определяет достоверность версии в кеше.
     * name - это поля бизнес модели. В нашем примере это одно поле name. Это поле имеет get set методы.
     */

    private final int id;
    private final int version;
    private String name;

    public Base(int id, int version) {
        this.id = id;
        this.version = version;
    }

    public int getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Base{"
                + "id=" + id
                + ", version=" + version
                + ", name='" + name + '\''
                + '}';
    }

    public static void main(String[] args) {
        Map<Integer, Base> map = new HashMap<>();
        Base base = new Base(1, 0);
        map.put(base.getId(), base);

        Base user1 = map.get(1);
        user1.setName("User 1");

        Base user2 = map.get(1);
        user1.setName("User 2");

        map.put(user1.getId(), user1);
        map.put(user2.getId(), user2);

        System.out.println(map);
    }
}
