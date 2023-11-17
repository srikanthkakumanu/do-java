package concurrency;

import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class ThreadLocalExample {

    private static final ThreadLocal<SimpleDateFormat> formatter = ThreadLocal
            .withInitial(() -> new SimpleDateFormat("yyyyMMdd HHmm"));

    private static Runnable r = () -> {
        System.out.printf("Thread: %s, default formatter: %s \n", Thread.currentThread().getName(),
                formatter.get().toPattern());
        try {
            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));
        } catch (InterruptedException exception) {
            System.out.printf("Thread: %s interrupted from sleep, exception thrown \n",
                    Thread.currentThread().getName());
        }
        formatter.set(new SimpleDateFormat());
        System.out.printf("Thread: %s, formatter: %s \n", Thread.currentThread().getName(),
                formatter.get().toPattern());
    };

    public static void main(String[] args) {
        IntStream.range(1, 10).forEach(i -> {
            Thread t = new Thread(r, "-" + i);
            try {
                TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));
            } catch (InterruptedException e) {
                System.out.println("Main Thread interrupted from sleep, exception thrown");
            }
            t.start();
        });
    }
}
