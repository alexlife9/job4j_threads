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
 * @version 2.0
 * @since 18.09.2022
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
    public synchronized boolean add(Account account) {
        return accounts.putIfAbsent(account.id(), account) == null;
    }

    /**
     * @param account - счет на замену
     * @return успешность обновления счета
     */
    public synchronized boolean update(Account account) {
        return accounts.replace(account.id(), account) != null;
    }

    public synchronized boolean delete(int id) {
        return accounts.remove(id) != null;
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    /**
     * Метод transfer должен перевести сумму amount со счета fromId в счет toId.
     *
     * @param fromId -счет ОТКУДА будем переводить
     * @param toId   -счет КУДА будем переводить
     * @param amount -СКОЛЬКО будем переводить
     * @return -успешно или не очень завершен перевод
     */
    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean rsl = false;
        Optional<Account> from = getById(fromId);
        Optional<Account> to = getById(toId);
        if (amount >= 0 && (from.isPresent() && to.isPresent() && from.get().amount() >= amount)) {
            accounts.replace(fromId, new Account(from.get().id(), from.get().amount() - amount));
            accounts.replace(toId, new Account(to.get().id(), to.get().amount() + amount));
            rsl = true;
        }
        return rsl;
    }
}
