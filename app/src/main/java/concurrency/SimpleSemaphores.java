package concurrency;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class SimpleSemaphores {
    public static void main(String[] args) {
        Semaphore sp = new Semaphore(1);
        new Thread(new Increment("Incrementor", sp)).start();
        new Thread(new Decrement("Decrementor", sp)).start();
    }
}

// Shared resource
class Shared {
    static int count = 0;
}

class Increment implements Runnable {
    private String name;
    private Semaphore sp;

    Increment(String name, Semaphore sp) {
        this.name = name;
        this.sp = sp;
    }

    public void run() {
        System.out.printf("Starting %s\n", name);

        try {
            System.out.printf("%s is waiting for a permit.\n", name);
            sp.acquire();
            System.out.printf("%s gets permit.\n", name);
            IntStream.rangeClosed(1, 5).forEach(v -> {
                Shared.count++;
                System.out.printf("%s:%d\n", name, Shared.count);
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch(InterruptedException e) {
            System.out.println(e);
        }
        System.out.printf("%s releases the permit.\n", name);
        sp.release();
    }
}

class Decrement implements Runnable {
    private String name;
    private Semaphore sp;

    Decrement(String name, Semaphore sp) {
        this.name = name;
        this.sp = sp;
    }

    public void run() {
        System.out.printf("Starting %s\n", name);
        try {
            System.out.printf("%s is waiting for permit.\n", name);
            sp.acquire();
            System.out.printf("%s gets a permit.\n", name);

            IntStream.rangeClosed(1, 5).forEach(v -> {
                Shared.count--;
                System.out.printf("%s:%d\n", name, Shared.count);
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        System.out.printf("%s releases the permit.\n", name);
        sp.release();
    }
}