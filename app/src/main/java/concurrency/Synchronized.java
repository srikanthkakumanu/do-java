package concurrency;

import java.util.stream.IntStream;

public class Synchronized {

    public static void main(String[] args) {
        SynchronizedExchanger exchanger = new SynchronizedExchanger();

        Thread t1 = new Thread() {
            @Override
            public void run() {
                IntStream.range(0, 1000).forEach(i -> exchanger.setObject("" + i));
            }
        };
        t1.setName("T1");

        Thread t2 = new Thread() {
            @Override
            public void run() {
                IntStream.range(0, 1000).forEach(i -> System.out.printf("Thread: %s, i : %s \n", getName(), exchanger.getObject()));
            }
        };
        t2.setName("T2");

        t1.start();
        t2.start();
    }
}

class SynchronizedExchanger {
    protected Object object = null;

    public synchronized void setObject(Object object) { this.object = object; }

    public synchronized Object getObject() { return object; }

    public void setObj(Object object) {
        System.out.println("setObject is called on start: ");
        synchronized(this) {
            this.object = object;
        }
        System.out.println("setObject is called on end:");
    }

    public Object getObj() { 
        System.out.println("getObject is called on start:");
        synchronized(this) {
            System.out.println("getObject is called on end:");
            return this.object;
        }
    }
}
