package concurrency;

import java.util.Random;
import java.util.stream.IntStream;

public class ThreadGroups {
    public static void main(String[] args) {
        Runnable r = () -> {
            int result;
            Random random = new Random(Thread.currentThread().getId());
            while(true) {
                result = 1000 / ((int) (random.nextDouble() * 1000000000));
                if(Thread.currentThread().isInterrupted()) {
                    System.out.printf("Thread: %d is interrupted. \n", Thread.currentThread().getId());
                    return;
                }
            }
        };

        int noOfThreads = Runtime.getRuntime().availableProcessors();
        BasicThreadGroup btg = new BasicThreadGroup("BTG");
        IntStream.range(0, noOfThreads).forEach(i -> {
            Thread t = new Thread(btg, r);
            t.start();
        });

        System.out.printf("No. Of Active Threads: %d \n", btg.activeCount());
        System.out.println("Thread Group Information");
        btg.list();
    }
}

class BasicThreadGroup extends ThreadGroup {
    public BasicThreadGroup(String name) { super(name); }

    public void uncaughtException(Thread t, Throwable e) {
        System.out.printf("Thread: %s thrown the exception: %s \n", t.getId(), e.getMessage());
        System.out.println("Terminating remaining threads");
        interrupt();
    }
}

