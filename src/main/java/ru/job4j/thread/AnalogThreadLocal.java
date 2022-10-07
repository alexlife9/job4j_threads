package ru.job4j.thread;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Тестовое задание ThreadLocal
 * В Java SDK есть класс java.lang.ThreadLocal. Класс очень удобный и довольно распространенный –
 * сложно представить себе более-менее крупный Java-проект, в котором не используется ThreadLocal.
 *
 * Если на минуту представить себе что в SDK "забыли" добавить такой удобный класс, то встает вопрос –
 * а можно ли его реализовать самостоятельно, не имея доступа к закрытым частям SDK и native-методам?
 *
 * В этом тестовом задании я предлагаю вам попробовать сделать именно это –
 * реализовать свой собственный аналог класса ThreadLocal с тремя базовыми методами – get, set и remove.
 * При реализации задания хорошо бы учесть возможные concurrency-проблемы
 * и то, что потоки в JVM могут умирать "без предупреждения".
 *
 * Использовать можно только возможности стандартной библиотеки Java/Kotlin
 * Писать нужно на Java (версия 11 и выше) или Kotlin.
 * В задании должен быть рабочий метод main(), это должно быть готовое приложение, а не просто библиотека
 *
 * @author Alex_life
 * @version 1.0
 * @since 07.10.2022
 */
public class AnalogThreadLocal<T> extends Thread {
    public T localVariable; /* базовая переменная */

    public AnalogThreadLocal(T localVariable) {
        this.localVariable = localVariable;
        this.start();
    }

    public AnalogThreadLocal() {
    }

    ConcurrentHashMap<Thread, T> variableStorage = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        AnalogThreadLocal<Integer> thread1 = new AnalogThreadLocal<>(10);
        System.out.println("текущее значение при создании: " + thread1.localVariable);
        thread1.set(20);
        System.out.println();

        AnalogThreadLocal<Integer> thread2 = new AnalogThreadLocal<>(2);
        System.out.println("текущее значение при создании: " + thread2.localVariable);
        thread1.set(3);
        thread2.getT();
        System.out.println();

        AnalogThreadLocal<Integer> thread3 = new AnalogThreadLocal<>(5);
        System.out.println("текущее значение при создании: " + thread3.localVariable);
        thread1.set(7);
        thread3.remove(7);
        System.out.println();

        AnalogThreadLocal<Object> analogThreadLocal = new AnalogThreadLocal<>();
        System.out.println("начальная переменная: " + analogThreadLocal.localVariable); /* остается null */
    }

    /* Устанавливает значение локальной переменной для текущего потока */
    public void set(T value) {
        localVariable = value;
        Thread t = Thread.currentThread();
        variableStorage.put(t, value);
        System.out.println("установили значение: " + value);
    }

    /* Возвращает самое последнее значение локальной переменной текущего потока */
    public void getT() {
        Thread t = Thread.currentThread();
        variableStorage.get(t);
        System.out.println("достали: " + variableStorage.get(t));
    }

    /* Удаляет значение локальной переменной текущего потока */
    public void remove(T value) {
        localVariable = value;
        variableStorage.remove(value);
        System.out.println("удалили: " + value);
    }
}