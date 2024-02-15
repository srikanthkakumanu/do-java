package concurrency.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface AsyncCompletableFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 1st example
        // method chaining
        CompletableFuture
                .supplyAsync(AsyncCompletableFuture::greet)
                .thenApply(AsyncCompletableFuture::decorate)
                .thenAccept(AsyncCompletableFuture::print);

        // Or, can also use join() optionally like below.
        CompletableFuture
                .supplyAsync(AsyncCompletableFuture::greet)
                .thenApply(AsyncCompletableFuture::decorate)
                .thenAccept(AsyncCompletableFuture::print)
                .join();

        // 2nd example
        // Future Vs. CompletableFuture
        // Future<T> with Runnable
        System.out.println("\n----------Future-Vs.CompletableFuture----------\n");
        var executorService = ForkJoinPool.commonPool();
        Future<?> futureRunnable = executorService.submit(
                () -> System.out.println("not returning a value"));

        // Future<T> with Callable
        Future<String> futureCallable = executorService.submit(
                () -> "Hello, Async World!");
        System.out.println(futureCallable.get());

        // CompletableFuture<T>
        // CompletableFuture with Runnable
        CompletableFuture<Void> completableFutureRunnable =
                CompletableFuture.runAsync(
                        () -> System.out.println("not returning a value"));

        // CompletableFuture with Supplier
        CompletableFuture<String> completableFutureSupplier =
                CompletableFuture.supplyAsync(() -> "Hello, Async World!");
        System.out.println(completableFutureSupplier.get());

        // combine
        System.out.println("\n----------combine----------\n");
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> 42);
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> 43);
        // The task consuming the combined results of the previous stage.
        BiFunction<Integer, Integer, CompletableFuture<Integer>> task =
                (lhs, rhs) -> CompletableFuture.supplyAsync(() -> lhs + rhs);

        // The return value of a task is wrapped into another stage by thenCombine,
        // resulting in an unwanted CompletionStage<CompletionStage<Integer>>.
        // The thenCompose call with Function.identity() unwraps the nested stage,
        // and the pipeline is a CompletionStage<Integer> again.
        CompletableFuture<Integer> combined =
                future1.thenCombine(future2, task)
                        .thenCompose(Function.identity());
        var result = combined.get();
        System.out.println(result);

        System.out.println("\n----------Either and reject operations----------\n");
        CompletableFuture<String> notFailed =
                CompletableFuture.supplyAsync(() -> "Success!");

        CompletableFuture<String> failed =
                CompletableFuture.supplyAsync(() -> { throw new RuntimeException(); });

        // NO OUTPUT BECAUSE THE PREVIOUS STAGE FAILED
        var rejected = failed.acceptEither(notFailed, System.out::println);

        // OUTPUT BECAUSE THE PREVIOUS STAGE COMPLETED NORMALLY
        var resolved = notFailed.acceptEither(failed, System.out::println);



    }

    static String greet() { return "Happy Diwali!"; }
    static String decorate(String input) { return "**%s**".formatted(input); }
    static void print(String message) { System.out.println(message); }
}

