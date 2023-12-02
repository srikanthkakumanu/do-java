package concurrency;

import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * This example demonstrates classic Producer/Consumer problem in concurrency
 * by using Condition and ReentrantLock.
 */
public class ProducerConsumerLocks {

    private Stack<String> stack = new Stack<>();
    private static final int MAX_CAPACITY = 5;
    private Lock lock = new ReentrantLock();
    private Condition added = lock.newCondition();
    private Condition removed = lock.newCondition();

    public static void main(String[] args) {

        int numOfThreads = 2;
        ProducerConsumerLocks object = new ProducerConsumerLocks();
        ExecutorService service = Executors.newFixedThreadPool(numOfThreads);
        
        service.execute(() -> {
            IntStream.range(1, 11).forEach(item -> {
                try {
                    object.produce(Integer.toString(item));
                } catch (InterruptedException e) {
                    System.err.printf("Interrupted Exception thrown while pushing/producing an item: %s", e.toString());
                }
            });
        });
        
        service.execute(() -> {
            IntStream.range(1, 11).forEach( item -> {
                try {
                    object.consume();
                } catch (InterruptedException e) {
                    System.err.printf("Interrupted Exception thrown while consuming/removing an item: %s", e.toString());
                }
            });
        });
        service.shutdown();
    }
    /**
     * Producer which produces an item into stack.
     * @param item
     * @throws InterruptedException
     */
    private void produce(String item) throws InterruptedException {
        try {
            lock.lock();
            while(stack.size() == MAX_CAPACITY) {
                System.out.printf("Thread: %s wait as the stack is full.\n", Thread.currentThread().getName());
                removed.await();
            }
            System.out.printf("PRODUCING an item: %s\n", item);
            stack.push(item);
        } finally { 
            added.signal();
            lock.unlock(); }
    }

    /**
     * Consumer which removes/consumes the item.
     * @throws InterruptedException
     */
    private void consume() throws InterruptedException {
        try {
            lock.lock();
            while(stack.size() == 0) {
                System.out.printf("Thread: %s wait as the stack is empty.\n", Thread.currentThread().getName());
                added.await();
            }
            System.out.printf("Thread: %s consumed/removed an item: %s \n", Thread.currentThread().getName(), stack.pop());
        } finally { 
            removed.signal();
            lock.unlock(); 
        }
    }
}
