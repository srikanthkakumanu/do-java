package concurrency;

import java.util.concurrent.TimeUnit;

public class ThreadInterruptor {
    public static void main(String[] args) {
        // main thread
        Thread.currentThread().setName("Main");
        System.out.printf("Name: %s started running.. \n", Thread.currentThread().getName());

        Thread t1 = new MyThread();
        t1.setName("T1");
        t1.start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            System.out.printf("Name: %s is interrupted from sleep \n", Thread.currentThread().getName());
        }
        
        t1.interrupt();
        // comment the below join line to see the difference. 
        // join statement makes main thread to wait until t1 to complete the execution.
        try {
            t1.join();
        } catch (InterruptedException e) {
            System.out.println("t1.join() thrown exception..");
        }
        //System.out.printf("Interrupt called on %s from  Main program (main thread) \n", t1.getName());
        System.out.printf("Name: %s completed \n", Thread.currentThread().getName());
    }

    static class MyThread extends Thread {
        public void run() {
            
            System.out.printf("Name: %s started running.. \n", getName());
            for(int i = 0; i < 5; i++) {
                System.out.printf("Name: %s, i = %d \n", getName(), i);
                try {
                    sleep(1000);
                } catch(InterruptedException e) {
                    System.out.printf("Name: %s is interrupted \n", getName());
                    break;
                }
            }
            System.out.printf("Name: %s completed \n", getName());
        }
    }
}

