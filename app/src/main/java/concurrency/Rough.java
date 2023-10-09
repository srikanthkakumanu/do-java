package concurrency;

import java.math.BigInteger;

/**
 * Interrupting a thread via interrupt() method always throw an InterruptedException
 * and execution is interrupted. To avoid this situation, we can use Daemon thread
 * to continue the execution.
 * 
 */
public class Rough {
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