package concurrency.executors;

import java.util.concurrent.Executor;

public class SimpleExecutor {
    public static void main(String[] args) {
        Runnable r = () -> { System.out.println("Simple Runnable"); };
        Executor e = new TheExecutor();
        e.execute(r);        
    }
}

class TheExecutor implements Executor {
    public void execute(Runnable r) { 
        r.run(); 
        // new Thread(r).start(); // spawns a new thread for every execution
    }
}

