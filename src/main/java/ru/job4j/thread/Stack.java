package ru.job4j.thread;

import net.jcip.annotations.NotThreadSafe;

/**
 * CAS - операции
 *
 * Операции write и read по отдельности атомарны. volatile обеспечивает правильную публикацию изменений.
 * Но во многих случаях нам нужны действия check-then-act. Чтобы этого добиться, нужно делать синхронизацию, но
 * синхронизация блокирует выполнение нитей, то есть программа из многопоточной превращается в однопоточную.
 *
 * Процессоры на уровне ядра поддерживают операцию compare-and-swap. Эта операция атомарная. В Java есть структуры
 * данных, которые реализуют этот механизм CA и поддерживают атомарные операции,
 * например AtomicInteger, AtomicLong, LongAdder
 *
 * Если не указать synchronized, то вывод будет непредсказуемым, но synchronized довольно тяжелая операция,
 * каждый раз при обращении потока идет lock/unlock монитора. Но мы обязаны использовать synchronized при
 * не атомарных операциях (не неделимых), где сначала читается значение, потом изменяется, потом запись в память.
 *
 * Поэтому для таких случаев надо пользоваться специальным классом - AtomicInteger, в котором все операции атомарны и
 * не нужно будет указывать synchronized.
 * При использовании класса AtomicInteger невозможно состояние гонки потоков Date Race
 *
 *
 * Класс Stack не потокобезопасный, поэтому применяем к нему класс AtomicReference
 *
 * @author Alex_life
 * @version 1.0
 * @since 18.09.2022
 */
@NotThreadSafe
public class Stack<T> {
    private Node<T> head;

    /* Методы push и poll используют шаблон check-then-act. */
    public void push(T value) {
        Node<T> temp = new Node<>(value);
        if (head == null) {
            head = temp;
            return;
        }
        temp.next = head;
        head = temp;
    }

    public T poll() {
        Node<T> temp = head;
        if (temp == null) {
            throw new IllegalStateException("Stack is empty");
        }
        head = temp.next;
        temp.next = null;
        return temp.value;
    }

    private static final class Node<T> {
        private final T value;

        private Node<T> next;

        public Node(final T value) {
            this.value = value;
        }
    }
}
