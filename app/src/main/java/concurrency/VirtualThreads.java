package concurrency;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class VirtualThreads {
    public static void main(String[] args) throws InterruptedException, CancellationException, ExecutionException {
        // standard();
        virtual();
    }

    private static void virtual() {
        try (ExecutorService service = Executors.newVirtualThreadPerTaskExecutor()) {

            List<Task> tasks = new ArrayList<>();
            IntStream.rangeClosed(0, 1_00).forEach(number -> tasks.add(new Task(number)));
            long time = System.currentTimeMillis();
            List<Future<Integer>> futures = service.invokeAll(tasks);

            long sum = 0;
            sum = futures.stream().mapToLong(result -> {
                try {
                    return result.get();
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }).sum();
            time = System.currentTimeMillis() - time;

            System.out.println("sum = " + sum + "; time = " + time + " ms");
        } catch (InterruptedException | CancellationException e) {
            throw new RuntimeException(e);
        }
    }

    private static void standard() {
        try (ExecutorService service = Executors.newFixedThreadPool(100)) {

            List<Task> tasks = new ArrayList<>();
            IntStream.rangeClosed(0, 1_00).forEach(number -> tasks.add(new Task(number)));
            long time = System.currentTimeMillis();
            List<Future<Integer>> futures = service.invokeAll(tasks);

            long sum = 0;
            sum = futures.stream().mapToLong(result -> {
                try {
                    return result.get();
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }).sum();
            time = System.currentTimeMillis() - time;

            System.out.println("sum = " + sum + "; time = " + time + " ms");
        } catch (InterruptedException | CancellationException e) {
            throw new RuntimeException(e);
        }
    }
}

class Task implements Callable<Integer> {
    private final int number;

    public Task (int number) { this.number = number; }

    @Override
    public Integer call () throws Exception {
        System.out.printf ("Thread %s - Task %d waiting...%n",
                Thread.currentThread().getName(), number);

        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            System.out.printf ("Thread %s - Task %d canceled.%n",
                    Thread.currentThread().getName(), number);
            return -1;
        }

        System.out.printf ("Thread %s - Task %d finished.%n",
                Thread.currentThread().getName(), number);

        return ThreadLocalRandom.current().nextInt(100);
    }
}