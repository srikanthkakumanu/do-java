package concurrency;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class SimpleCyclicBarrier {
    public static void main(String[] args) {
        // // An object of this implementation is called when the CyclicBarrier ends.
        Runnable barrierAction = () -> System.out.println("Barrier Reached!");
        CyclicBarrier cb = new CyclicBarrier(3, new Thread(barrierAction));
        new Thread(new CBThread("A", cb)).start();
        new Thread(new CBThread("B", cb)).start();
        new Thread(new CBThread("C", cb)).start();
        new Thread(new CBThread("X", cb)).start();
        new Thread(new CBThread("Y", cb)).start();
        new Thread(new CBThread("Z", cb)).start();
    }
}

class CBThread implements Runnable {
    private CyclicBarrier cb;
    private String name;

    CBThread(String name, CyclicBarrier cb) {
        this.name = name;
        this.cb = cb;
    }

    public void run() {
        System.out.println(name);
        try { cb.await(); }
        catch (BrokenBarrierException | InterruptedException e) {
            System.out.println(e);
        }
    }
}