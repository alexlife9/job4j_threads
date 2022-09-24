package ru.job4j.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Неблокирующий кеш
 *
 * В кеше должна быть возможность проверять актуальность данных.Для этого в модели данных используется поле int version.
 * Это поле должно увеличиваться на единицу каждый раз, когда модель изменили, то есть вызвали метод update.
 * Даже если все поля остались не измененными поле version должно увеличиться на 1.
 *
 * Например. Два пользователя прочитали объект task ID = 1.
 * Первый пользователь изменил поле имя и второй сделал то же самое.
 * Теперь пользователи сохраняют изменения. Для этого они вызывают метод update.
 * В этом случае возникает ситуация, которая называется "последний выиграл".
 * То есть в кеше сохраняться данные только последнего пользователя.
 *
 * @author Alex_life
 * @version 1.0
 * @since 19.09.2022
 */
public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    /**
     * Эта реализация метода add не потокобезопасная. Ее использовать нельзя, потому что хоть
     * сама ConcurrentHashMap потокобезопасная, но последовательные вызовы методов нет. Это не атомарные операции.
     * Для решения этих задач нужно использовать CAS методы.
     */
/*    public boolean add(Base model) {
        if (memory.containsKey(model.getId())) {
            return false;
        }
        memory.put(model.getId(), model);
        return true;
    }*/

    /* Метод putIfAbsent выполняет методы сравнения и вставки, только делает это атомарно */
    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    /**
     * В кеше нужно перед обновлением данных проверить поле version. Если version у модели и в кеше одинаковы,
     * то можно обновить. Если нет, то выбросить OptimisticException.
     * Перед обновлением данных необходимо проверять текущую версию и ту что обновляем и увеличивать
     * на единицу каждый раз, когда произошло обновление.
     * ConcurentHashMap имеет метод computeIfPresent.
     * Этот метод принимает функцию BiFunction и позволяет атомарно обновить нужную ячейку.
     * В нашем случае, если version отличаются нужно выкинуть исключение.
     */
    public boolean update(Base model) {
        Base base = memory.computeIfPresent(model.getId(), (i, b) -> {
            if (model.getVersion() != b.getVersion()) {
                throw new OptimisticException("Модель была уже обновлена");
            }
            return new Base(model.getId(), b.getVersion() + 1);
        });
        return base != null;
    }

    /* метод delete работает по аналогии с методом add */
    public void delete(Base model) {
        memory.remove(model.getId(), model);
    }

    public Base get(int key) {
        return memory.get(key);
    }
}
