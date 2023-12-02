package concurrency;

import java.util.concurrent.CompletableFuture;

public interface AsyncCompletableFuture {
    public static void main(String[] args) {
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

    }

    static String greet() { return "Happy Diwali!"; }
    static String decorate(String input) { return "**%s**".formatted(input); }
    static void print(String message) { System.out.println(message); }
}

