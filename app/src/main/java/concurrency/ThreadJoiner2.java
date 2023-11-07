package concurrency;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;


public class ThreadJoiner2 {
    public static void main(String[] args) {
        
        Runnable datasourceLoader = () -> {
            System.out.printf("DataSourceLoader is started and loading %s \n", LocalDateTime.now());
            try {
                TimeUnit.SECONDS.sleep(4);
            } catch(InterruptedException e) {
                System.out.println("DataSourceLoader is interrupted from sleep.. Exception Thrown");
            }
            System.out.printf("DataSourceLoader loading is completed %s \n", LocalDateTime.now());
        };

        Runnable networkConnectionLoader = () -> {
            System.out.printf("NetworkConnectionLoader is started and loading %s \n", LocalDateTime.now());
            try {
                TimeUnit.SECONDS.sleep(6);
            } catch(InterruptedException e) {
                System.out.println("NetworkConnectionLoader is interrupted from sleep.. Exception Thrown");
            }
            System.out.printf("NetworkConnectionLoader loading is completed %s \n", LocalDateTime.now());
        };

        System.out.printf("Main thread started and running - %s \n", LocalDateTime.now());
        
        Thread dsLoader = new Thread(datasourceLoader, "DatasourceLoader");
        Thread ncLoader = new Thread(networkConnectionLoader, "NetworkConnectionLoader");
        dsLoader.start(); ncLoader.start();

        try {
            dsLoader.join();
            ncLoader.join();
        } catch(InterruptedException e) {
            System.out.println("InterruptedException thrown while invoking join on DatasourceLoader and NetworkConnectionLoader threads");
        }
        System.out.printf("Main thread completed - %s \n", LocalDateTime.now());
    }

}
