package concurrency;

/**
 * UncaughtExceptionHandler is to attach an exception handler
 * to a thread to handle any un caught exceptions.
 */
public class UncaughtExceptionHandlerDemo {
        public static void main(String[] args) throws InterruptedException {
            Thread t = new Thread(() -> {
                System.out.printf("We are in thread: %s and Priority: %d \n", Thread.currentThread().getName(), Thread.currentThread().getPriority());
                throw new RuntimeException("Intentional Exception");
            });

            System.out.printf("We are in thread: %s before starting a new thread\n", Thread.currentThread().getName());
            
            // t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            //     @Override
            //     public void uncaughtException(Thread t, Throwable e) {
            //         System.out.printf("A critical error happened in thread: %s and the error is: %s\n", t.getName(), e.getMessage());
            //     }
            // });

            t.setUncaughtExceptionHandler((t1, e) -> {
                System.out.printf("A critical error happened in thread: %s and the error is: %s\n", t1.getName(), e.getMessage());
            });

            t.start(); t.setPriority(Thread.MAX_PRIORITY);
                        
            System.out.printf("We are in thread: %s after starting a new thread\n", Thread.currentThread().getName());
            t.sleep(1000);
        }
}
