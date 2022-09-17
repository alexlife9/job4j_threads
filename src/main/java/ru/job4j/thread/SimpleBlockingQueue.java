package ru.job4j.thread;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Блокирующая очередь, ограниченная по размеру
 *
 * Задание:
 * Реализовать шаблон Producer Consumer.
 *
 * Для этого необходимо реализовать собственную версию bounded blocking queue.
 * Это блокирующая очередь, ограниченная по размеру.
 * В данном шаблоне Producer помещает данные в очередь, а Consumer извлекает данные из очереди.
 *
 * Если очередь заполнена полностью, то при попытке добавления поток Producer блокируется,
 * до тех пор пока Consumer не извлечет очередные данные, т.е. в очереди появится свободное место.
 * И наоборот если очередь пуста поток Consumer блокируется, до тех пор пока Producer не поместит в очередь данные.
 *
 * В решении нельзя использовать потокобезопасные коллекции реализованные в JDK.
 * Нужно используя wait/notify реализовать блокирующую очередь.
 *
 * Producer и Consumer - по сути это обычные нити.
 * Для того чтобы нить перевести в ждущее состояние необходимо в ее процессе вызвать метод wait() для объекта монитора.
 * Для того чтобы разбудить нить, нужно, чтобы другая нить вызвала у объекта монитора метод notify().
 *
 * Когда нить переходит в состояние ожидания, то она отпускает объект монитор и другая нить тоже может
 * выполнить этот метод. Либо монитор снова захватит первая нить.
 *
 * @author Alex_life
 * @version 1.0
 * @since 17.09.2022
 */
@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>(); /* этот объект является общим ресурсом между нитями */

    private final int maxSize;

    public SimpleBlockingQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    /**
     * метод producer добавляет элементы в очередь
     * @param value - значение элемента
     */
    public synchronized void producer(T value) {
        while (queue.size() == maxSize) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        queue.add(value);
        System.out.println("Добавили в очередь элемент: " + value);
        /* если размер очереди равен максимальному заданному размеру, то сообщаем другим потокам что монитор свободен */
        notify();
    }

    /**
     * Метод consumer() должен вернуть объект из внутренней коллекции.
     * Если в коллекции объектов нет, то нужно перевести текущую нить в состояние ожидания.
     */
    public synchronized T consumer() {
        while (isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        T value = queue.poll();
        System.out.println("Удалили элемент из очереди: " + value);
        notify();
        return value;
    }

    public static void main(String[] args) throws InterruptedException {
        SimpleBlockingQueue<Integer> simple = new SimpleBlockingQueue<>(100);
        Thread producer = new Thread(
                () -> {
                    for (int i = 0; i < 300; i++) {
                        simple.producer(i);
                    }
        });

        Thread consumer = new Thread(
                () -> {
                    for (int i = 0; i < 300; i++) {
                        simple.consumer();
                    }
        });

        producer.start();
        consumer.start();
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}