package concurrency;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeUnit;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DaemonThreads2 {

    public static void main(String[] args) {
        Deque<Event> deque = new ConcurrentLinkedDeque<>();
        WriterTask writer = new WriterTask(deque);
        for(int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
            Thread thread = new Thread(writer);
            thread.start();
        }
        CleanerTask cleaner = new CleanerTask(deque);
        cleaner.start();
    } 
}

class CleanerTask extends Thread {
    private Deque<Event> deque;

    public CleanerTask(Deque<Event> deque) { this.deque = deque;  setDaemon(true); }

    public void run() {
        System.out.println("CleanerTask: started and running");
        while(true) {
            LocalDateTime date = LocalDateTime.now();
            clean(date);
        }
    }

    private void clean(LocalDateTime date) {
        long diff;
        boolean delete;
        
        if(deque.size() == 0)
            return;
        
        delete = false;
        do {
            Event e = deque.getLast();
            diff = convertLocalDateTimeToLong(date) - convertLocalDateTimeToLong(e.getDate());
            if(diff > 10000) {
                System.out.printf("CleanerTask: %s \n", e.getType());
                deque.removeLast();
                delete = true;
            }
        } while(diff > 10000);

        if(delete)
            System.out.printf("CleanerTask: Size of the queue: %d \n", deque.size());
        System.out.println("CleanerTask: completed");
    }

    private long convertLocalDateTimeToLong(LocalDateTime ldt) {
        ZonedDateTime zdt = ZonedDateTime.of(ldt, ZoneId.systemDefault());
        return zdt.toInstant().toEpochMilli();
    }
}

class WriterTask implements Runnable {
    private Deque<Event> deque;

    public WriterTask(Deque<Event> deque) { this.deque = deque; }

    public void run() {
        System.out.println("WriterTask: started and running");
        for(int i = 1; i < 100; i++) {
            Event event = new Event();
            event.setDate(LocalDateTime.now());
            event.setType(String.format("The Thread %s has generated an event", Thread.currentThread().getId()));
            deque.addFirst(event);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch(InterruptedException e) {
                System.out.println("WriterTask: interrupted from sleep, Exception thrown");
            }
        }
        System.out.println("WriterTask: completed");
    }
}
class Event {
    private LocalDateTime date;
    private String type;
    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
}


