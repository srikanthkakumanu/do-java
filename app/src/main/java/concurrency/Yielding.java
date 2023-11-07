package concurrency;

import java.util.concurrent.TimeUnit;

import javax.annotation.concurrent.NotThreadSafe;

public class Yielding {
    public static void main(String[] args) {
        Thread main = Thread.currentThread();
        main.setName("Main");
        System.out.println("Main thread is now running");

        MyThread t1 = new MyThread();
        t1.setName("T1");
        t1.start();
        t1.interrupt();
        System.out.println("Main thread is completed");
    }
}

@NotThreadSafe
class UnsafeSequence {
    private int value;
    public int getValue() { return value++; }
}
class MyThread extends Thread {
    public void run() {
        System.out.printf("%s is now running \n", getName());
        for(int i = 0; i < 10; i++) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                System.out.printf("%s is interrupted from sleep \n", getName());
            }
            System.out.printf("%s is printing i = %d \n", getName(), i);
        }
        System.out.printf("%s is now completed running \n", getName());
    }
}