package ru.job4j.pool;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * CompletableFuture
 *
 * Синхронность – это когда действия выполняются последовательно. К примеру, вы сталкивались со словом synchronized,
 * что значит, в данном месте потоки выполняют последовательно, а не одновременно.
 * Асинхронность – это наоборот, когда действие выполняется отдельно от другого действия.
 *
 * асинхронная задача это та, что выполняется вне основного потока.
 * К тому же можно заметить, что асинхронность хорошо подходит, когда основной поток сильно загружен,
 * а нам нужно выполнить что-то отдельно. В этом случае удобно использовать асинхронность,
 * при этом не требуется управление потоками напрямую.
 *
 * Для написания асинхронного кода в Java существует замечательный класс CompletableFuture.
 * Данный класс имеет много методов. Мы рассмотрим только некоторые из них. Объяснение будет идти на бытовых примерах.
 *
 * Создание и получение результата из CompletableFuture
 * Можно создать объект типа CompletableFuture, который вернет результат и которой просто выполнит действие,
 * не возвращая результата. Для этой цели служат методы supplyAsync() и runAsync() соответственно.
 * Первый возвращает CompletableFuture<T>, второй CompletableFuture<Void>.
 * Стоит отметить, что создание предполагает и запуск. Оба этих метода запускают асинхронную задачу.
 *
 * Для получения результата, нужно использовать метод get().
 * Для объекта, созданного через supplyAsync(), этот метод вернет T; для объекта, созданного через runAsync() - null.
 * Этот метод является блокирующим, т.е. блокирует поток выполнения, в котором он будет вызван.
 *
 * Преимущества асинхронности:
 * Подводя итоги, хотелось бы сделать выводы о том, в чем преимущество асинхронности:
 * 1. "inline многопоточность". Для написания асинхронного кода вам практически не нужно задумываться о потоках.
 * Достаточно создать асинхронную задачу.
 * 2. Удобно использовать для мелких относительно независимых задач. Под мелкими здесь подразумевается,
 * что асинхронные задачи должны исполняться быстрее главного потока, особенно при использовании CompletableFuture,
 * т.к. рано или поздно нужно будет вызывать метод get(), а он блокирующий.
 *
 * @author Alex_life
 * @version 1.0
 * @since 04.10.2022
 */
public class CompletableFutureEx {

    /**
     * Допустим вы очень занятой человек и часто берете работу на дом. Но дома тоже есть свои дела.
     * Например, сходить выбросить мусор. Вам некогда этим заниматься, но у вас есть сын, который может это сделать.
     * Вы сами работаете, а ему поручаете это дело. Это проявление асинхронности, т.к. вы сами работаете,
     * а тем временем ваш сын выбрасывает мусор. Сымитируем эту ситуацию.
     * 1) Пример runAsync() в методах iWork, goToTrash, runAsyncExample
     */
    private static void iWork() throws InterruptedException {
        int count = 0;
        while (count < 10) {
            System.out.println("Вы: Я работаю");
            TimeUnit.SECONDS.sleep(1);
            count++;
        }
    }

    public static CompletableFuture<Void> goToTrash() {
        return CompletableFuture.runAsync(
                () -> {
                    System.out.println("Сын: Мам/Пам, я пошел выносить мусор");
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Сын: Мам/Пап, я вернулся!");
                }
        );
    }

    public static void runAsyncExample() throws Exception {
        CompletableFuture<Void> gtt = goToTrash();
        iWork();
    }

    /**
     * 2) Пример supplyAsync()
     * Возьмем теперь другой пример. Вы поручили сыну купить молоко. Вот как это будет выглядеть.
     */
    public static CompletableFuture<String> buyProduct(String product) {
        return CompletableFuture.supplyAsync(
                () -> {
                    System.out.println("Сын: Мам/Пам, я пошел в магазин");
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Сын: Мам/Пап, я купил " + product);
                    return product;
                }
        );
    }

    public static void supplyAsyncExample() throws Exception {
        CompletableFuture<String> bm = buyProduct("Молоко");
        iWork();
        System.out.println("Куплено: " + bm.get());
    }

    /**
     * thenApply(), thenAccept(), thenRun()
     * Данные методы позволяют прописать методы-колбэки (callback).
     * Callback-метод – это метод, который будет вызван после выполнения асинхронной задачи.
     * Обратите внимание, что все эти методы также возвращают CompletableFuture.
     *
     * 3) Пример thenRun()
     * Этот метод ничего не возвращает, а позволяет выполнить задачу типа Runnable после выполнения асинхронной задачи.
     * Допишем первый пример, чтобы сын шел мыть руки
     */
    public static void thenRunExample() throws Exception {
        CompletableFuture<Void> gtt = goToTrash();
        gtt.thenRun(() -> {
            int count = 0;
            while (count < 3) {
                System.out.println("Сын: я мою руки");
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count++;
            }
            System.out.println("Сын: Я помыл руки");
        });
        iWork();
    }

