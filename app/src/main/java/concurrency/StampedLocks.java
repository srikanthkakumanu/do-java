package concurrency;

import java.util.concurrent.locks.StampedLock;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class StampedLocks {
    public static void main(String[] args) throws InterruptedException {
        final int threadCount = 4;
        final ExecutorService service = Executors.newFixedThreadPool(threadCount);
        Counter object = new Counter(0);

        Runnable writeTask = () -> {
            object.increment();
        };

        Runnable readTask = () -> {
            object.get();
        };

        Runnable readOptimisticTask = () -> {
            try {
                object.getOptimistic();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        service.submit(writeTask);
        service.submit(writeTask);
        service.submit(readTask);
        service.submit(readOptimisticTask);

        service.shutdown();
    }
}

class Counter {

    private long value = 0;
    private StampedLock slock = new StampedLock();

    public Counter(long value) { this.value = value; }

    public void increment() {
        long stamp = slock.writeLock();
        try { 
            value++; 
            System.out.printf("Thread: %s, incremented value: %d \n", Thread.currentThread().getName(), value);
        } 
        finally { slock.unlockWrite(stamp); }
    }

    public long get() {
        long stamp = slock.readLock();
        try { 
            System.out.printf("Thread: %s, Current value: %d \n", Thread.currentThread().getName(), value);
            return value; 
        } 
        finally { slock.unlockRead(stamp); }
    }

    /**
     Optimistic Read
     @return
     */
    public long getOptimistic() throws InterruptedException {
        long stamp = slock.tryOptimisticRead();
        long value = this.value;

        if(!slock.validate(stamp)) {
            stamp = slock.readLock();
            try { 
                System.out.printf("(Optimistic Read) Thread: %s, Current value: %d \n", Thread.currentThread().getName(), value);
                TimeUnit.MILLISECONDS.sleep(1000);
                return value; 
            } 
            finally { slock.unlock(stamp); }
        }
        return value;
    }
}
