package concurrency;

import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class ConditionWithReentrantLock {

    private Stack<String> stack = new Stack<>();
    private static final int MAX_CAPACITY = 5;
    private Lock lock = new ReentrantLock();
    private Condition emptyCondition = lock.newCondition();
    private Condition fullCondition = lock.newCondition();

    public static void main(String[] args) {

        int numOfThreads = 2;
        ConditionWithReentrantLock object = new ConditionWithReentrantLock();
        ExecutorService service = Executors.newFixedThreadPool(numOfThreads);
        
        service.execute(() -> {
            IntStream.range(1, 11).forEach(item -> {
                try {
                    object.push(Integer.toString(item));
                } catch (InterruptedException e) {
                    System.err.printf("Interrupted Exception thrown while pushing an item: %s", e.toString());
                }
            });
        });
        
        service.execute(() -> {
            IntStream.range(1, 11).forEach( item -> {
                try {
                    object.pop();
                } catch (InterruptedException e) {
                    System.err.printf("Interrupted Exception thrown while popping an item: %s", e.toString());
                }
            });
        });
        service.shutdown();
    }
    private void push(String item) throws InterruptedException {
        try {
            lock.lock();
            if(stack.size() == MAX_CAPACITY) {
                System.out.printf("Thread: %s wait on stack full.\n", Thread.currentThread().getName());
                fullCondition.await();
            }
            System.out.printf("PUSHing an item: %s\n", item);
            stack.push(item);
            emptyCondition.signalAll(); // calling signalAll support fairness to all the threads that are waiting.
        } finally { lock.unlock(); }
    }

    private void pop() throws InterruptedException {
        try {
            lock.lock();
            if(stack.size() == 0) {
                System.out.printf("Thread: %s wait on stack empty.\n", Thread.currentThread().getName());
                emptyCondition.await();
            }
            System.out.printf("item: %s is POPped out.\n", stack.pop());
        } finally {
            fullCondition.signalAll(); // calling signalAll support fairness to all the threads that are waiting.
            lock.unlock();
        }
    }
}
