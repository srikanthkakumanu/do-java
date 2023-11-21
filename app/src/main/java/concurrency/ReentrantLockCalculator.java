package concurrency;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockCalculator {
    public static void main(String[] args) {
        
    }

    public static class Calculator {
        public static final int UNSPECIFIED = -1;
        public static final int ADDITION = 0;
        public static final int SUBSTRACTION = 1;
        int type = UNSPECIFIED;

        public double value;
        private double result = 0.0d;
        // true - ensure lock fairness for waiting threads, which may prevent thread starvation to occur
        Lock lock = new ReentrantLock(true); 

        public Calculator(int type, double value) {
            this.type = type;
            this.value = value;
        }

        public void add(double value) {
            try {
                lock.lock();
                this.result += value;
            } finally {
                lock.unlock();
            }
        }

        public void substract(double value) {
            try {
                lock.lock();
                this.result -= value;
            } finally {
                lock.unlock();
            }
        }        

        public void calculate(Calculator... calculator) {
            try {
                lock.lock();
                for(Calculator cal : calculator) {
                    switch (cal.type) {
                        case Calculator.ADDITION: add(cal.value);
                            break;
                        case Calculator.SUBSTRACTION: substract(cal.value);
                            break;
                    }
                }
            } finally {
                lock.unlock();
            }
        }
    }
}
