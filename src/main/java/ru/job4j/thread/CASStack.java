package ru.job4j.thread;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

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
 * В классе CASStack нет синхронизации, но класс потокобезопасный.
 *
 * @author Alex_life
 * @version 1.0
 * @since 19.09.2022
 */
@ThreadSafe
public class CASStack<T> {

    private final AtomicReference<Node<T>> head = new AtomicReference<>();

    public void push(T value) {
        Node<T> temp = new Node<>(value);
        Node<T> ref;
        /* Вначале мы считываем указатель */
        do {
            ref = head.get();
            temp.next = ref;
            /* Указатель могут считать несколько потоков одновременно, не блокируя эту операцию
            * Метод compareAndSet атомарный. Это значит - если два потока прочитали одно и тоже значение ref,
            * то первый вызов compareAndSet даст true, а второй вызов вернет false.
            * Вторая нить будет заново читать ref и выполнять операцию compareAndSet.
            * Обе нити не блокируются, а выполняются параллельно.*/
        } while (!head.compareAndSet(ref, temp));
    }

    /**
     * head.compareAndSet(ref, temp)
     * head - сравниваемое значение
     * ref - то с чем сравниваем
     * temp - результат который будет записан в head при условии что head и ref равны
     */
    public T poll() {
        Node<T> ref;
        Node<T> temp;
        do {
            ref = head.get();
            if (ref == null) {
                throw new IllegalStateException("Stack i,s empty");
            }
            temp = ref.next;
        } while (!head.compareAndSet(ref, temp));
        ref.next = null;
        return ref.value;
    }

    private static final class Node<T> {
        final T value;

        Node<T> next;

        public Node(final T value) {
            this.value = value;
        }
    }
}
