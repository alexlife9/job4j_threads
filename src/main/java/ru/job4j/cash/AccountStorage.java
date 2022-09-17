package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

/**
 * Денежные переводы AccountStorage
 *
 * Задание:
 * 1. решить классическую задачу по переводу денег с одного счета на другой.
 * 2. Сделать блокирующий кеш UserStorage для модели User.
 *
 * Кеш - это подход в программировании позволяющий увеличить скорость работы с данными
 * за счет временного хранения данных в памяти.
 * Например, у нас есть жесткий диск, часть информации мы можем загрузить в оперативную память. Сначала надо проверить,
 * есть ли данные в оперативной памяти, если их нет, то загрузить с диска.
 *
 * У нас будет многопользовательская среда, а значит нам нужно обеспечить эксклюзивных доступ к хранилищу пользователей.
 * Чтобы операции были атомарны, нам нужен один объект монитора.
 * В этом задании объект монитора будет объект класса UserStore.
 * В хранилище можно добавлять новых пользователей, обновлять данные пользователей, удалять пользователей
 * и осуществлять денежные переводы.
 *
 * Доступ ко всем методам класса AccountStorage должен быть эксклюзивным, то есть нужно использовать синхронизацию.
 * Поле accounts - это общий ресурс для нитей, поэтому этот объект можно использовать в качестве объекта монитора.
 *
 * @author Alex_life
 * @version 1.0
 * @since 17.09.2022
 */
@ThreadSafe
public class AccountStorage {
    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    /**
     * account.id() это ключ в HashMap
     * @param account добавление нового счета
     * @return успешность добавления
     */
    public boolean add(Account account) {
        synchronized (accounts) {
            Optional<Account> user = Optional.ofNullable(accounts.putIfAbsent(account.id(), account));
            return user.isEmpty();
        }
    }

    /**
     *
     * @param account - счет на замену
     * @return успешность обновления счета
     */
    public boolean update(Account account) {
        synchronized (accounts) {
            Optional<Account> user = Optional.ofNullable(accounts.replace(account.id(), account));
            return user.isPresent();
        }
    }

    public boolean delete(int id) {
        synchronized (accounts) {
            Optional<Account> user = Optional.ofNullable(accounts.remove(id));
            return user.isPresent();
        }
    }

    public Optional<Account> getById(int id) {
        synchronized (accounts) {
            return Optional.ofNullable(accounts.get(id));
        }
    }

    /**
     * Метод transfer должен перевести сумму amount со счета fromId в счет toId.
     *
     * @param fromId -счет ОТКУДА будем переводить
     * @param toId   -счет КУДА будем переводить
     * @param amount -СКОЛЬКО будем переводить
     * @return -успешно или не очень завершен перевод
     */
    public boolean transfer(int fromId, int toId, int amount) {
        synchronized (accounts) {
            boolean rsl = false;
            Optional<Account> from = getById(fromId);
            Optional<Account> to = getById(toId);
            if (from.isPresent() && to.isPresent() && from.get().amount() >= amount) {
                accounts.replace(fromId, new Account(from.get().id(), from.get().amount() - amount));
                accounts.replace(toId, new Account(to.get().id(), to.get().amount() + amount));
                rsl = true;
            }
            return rsl;
        }
    }
}
