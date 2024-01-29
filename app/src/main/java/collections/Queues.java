package collections;

import java.util.Queue;
import java.util.ArrayDeque;

/**
 Queue provides two sets of methods.
 1. Which throws exception - add(), remove(), element()
 2. Returns a special value - offer(), poll(), peek()
 
 */
public class Queues {
    public static void main(String[] args) {
        Queue<Task> taskQueue = new ArrayDeque<>(); // Follows FIFO order

        PhoneTask mikePhone = new PhoneTask("Mike", "987 6543");
        PhoneTask paulPhone = new PhoneTask("Paul", "123 4567");
        CodingTask databaseCode = new CodingTask("db");
        CodingTask guiCode = new CodingTask("gui");
        CodingTask logicCode = new CodingTask("logic");
        // 
        taskQueue.offer(mikePhone);
        taskQueue.offer(paulPhone);
        System.out.println("Inserted Tasks:");
        taskQueue.stream().forEach(System.out::println);

        Task removed = taskQueue.poll();
        System.out.println("Removed Task: " + removed);
        System.out.println("Head: " + taskQueue.peek());
    }
}


