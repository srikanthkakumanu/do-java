package concurrency;

import java.math.BigInteger;

/**
 Daemon thread purpose is that background tasks should not block our application
 i.e. main method thread from terminating.
 */
public class DaemonThreads {
    public static void main(String[] args) throws InterruptedException {

        Thread t = new Thread(new LongComputeTask(new BigInteger("200000"), new BigInteger("1000000000")));
        t.setDaemon(true);
        t.start();
        Thread.sleep(100);
        //t.interrupt();
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