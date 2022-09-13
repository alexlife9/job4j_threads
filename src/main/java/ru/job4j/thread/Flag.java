package ru.job4j.thread;

/**
 * Модель памяти Java
 *
 * Может возникнуть ситуация, что главная нить запишет новое значение переменной в кеш процессора,
 * а дополнительная нить будет продолжать читать переменную flag из регистра.
 *
 * Эта ситуация называется проблемой видимости (share visibility problem).
 * Чтобы ее решить, можно использовать синхронизацию. То есть при работе с флагом использовать эксклюзивный доступ.
 * Но в данном случае она избыточна, потому что флаг не привязан к предыдущему состоянию.
 *
 * В Java есть облегченный механизм синхронизации - volatile.
 * Его можно использовать только в том случае, когда общий ресурс не обновляется в зависимости от своего состояния.
 * Например, для инкремента его использовать нельзя.
 *
 * volаtile - это ключевое слово, которое используется для полей класса.
 * По сути это отдельная синхронизация операций чтения и записи.
 * Если поле класса обозначено volatile,то чтение и запись переменной будет происходить только из RAM памяти процессора
 *
 * @author Alex_life
 * @version 1.0
 * @since 13.09.2022
 */
public class Flag {
    /* private static boolean flag = true; */
    private static volatile boolean flag = true;

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(
                () -> {
                    while (flag) {
                        System.out.println(Thread.currentThread().getName());
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        thread.start();
        Thread.sleep(1000);
        flag = false;
        thread.join();
    }
}