    /**
     * 4) Пример thenAccept()
     * Допустим вы не хотите запускать отдельную задачу, а хотите, чтобы просто было выполнено какое-то действие.
     * В отличие от thenRun(), этот метод имеет доступ к результату CompletableFuture.
     * Допишем второй пример, чтобы сын убрал молоко в холодильник.
     */
    public static void thenAcceptExample() throws Exception {
        CompletableFuture<String> bm = buyProduct("Молоко");
        bm.thenAccept((product) -> System.out.println("Сын: Я убрал " + product + " в холодильник "));
        iWork();
        System.out.println("Куплено: " + bm.get());
    }
    /**
     * 5) Пример thenApply()
     * Этот метод принимает Function. Также имеет доступ к результату. Как раз благодаря этому, мы можем произвести
     * преобразование полученного результата. Допишем второй пример. Например, вы хотите, чтобы после того, как сын
     * принес молоко, налил вам его в кружку. Однако результат преобразования станет доступным только при вызове get()
     */
    public static void thenApplyExample() throws Exception {
        CompletableFuture<String> bm = buyProduct("Молоко")
                .thenApply((product) -> "Сын: я налил тебе в кружку " + product + ". Держи.");
        iWork();
        System.out.println(bm.get());
    }

    /**
     * thenCompose(), thenCombine()
     * Для совмещения двух объектов ComputableFuture можно использовать thenCompose(), thenCombine().
     *
     * 6) Пример thenCompose()
     * Данный метод используется, если действия зависимы. Т.е. сначала должно выполниться одно, а только потом другое.
     * Например, вам принципиально, чтобы сын сначала выбросил мусор, а только потом сходил за молоком.
     */
    public static void thenComposeExample() throws Exception {
        CompletableFuture<String> result = goToTrash().thenCompose(a -> buyProduct("Milk"));
        result.get(); /* wait calculations */
    }

    /**
     * 7) Пример thenCombine()
     * Данный метод используется, если действия могут быть выполнены независимо друг от друга.
     * Причем в качестве второго аргумента, нужно передавать BiFunction – функцию, которая преобразует результаты двух
     * задач во что-то одно. Например, первого сына вы посылаете выбросить мусор, а второго сходить за молоком.
     */
    public static void thenCombineExample() throws Exception {
        CompletableFuture<String> result = buyProduct("Молоко")
                .thenCombine(buyProduct("Хлеб"), (r1, r2) -> "Куплены " + r1 + " и " + r2);
        iWork();
        System.out.println(result.get());
    }

    /**
     * allOf() и anyOf()
     * В случае, если задач много, то совместить их можно с помощью методов allOf() и anyOf().
     *
     * 8) Пример allOf()
     * Это метод возвращает ComputableFuture<Void>, при этом обеспечивает выполнение всех задач.
     * Например, вы зовете всех членов семью к столу. Надо дождаться пока все помоют руки.
     */
    public static CompletableFuture<Void> washHands(String name) {
        return CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + ", моет руки");
        });
    }

    public static void allOfExample() throws Exception {
        CompletableFuture<Void> all = CompletableFuture.allOf(
                washHands("Папа"), washHands("Мама"),
                washHands("Ваня"), washHands("Боря")
        );
        TimeUnit.SECONDS.sleep(3);
    }

    /**
     * 9) Пример anyOf()
     * Этот метод возвращает ComputableFuture<Object>. Результатом будет первая выполненная задача. На том же примере
     * мы можем, например, узнать, кто сейчас моет руки. Результаты запуск от запуска будут различаться.
     */
    public static CompletableFuture<String> whoWashHands(String name) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return name + ", моет руки";
        });
    }

    public static void anyOfExample() throws Exception {
        CompletableFuture<Object> first = CompletableFuture.anyOf(
                whoWashHands("Папа"), whoWashHands("Мама"),
                whoWashHands("Ваня"), whoWashHands("Боря")
        );
        System.out.println("Кто сейчас моет руки?");
        TimeUnit.SECONDS.sleep(1);
        System.out.println(first.get());
    }

    /**
     * 10) пример async
     * Пусть нам нужно посчитать суммы элементов по диагоналям матрицы. Сколько диагоналей в матрице? 2 * N.
     * Каждая сумма может быть подсчитана независимо от другой. Реализуем эту логику.
     * Если выполнить этот код по сравнению с последовательной версией кода, то можно увидеть,
     * что он работает от 1.5 до 2 раз быстрее. Стоит обратить внимание, что в цикле мы запускаем две задачи.
     * Одну с начала обхода, другую с конца. Так распределение идет более равномерно.
     */
    public static int[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        int n = matrix.length;
        int[] sums = new int[2 * n];
        Map<Integer, CompletableFuture<Integer>> futures = new HashMap<>();
        /* считаем сумму по главной диагонали */
        futures.put(0, getTask(matrix, 0, n - 1, n - 1));
        /* считаем суммы по побочным диагоналям */
        for (int k = 1; k <= n; k++) {
            futures.put(k, getTask(matrix, 0, k - 1,  k - 1));
            if (k < n) {
                futures.put(2 * n - k, getTask(matrix, n - k, n - 1, n - 1));
            }
        }
        for (Integer key : futures.keySet()) {
            sums[key] = futures.get(key).get();
        }
        return sums;
    }

    public static CompletableFuture<Integer> getTask(int[][] data, int startRow, int endRow, int startCol) {
        return CompletableFuture.supplyAsync(() -> {
            int sum = 0;
            int col = startCol;
            for (int i = startRow; i <= endRow; i++) {
                sum += data[i][col];
                col--;
            }
            return sum;
        });
    }
}
