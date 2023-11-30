package concurrency;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

public class Phasers {
    public static void main(String[] args) throws InterruptedException {
        Phaser p = new Phaser(1);
        System.out.printf("Phaser - current phase - %d \n", p.getPhase());
        new Thread(new LongRunningAction("T1", p)).start();
        new Thread(new LongRunningAction("T2", p)).start();
        new Thread(new LongRunningAction("T3", p)).start();

        System.out.printf("Thread %s waiting for others\n", Thread.currentThread().getName());
        p.arriveAndAwaitAdvance();
        System.out.printf("Thread %s proceeding in phase %d\n", Thread.currentThread().getName(), p.getPhase());

        new Thread(new LongRunningAction("T4", p)).start();
        new Thread(new LongRunningAction("T5", p)).start();
        
        System.out.printf("Thread %s waiting for next phase \n", Thread.currentThread().getName());
        p.arriveAndAwaitAdvance();
        System.out.printf("Thread %s proceeding in phase %d\n", Thread.currentThread().getName(), p.getPhase());

        p.arriveAndDeregister();
        TimeUnit.MILLISECONDS.sleep(1000);
        System.out.printf("Phaser is Eliminated? - %s\n", p.isTerminated());
    }
}

class LongRunningAction implements Runnable {
    private String threadName;
    private Phaser phaser;

    public LongRunningAction(String threadName, Phaser phaser) {
        this.threadName = threadName;
        this.phaser = phaser;
        this.randomWait();
        phaser.register();
    }

    public void run() {
        phaser.arriveAndAwaitAdvance();
        randomWait();
        phaser.arriveAndDeregister();
    }

    // simulate some work
    private void randomWait() {
        try {
            TimeUnit.MILLISECONDS.sleep(((long) Math.random() * 100));
        } catch(InterruptedException e) { e.printStackTrace(); }
    }


}
