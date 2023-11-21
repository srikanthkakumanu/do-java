package concurrency;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLocks {
    public static void main(String[] args) {
        reentrantLock();
    }    

    public static void reentrantLock() {
        CounterLock counterLock = new CounterLock();
        Runnable r = () -> {
            counterLock.inc();
            System.out.println(counterLock.getCount());
        };

        Thread t1 = new Thread(r, "T1");
        Thread t2 = new Thread(r, "T2");
        Thread t3 = new Thread(r, "T3");
        
        t1.start(); t2.start(); t3.start();
    }
}

class CounterLock {
    
    private long count = 0;
    // true - ensure lock fairness for waiting threads, which may prevent thread starvation to occur
    private Lock lock = new ReentrantLock(true);

    public void inc() {
        try {
            lock.lock();
            this.count++;
        } finally {
            lock.unlock();
        }
    }

    public long getCount() {
        try {
            lock.lock();
            return this.count;
        } finally {
            lock.unlock();
        }
    }
}

