package concurrency;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Locks {
    public static void main(String[] args) {

        Lock lock = new ReentrantLock();
        lock.lock();
        
        lock.unlock();
    }    
}

