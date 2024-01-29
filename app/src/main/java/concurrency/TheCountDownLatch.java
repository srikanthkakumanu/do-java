package concurrency;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.Collections;
import java.util.ArrayList;
import java.util.stream.Stream;
import static java.util.stream.Collectors.toList;

public class TheCountDownLatch {
    public static void main(String[] args) throws InterruptedException {
        TheCountDownLatch cdl = new TheCountDownLatch();
        //cdl.blockParentThreadUntilComplete();
        cdl.blockEachChildUntilAllOtherThreadsStart();
    }

    /**
     This below example blocks parent thread (Main thread) until some child
     threads finishes.
     @throws InterruptedException
     */
    private void blockParentThreadUntilComplete() throws InterruptedException {
        // Given
        List<String> scraper = Collections.synchronizedList(new ArrayList<>());
        CountDownLatch latch = new CountDownLatch(5);

        List<Thread> workers = Stream.generate(() -> new Thread(new CountDownWorker(scraper, latch)))
                .limit(5).collect(toList());

        // When
        workers.forEach(Thread::start);
        latch.await(); // Block until workers finish
        scraper.add("Latch Released");
        scraper.forEach(System.out::println);
    }

    /**
     This below example (contrary to blockParentThreadUntilComplete() metod),
     blocks each child thread until all other threads have started and then
     blocks until the workers have finished.
     Very useful to test parallalism.
     
     @throws InterruptedException
     */
    private void blockEachChildUntilAllOtherThreadsStart() throws InterruptedException {
        List<String> scraper = Collections.synchronizedList(new ArrayList<>());
        CountDownLatch ready = new CountDownLatch(5);
        CountDownLatch calling = new CountDownLatch(1);
        CountDownLatch completed = new CountDownLatch(5);

        List<Thread> workers = Stream.generate(() -> new Thread(new WaitingWorker(scraper, ready, calling, completed)))
                .limit(5).collect(toList());
        workers.forEach(Thread::start);
        ready.await();
        scraper.add("Workers Ready");
        calling.countDown();
        completed.await();
        scraper.add("Workers completed");
        scraper.forEach(System.out::println);
    }
}


class CountDownWorker implements Runnable {
    private List<String> scraper;
    private CountDownLatch latch;

    public CountDownWorker(List<String> scraper, CountDownLatch latch) {
        this.scraper = scraper;
        this.latch = latch;
    }

    public void run() {
        someThing();
        scraper.add("Counted Down");
        latch.countDown();
    }

    private void someThing() { System.out.printf("%s Doing some work\n", Thread.currentThread().getName()); }
}


class WaitingWorker implements Runnable {
    private List<String> scraper;
    private CountDownLatch ready, calling, completed;

    public WaitingWorker(List<String> scraper, CountDownLatch ready, CountDownLatch calling, CountDownLatch completed) {
        this.scraper = scraper;
        this.ready = ready;
        this.calling = calling;
        this.completed = completed;
    }

    public void run() {
        ready.countDown();
        try {
            calling.await();
            someThing();
            scraper.add("Counted Down");
        } catch(InterruptedException e) { e.printStackTrace(); }
        finally { completed.countDown(); }
    }

    private void someThing() { System.out.printf("%s Doing some work\n", Thread.currentThread().getName()); }
}