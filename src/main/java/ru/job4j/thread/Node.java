package ru.job4j.thread;

/**
 * Immutable объекты
 *
 * Проблемы в многопоточной среде возникают из-за объектов, которые могут менять состояния.
 * Правила создания Immutable объекта.
 * 1. Все поля отмечены final.
 * 2. Состояние объекта не изменяется после создания объекта.
 *
 * immutable это еще и запрет наследования
 *
 * @author Alex_life
 * @version 2.0
 * @since 13.09.2022
 */
public final class Node<T> {
    private final Node<T> next;
    private final T value;

    public Node(Node<T> next, T value) {
        this.next = next;
        this.value = value;
    }

    public Node<T> getNext() {
        return next;
    }

    public T getValue() {
        return value;
    }

    /* поскольку поля final - установить новые значения мы не сможем, поэтому сеттеры не нужны
    public void setNext(Node<T> next) {
        this.next = next;
    }

    public void setValue(T value) {
        this.value = value;
    }
     */
}
