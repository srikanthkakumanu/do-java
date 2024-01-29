package concurrency;

import java.util.concurrent.Semaphore;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.stream.IntStream;
import java.util.concurrent.TimeUnit;

/**
 Semaphores are two types:
 - Counting Semaphores - Below LoginQueue solution demonstrates it.
 - Binary Semaphores   - Mutex (Mutual Exclusion) acts similar to Binary semaphore.
 */
public class Semaphores {
    public static void main(String[] args) throws InterruptedException {
        tryCountingSemaphore();
        tryBinarySemaphore();
    }

    private static void tryCountingSemaphore() {
        Semaphores sp = new Semaphores();
        sp.givenLoginQueue_whenReachLimit_thenBlocked();
        sp.givenLoginQueue_whenLogout_thenSlotsAvailable();
    }

    private static void tryBinarySemaphore() throws InterruptedException {
        Semaphores sp = new Semaphores();
        sp.whenMutexAndMultipleThreads_thenBlocked();
        sp.givenMutexAndMultipleThreads_ThenDelay_thenCorrectCount();
    }

    /**
     When all threads try to access the counter at once, they will be blocked in a queue.
     @throws InterruptedException
     */
    public void whenMutexAndMultipleThreads_thenBlocked() throws InterruptedException {
        int count = 5;
        ExecutorService service = Executors.newFixedThreadPool(count);
        MutexCounter counter = new MutexCounter();
        IntStream.range(0, count).forEach(user -> service.execute(() -> {
          try { counter.increase(); } 
          catch(InterruptedException e) { e.printStackTrace(); }
        }));
        service.shutdown();

        System.out.println("Has Queued Threads? - " + counter.hasQueuedThreads());
    }

    /**
     When we wait, all threads will access the counter and no threads left in the queue.
     @throws InterruptedException
     */
    public void givenMutexAndMultipleThreads_ThenDelay_thenCorrectCount() throws InterruptedException {
        int count = 5;
        ExecutorService service = Executors.newFixedThreadPool(count);
        MutexCounter counter = new MutexCounter();
        IntStream.range(0, count)
                .forEach(user -> service.execute(() -> {
                    try { counter.increase(); }
                    catch (InterruptedException e) { e.printStackTrace(); }
                }));
        service.shutdown();

        System.out.println("Has Queued Threads? - " + counter.hasQueuedThreads());
        TimeUnit.MILLISECONDS.sleep(5000);
        System.out.println("Has Queued Threads? - " + counter.hasQueuedThreads());
        System.out.printf("Total Count: %d \n", counter.getCount());
    }

    private void givenLoginQueue_whenReachLimit_thenBlocked() {
        int slots = 10;
        ExecutorService service = Executors.newFixedThreadPool(slots);
        LoginQueue queue = new LoginQueue(slots);
        IntStream.range(0, slots).forEach(user -> service.execute(queue::tryLogin));
        service.shutdown();
        System.out.printf("Available Slots: %d\n", queue.available());
        System.out.println("Can Login? - " + queue.tryLogin());
    }

    private void givenLoginQueue_whenLogout_thenSlotsAvailable() {
        int slots = 10;
        ExecutorService service = Executors.newFixedThreadPool(slots);
        LoginQueue queue = new LoginQueue(slots);
        IntStream.range(0, slots)
                .forEach(user -> service.execute(queue::tryLogin));
        service.shutdown();
        System.out.printf("Available Slots: %d\n", queue.available());
        queue.logout();

        System.out.printf("Available Slots > 0: %s\n", (queue.available() > 0));
        System.out.println("Can Login? - " + queue.tryLogin());
    }
}

class LoginQueue {
    
    private Semaphore sp;

    public LoginQueue(int limit) { sp = new Semaphore(limit); }

    boolean tryLogin() { return sp.tryAcquire(); }

    void logout() { sp.release(); }

    int available() { return sp.availablePermits(); }
}

class MutexCounter {
    
    private Semaphore sp;
    private int count;

    MutexCounter() { 
        sp = new Semaphore(1);
        count = 0;
    }

    void increase() throws InterruptedException {
        sp.acquire();
        this.count += 1;
        TimeUnit.MILLISECONDS.sleep(1000);
        sp.release();
    }

    int getCount() { return this.count; }

    boolean hasQueuedThreads() { return sp.hasQueuedThreads(); }
}