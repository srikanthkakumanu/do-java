package concurrency;

import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

public class SimpleCountDownLatch {
    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(5);
        System.out.println("Starting");
        new Thread(new TheThread(latch)).start();
        try {
            latch.await();
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        System.out.println("Done");
    }
}
class TheThread implements Runnable {
    CountDownLatch latch;

    TheThread(CountDownLatch latch) { this.latch = latch; }
    public void run() {
        IntStream.rangeClosed(1, 5).forEach(v -> {
            System.out.println(v);
            latch.countDown();
        });
    }
}
