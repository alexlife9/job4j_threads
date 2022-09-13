package ru.job4j.thread;

/**
 * Модель памяти Java
 *
 * Проблема идиомы Double Checked Lock заключается в модели памяти Java, точнее в порядке создания объектов.
 * Можно условно представить этот порядок следующими этапами [2, 3]:
 * Пусть мы создаем нового студента: Student s = new Student(), тогда:
 * 1) local_ptr = malloc(sizeof(Student)) - выделение памяти под сам объект;
 * 2) s = local_ptr - инициализация указателя;
 * 3) Student::ctor(s); - конструирование объекта (инициализация полей);
 * Таким образом, между вторым и третьим этапом возможна ситуация,
 * при которой другой поток может получить и начать использовать (на основании условия, что указатель не нулевой)
 * не полностью сконструированный объект.
 *
 * На самом деле, эта проблема была частично решена в JDK 1.5 [5],
 * однако авторы JSR-133 [5] рекомендуют использовать voloatile для Double Cheсked Lock.
 *
 * @author Alex_life
 * @version 1.0
 * @since 13.09.2022
 */
public final class DCLSingleton {

    private static volatile DCLSingleton inst;

    public static DCLSingleton instOf() {
        if (inst == null) {
            synchronized (DCLSingleton.class) {
                if (inst == null) {
                    inst = new DCLSingleton();
                }
            }
        }
        return inst;
    }

    private DCLSingleton() {
    }

}
