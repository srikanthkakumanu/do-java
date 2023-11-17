package concurrency;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class ThreadLocalExample2 {

    public static void main(String[] args) throws InterruptedException {

        ThreadLocalRunner tr = new ThreadLocalRunner();

        Thread t1 = new Thread(tr);
        Thread t2 = new Thread(tr);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}

class ThreadLocalRunner implements Runnable {

    private ThreadLocal<Integer> tl = ThreadLocal.withInitial(() -> Integer.valueOf(0));

    public void run() {
        tl.set((int) (Math.random() * 100));
        try {
            TimeUnit.MILLISECONDS.sleep(2000);
        } catch (InterruptedException e) {
            System.out.printf("Thread: %s interrupted from sleep, exception thrown \n",
                    Thread.currentThread().getName());
        }
        System.out.printf("Thread: %s, value: %d \n", Thread.currentThread().getName(), tl.get());
    }
}