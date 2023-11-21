package concurrency;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLocks {
    public static void main(String[] args) {

        

        Thread t1 = new Thread("T1") {
            public void run() {
                SimpleReentrantReadWriteLock srl = new SimpleReentrantReadWriteLock(1);
                long temp = srl.incrementAndGet();
                System.out.printf("Thread: %s, Value: %d\n", getName(), temp);
            };
        };
        Thread t2 = new Thread("T2") {
            public void run() {
                SimpleReentrantReadWriteLock srl = new SimpleReentrantReadWriteLock(1);
                long temp = srl.incrementAndGet();
                System.out.printf("Thread: %s, Value: %d\n", getName(), temp);
            };
        }; 
        t1.start(); t2.start();
    } 
}

class SimpleReentrantReadWriteLock {
    
    private long value;

    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock rlock = rwLock.readLock();
    private final Lock wlock = rwLock.writeLock();

    public SimpleReentrantReadWriteLock(long value) { this.value = value; }

    public long get() {
        try {
            rlock.lock();
            return value;
        } finally {
            rlock.unlock();
        }
    }

    public long decrementAndGet() {
        try {
            wlock.lock();
            return --value;
        } finally {
            wlock.unlock();
        }
    }

    public long getAndIncrement() {
        long temp = 0;
        try {
            wlock.lock();
            temp = value;
            value++;
            return temp;
        } finally {
            wlock.unlock();
        }
    }
    /**
     * This method demonstrates downgrading the lock from write to read. 
     * But read to write upgradation is not possible and not recommended.
     */
    public long incrementAndGet() {
        long value = 0;
        Lock lock = rwLock.writeLock();
        lock.lock();
        
        try {
            this.value++; // write lock held already
            final Lock readLock = rwLock.readLock();
            readLock.lock(); // lock downgrading to read
            try {
                lock.unlock(); // unlock the write lock
                value = this.value;
            } finally { lock = readLock; } // assign read lock to lock object
        } finally { lock.unlock(); } // unlock the read lock now

        return value;
    }

    public long getAndDecrement() {
        try {
            wlock.lock();
            return value--;
        } finally {
            wlock.unlock();
        }
    }
}