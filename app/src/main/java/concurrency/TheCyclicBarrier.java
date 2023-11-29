package concurrency;

import java.util.concurrent.CyclicBarrier;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.stream.IntStream;
public class TheCyclicBarrier {

    private CyclicBarrier cb;
    private List<List<Integer>> partialResults = Collections.synchronizedList(new ArrayList<>());
    private Random random = new Random();
    private int NUM_PARTIAL_RESULTS;
    private int NUM_WORKERS;

    public static void main(String[] args) {
        TheCyclicBarrier cb = new TheCyclicBarrier();
        cb.runSimulation(5, 3);
    }

    private void runSimulation(int numWorkers, int numPartialResults) {
        NUM_PARTIAL_RESULTS = numPartialResults;
        NUM_WORKERS = numWorkers;

        cb = new CyclicBarrier(NUM_WORKERS, getAggregatorThread());

        System.out.printf("Spawning %d worker threads to compute %d partial results each.\n", NUM_WORKERS, NUM_PARTIAL_RESULTS);
        IntStream.range(0, NUM_WORKERS).forEach(w -> {
            Thread worker = new Thread(getNumCruncherThread());
            worker.start();
        });
    }

    private Runnable getNumCruncherThread() {

        Runnable r = () -> {
            String name = Thread.currentThread().getName();
            List<Integer> partialResult = new ArrayList<>();

            IntStream.range(0, NUM_PARTIAL_RESULTS).forEach(index -> partialResult.add(getRandomNumber()));
            partialResults.add(partialResult);

            try {
                System.out.printf("%s: waiting for other threads to reach barrier.\n", name);
                cb.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            } 
        };
        return r;
    }

    private Integer getRandomNumber() {
        Integer num = random.nextInt(10);
        System.out.printf("%s: Crunching some numbers: Final Result - %d\n", Thread.currentThread().getName(), num);
        return num;
    }

    private Runnable getAggregatorThread() {
        Runnable r = () -> {
            String name = Thread.currentThread().getName();
            System.out.printf("%s: computing sum of %d workers, having %d results each\n", name, NUM_WORKERS, NUM_PARTIAL_RESULTS);
            int sum = 0;
            // partialResults.forEach(pResult -> {
            //     System.out.print("Adding ");
            //     pResult.forEach(result -> {
            //         System.out.print(result + " ");
            //         sum += result;
            //     });
            //     System.out.println();
            // });

            sum = partialResults.stream()
                    .flatMapToInt(result -> result.stream()
                            .mapToInt(i -> i))
                    .sum();
            System.out.printf("%s: Final Result - %d\n", name, sum);
        };
        return r;
    }
}

