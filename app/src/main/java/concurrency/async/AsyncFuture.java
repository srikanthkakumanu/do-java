package concurrency.async;

import java.util.concurrent.*;

public class AsyncFuture {
    public static void main(String... args) throws InterruptedException, ExecutionException {
        Future<String> future = Executors
                .newSingleThreadExecutor()
                .submit(() -> "Hello, Java"); // callable interface implementation
        System.out.println(future.isDone());
        System.out.println(future.get());

        // 2nd example
        var executor = Executors.newSingleThreadExecutor();
        var heavyFuture = executor.submit(AsyncFuture::heavy);
        System.out.println(heavyFuture.isDone());
        System.out.println(heavyFuture.get());
        executor.shutdown();

        // 3rd Example
        thirdExample();
    }

    static int heavy() { 
        try { TimeUnit.MILLISECONDS.sleep(500); }
        catch(InterruptedException e) {
            System.out.println(e.getMessage());
        }
        return 2;
    }

    static void thirdExample() throws ExecutionException, InterruptedException {
        var executor = Executors.newFixedThreadPool(10);

        Callable<Integer> expensiveTask = () -> {
            System.out.println("(task) start");
            TimeUnit.SECONDS.sleep(2);
            System.out.println("(task) done");
            return 42;
        };

        System.out.println("(main) before submitting the task");
        var future = executor.submit(expensiveTask);
        System.out.println("(main) after submitting the task");

        var answer = future.get();
        System.out.println("(main) after the blocking call future.get()");
        executor.shutdown();
    }
}
