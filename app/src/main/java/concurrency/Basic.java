package concurrency;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Thread.State;

public class Basic {
    public static void main(String[] args) {

        System.out.printf("Minimum Priority : %s, Medium Priority : %s, Maximum Priority : %s\n", Thread.MIN_PRIORITY,
                Thread.NORM_PRIORITY, Thread.MAX_PRIORITY);
        Thread[] threads;
        Thread.State[] status;
        int totalThreads = 5;
        threads = new Thread[totalThreads];
        status = new Thread.State[totalThreads];

        for (int i = 0; i < totalThreads; i++) {
            threads[i] = new Thread(new Calculator());
            if ((i % 2) == 0)
                threads[i].setPriority(Thread.MAX_PRIORITY);
            else
                threads[i].setPriority(Thread.MIN_PRIORITY);

            threads[i].setName("My Thread " + i);
        }

        try (
                FileWriter file = new FileWriter("log.txt");
                PrintWriter pw = new PrintWriter(file);) {

            for (int i = 0; i < totalThreads; i++) {
                pw.println("Main: Status of Thread " + i + " : " + threads[i].getState());
                status[i] = threads[i].getState();
            }

            for (int i = 0; i < totalThreads; i++)
                threads[i].start();

            boolean finish = false;
            while (!finish) {
                for (int i = 0; i < totalThreads; i++) {
                    if (threads[i].getState() != status[i]) {
                        writeThreadInfo(pw, threads[i], status[i]);
                        status[i] = threads[i].getState();
                    }
                }

                finish = true;
                for (int i = 0; i < totalThreads; i++) 
                    finish = finish && (threads[i].getState() == State.TERMINATED);
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeThreadInfo(PrintWriter pw, Thread thread, State state) {
        pw.printf("Main:- [ ID: %d, Name: %s, Priority: %d, Old State: %s, New State: %s] \n", 
        thread.getId(), thread.getName(), thread.getPriority(), state, thread.getState());
        pw.printf("*****************************************************************************************\n");
    }
}

class Calculator implements Runnable {

    @Override
    public void run() {
        long current = 1L;
        long max = 5_000L;
        long numOfPrimes = 0L;

        System.out.printf("Thread '%s': START\n", Thread.currentThread().getName());

        while (current <= max) {
            if (isPrime(current))
                numOfPrimes++;

            current++;
        }
        System.out.printf("Thread '%s': END. Number of Primes : %d\n", Thread.currentThread().getName(), numOfPrimes);
    }

    private boolean isPrime(long number) {
        if (number <= 2)
            return true;

        for (long i = 2; i < number; i++) {
            if ((number % i) == 0)
                return false;
        }
        return false;
    }
}