package concurrency;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public interface AsyncFuture {
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
    }

    static int heavy() { 
        try { TimeUnit.MILLISECONDS.sleep(500); }
        catch(InterruptedException e) { e.printStackTrace(); }
        return 2;
    }
}
