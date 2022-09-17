package ru.job4j.cash;

/**
 * Денежные переводы AccountStorage
 *
 * Класс Account имеет два поля: ID - уникальный идентификатор, amount - баланс пользователя.
 *
 * @author Alex_life
 * @version 1.0
 * @since 17.09.2022
 */
public record Account(int id, int amount) {
}
