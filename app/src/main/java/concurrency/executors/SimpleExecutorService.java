package concurrency.executors;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Future;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class SimpleExecutorService {
    public static void main(String[] args) {
        // Different examples
       // simpleExecutorSubmit();
       //  executorServiceSubmitWithCustomThreadPool();
        // runnableSubmit();
        // callableFutureGet();
        // callableInvokeAny();
        // callableInvokeAll();
        // waysOfServiceShutdown();
        // cancelRunningTaskFuture();
    }

    /**
     * Fixed thread pool example which has core pool and max pool size as 3 only.
     */
    private static void simpleExecutorSubmit() {
        ExecutorService service = Executors.newFixedThreadPool(2);
        service.execute(newRunnable("Task-1"));
        service.execute(newRunnable("Task-2"));
        service.execute(newRunnable("Task-3"));
        service.shutdown();
    }

    /**
     * Fixed thread pool example which has different values for core and max pool sizes
     * and blocking queue.
     */
    private static void executorServiceSubmitWithCustomThreadPool() {
        int corePoolSize = 10;
        int maxPoolSize = 20;
        long keepAliveTime = 3000; // millis
        
        ExecutorService service = new ThreadPoolExecutor(
                                                        corePoolSize, 
                                                        maxPoolSize, 
                                                        keepAliveTime,
                                                        TimeUnit.MILLISECONDS, 
                                                        new ArrayBlockingQueue<>(128));
        
        service.submit(newRunnable("Task1"));
        service.submit(newRunnable("Task2"));
        service.submit(newRunnable("Task3"));
        service.shutdown();
    }

    /**
     * 
     */
    private static void runnableSubmit() {
        
        ExecutorService service = Executors.newFixedThreadPool(1);
        // The above statement has the same effect similar to singleThreadExecutor
        // like below
        // ExecutorService service = Executors.newSingleThreadExecutor();

        Future future = service.submit(newRunnable("Task-1"));
        System.out.println(future.isDone());
        try {
            // In case of Runnable implementation, future.get() method always
            // return NULL as Runnable.run() method does not return anything.
            future.get();
        } catch(InterruptedException e) { e.printStackTrace(); }
        catch(ExecutionException e) { e.printStackTrace(); }

        System.out.println(future.isDone());
        service.shutdown();
    }

    private static void callableFutureGet() {
        ExecutorService service = Executors.newFixedThreadPool(1);
        var future = service.submit(newCallable("Task1"));
        System.out.println(future.isDone());
        try {
            System.out.println(future.get());
        } catch(InterruptedException e) { e.printStackTrace(); }
        catch(ExecutionException e) { e.printStackTrace(); }

        System.out.println(future.isDone());
        service.shutdown();

    }
    /** 
     * invokeAny() - makes a call any one of the callables in the given list
     * and once one of these callables fully executed, any other waiting
     * tasks will be cancelled.
    */
    private static void callableInvokeAny() {
        ExecutorService service = Executors.newFixedThreadPool(1);
        List<Callable<String>> callables = Collections.synchronizedList(new ArrayList<>());
        callables.add(newCallable("Task1"));
        callables.add(newCallable("Task2"));
        callables.add(newCallable("Task3"));
        try {
            String result = service.invokeAny(callables);
            System.out.println(result);
        } catch(InterruptedException e) { e.printStackTrace(); }
        catch(ExecutionException e) { e.printStackTrace(); }
        finally { service.shutdown(); }
    }

        /** 
     * invokeAll - makes a call to all of the tasks.
    */
    private static void callableInvokeAll() {
        ExecutorService service = Executors.newFixedThreadPool(1);
        List<Callable<String>> callables = Collections.synchronizedList(new ArrayList<>());
        callables.add(newCallable("Task1"));
        callables.add(newCallable("Task2"));
        callables.add(newCallable("Task3"));
        try {
            List<Future<String>> results = service.invokeAll(callables);
            results.forEach(result -> {
                try {
                    System.out.println(result.get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            });
            
        } catch(InterruptedException e) { e.printStackTrace(); }
        finally { service.shutdown(); }
    }


    private static void waysOfServiceShutdown() {
        ExecutorService service = Executors.newFixedThreadPool(10);
        // shutdown - waits until all tasks are completed
        //service.shutdown();
        // ShutsdownNow - shutsdown immediately but returns list of unfinished runnable tasks
        // runnables - list of unfinished runnables
        // List<Runnable> runnables = service.shutdownNow(); 
        // Below example for shutdownNow(), it is used to allocate time limit
        // for last pending task to complete
        try {
            service.awaitTermination(3000L, TimeUnit.MILLISECONDS);
        } catch(InterruptedException e) { e.printStackTrace(); }
    }

    private static void cancelRunningTaskFuture() {
        ExecutorService service = Executors.newFixedThreadPool(10);
        Future future = service.submit(newCallable("Task1"));
        System.out.println(future.isDone());
        boolean mayInterrupt = true;
        boolean cancelSucceeded = future.cancel(mayInterrupt);
        System.out.println(cancelSucceeded);
        try {
            String result = (String) future.get();
            System.out.println(result);
        } catch(InterruptedException | ExecutionException | CancellationException e) { 
            String msg = "Cannot call Future.get() since task was cancelled";
            System.out.println(msg);
        }
        System.out.println(future.isDone());
        System.out.println(future.isCancelled());
        service.shutdown();
    }

    private static Runnable newRunnable(String task) {
        return (() -> System.out.printf("Thread: %s, task - %s\n", Thread.currentThread().getName(), task));
    }

    private static Callable newCallable(String task) {
        // return new Callable() {
        //     public Object call() throws Exception {
        //         return "Thread: %s, task - %s\n".formatted(Thread.currentThread().getName(), task);
        //     }
        // };

        return (() -> {return "Thread: %s, task - %s\n".formatted(Thread.currentThread().getName(), task);});
    }
}
