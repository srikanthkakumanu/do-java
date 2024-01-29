package concurrency;

import java.math.BigInteger;

/**
 Interrupting a thread via interrupt() method always throw an InterruptedException
 and execution is interrupted as main thread will terminate the execution. If there
 are any other threads are in midst of execution, such threads execution should be 
 handled in graceful manner as shown below. 
 
 To avoid such situation of handling other threads execution in graceful manner and
 not to cause a blocker for main thread to complete the execution,daemon threds are 
 the solution even after main threads execution is complete.
 
 */
public class InterruptedExceptionDemo {
    public static void main(String[] args) {
        // 1st example
        Runnable blockingTask = () -> {
            try {
                Thread.sleep(500000);
            } catch (InterruptedException e) {
               System.out.println("Exiting blocking thread");
            }
        };

        Thread t = new Thread(blockingTask);
        t.start();
        t.interrupt();

        // 2nd example
        t = new Thread(new LongComputeTask(new BigInteger("200000"), new BigInteger("1000000000")));
        t.start();
        t.interrupt();
    }

    private static class LongComputeTask extends Thread {
        private BigInteger base, power;

        public LongComputeTask(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        public void run() {
            System.out.printf("Base ^ Power = %d\n ", pow(base, power));
        }

        private BigInteger pow(BigInteger base, BigInteger power) {
            BigInteger result = BigInteger.ONE;

            for(BigInteger i = BigInteger.ZERO; i.compareTo(power) != 0; i = i.add(BigInteger.ONE)) {
                if(Thread.currentThread().isInterrupted()) {
                    System.out.println("Prematurely interrupted long computation task");
                    return BigInteger.ZERO;
                }
                result = result.multiply(base);
            }
            return result;
        }
    }
}