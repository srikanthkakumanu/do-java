package concurrency;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.IntStream;

public class ThreadJoiner {
    public static void main(String[] args) throws InterruptedException {
        print("Main", "started running");
        // ThreadJoin tj = new ThreadJoin();
        // tj.setName("TJ");
        // tj.start();
        // tj.join(); // comment this line to see the output difference between main thread and tj thread

        ThreadJoinWithDuration tjd = new ThreadJoinWithDuration();
        tjd.setName("TJD");
        tjd.start();
        // The main thread actually waits for the other thread until 
        // that dies because the join is called in a while loop, 
        // but without that, the main thread would continue the 
        // execution after 1 second.
        while(tjd.isAlive()) {
            print("Main", "is waiting for the thread: "+ tjd.getName());
            tjd.join(1000);
        }
        print("Main", "is completed");
    }

    private static void print(String name, String message) {
        System.out.printf("Name: %s %s \n", name, message);
    }
    private static void printWithTime(String name, String time, String message) {
        System.out.printf("Name: %s Time: %s %s \n", name, time, message);
    }

    static class ThreadJoin extends Thread {
        private int result;
        
        public void run() {
            print(getName(), "started the addition");
            result = IntStream.range(1, 10).sum();
            print(getName(), "is completed with result = " + result);
        }
    }

    static class ThreadJoinWithDuration extends Thread {
        private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        String time = dtf.format(LocalTime.now());
        
        public void run() {
            printWithTime(getName(), dtf.format(LocalTime.now()), "started long running operation");
            try {
                sleep(16_000);
            } catch(InterruptedException e) {
                printWithTime(getName(), dtf.format(LocalTime.now()), "interrupted from sleep, exception thrown");
            }
            printWithTime(getName(), dtf.format(LocalTime.now()), "is completed long running operation");
        }
    }
}