package ru.job4j.thread;

/**
 * Синхронизация общих ресурсов
 *
 * Чтобы добиться атомарности не атомарных операций, в Java используется механизм синхронизации.
 * Если две нити пробуют выполнить один и тот же синхронизированный метод,
 * то одна из нитей переходит в режим блокировки до тех пор пока первая нить не закончить работу с этим методом.
 * Синхронизация делает параллельную программу последовательной.
 *
 * Код внутри метода обозначенного synchronized называется критической секцией.
 * То есть это область памяти, с которой одновременно может работать только одна нить.
 * Виртуальная машина Java использует механизм объектов мониторов для регулирования эксклюзивного доступа.
 *
 * В Java есть два механизма указать объект монитор: явный и неявный.
 * В примере выше мы использовали неявный объект монитор.
 *
 * В случае нестатического метода объект монитор будет объект этого класса.
 *
 * В случае со статическом методом объект монитор будет сам класс.
 *
 * @author Alex_life
 * @version 1.0
 * @since 12.09.2022
 */
public class ShowRoomLock {
    /* В случае нестатического метода объект монитор будет объект этого класса.
    Неявно указываем  - это объект ShowRoomLock */
    public synchronized void lockOfInstance() {
    }

    /* В случае со статическом методом объект монитор будет сам класс.
    Неявно указываем объект монитор будет сам класс ShowRoomLock */
    public static synchronized void lockOfClass() {
    }

    /* В случае нестатического метода объект монитор будет объект этого класса.
    Явно указываем объект монитор - это объект ShowRoomLock */
    public void lockOfInstanceExplizite() {
        synchronized (this) {
            return;
        }
    }

    /* В случае со статическом методом объект монитор будет сам класс.
    Явно указываем объект монитор будет сам класс ShowRoomLock */
    public static void lockOfClassExplizite() {
        synchronized (ShowRoomLock.class) {
            return;
        }
    }
}
