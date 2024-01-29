package concurrency.executors;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinTask;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 There are two approaches to submit tasks to fork/join thread pool.
 1. via submit() or execute(): Need to explicitly join after submit or execute.
          forkJoinPool.execute(customRecursiveTask);
          int result = customRecursiveTask.join();
 2. via invoke(): It forks the task wait for the result, does not need any manual joining.
          int result = forkJoinPool.invoke(customRecursiveTask); 
 */
public class ForkJoinFramework {

    private int[] arr;
    private CustomRecursiveTask customRecursiveTask;
    private ForkJoinPool forkJoinPool = new ForkJoinPool(2);

    public static void main(String[] args) {
        ForkJoinFramework fj = new ForkJoinFramework();
        fj.init();
        fj.callPoolUtil_whenExistsAndExpectedType_thenCorrect();
        fj.executeRecursiveAction_whenExecuted_thenCorrect();
        fj.executeRecursiveAction_whenExecuted_thenCorrect();
        fj.executeRecursiveTask_whenExecuted_thenCorrect();
        fj.executeRecursiveTaskWithFJ_whenExecuted_thenCorrect();
        fj.executeRecursiveTaskWithFJ_whenExecuted_thenCorrect();
    }


    public void init() {
        Random random = new Random();
        arr = new int[50];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(35);
        }
        customRecursiveTask = new CustomRecursiveTask(arr);
    }

    public void callPoolUtil_whenExistsAndExpectedType_thenCorrect() {
        System.out.println(forkJoinPool.getParallelism()); // should return 2; no. of cores
    }

    public void executeRecursiveAction_whenExecuted_thenCorrect() {

        CustomRecursiveAction myRecursiveAction = new CustomRecursiveAction("ddddffffgggghhhh");
        ForkJoinPool.commonPool().invoke(myRecursiveAction);

        System.out.println(myRecursiveAction.isDone());
    }

    public void executeRecursiveTask_whenExecuted_thenCorrect() {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();

        forkJoinPool.execute(customRecursiveTask);
        customRecursiveTask.join();
        System.out.println(customRecursiveTask.isDone());

        forkJoinPool.submit(customRecursiveTask);
        customRecursiveTask.join();
        System.out.println(customRecursiveTask.isDone());
    }

    public void executeRecursiveTaskWithFJ_whenExecuted_thenCorrect() {
        CustomRecursiveTask customRecursiveTaskFirst = new CustomRecursiveTask(arr);
        CustomRecursiveTask customRecursiveTaskSecond = new CustomRecursiveTask(arr);
        CustomRecursiveTask customRecursiveTaskLast = new CustomRecursiveTask(arr);

        customRecursiveTaskFirst.fork();
        customRecursiveTaskSecond.fork();
        customRecursiveTaskLast.fork();
        int result = 0;
        result += customRecursiveTaskLast.join();
        result += customRecursiveTaskSecond.join();
        result += customRecursiveTaskFirst.join();

        System.out.println(customRecursiveTaskFirst.isDone());
        System.out.println(customRecursiveTaskSecond.isDone());
        System.out.println(customRecursiveTaskLast.isDone());
        System.out.println(result != 0);
    }
}

class CustomRecursiveAction extends RecursiveAction {

    private String workload = "";
    private static final int THRESHOLD = 4;

    public CustomRecursiveAction(String workload) { this.workload = workload; }

    @Override
    protected void compute() {
        if(workload.length() > THRESHOLD)
            ForkJoinTask.invokeAll(createSubTasks());
        else
            processing(workload);
    }

    private List<CustomRecursiveAction> createSubTasks() {
        List<CustomRecursiveAction> subtasks = new ArrayList<>();
        String partOne = workload.substring(0, workload.length()/2);
        String partTwo = workload.substring(workload.length()/2, workload.length());
        subtasks.add(new CustomRecursiveAction(partOne));
        subtasks.add(new CustomRecursiveAction(partTwo));
        return subtasks;
    }

    private void processing(String workload) { 
        System.out.printf("Thread: %s processed and Result: %s\n", Thread.currentThread().getName(), workload.toUpperCase()); 
    }
}

class CustomRecursiveTask extends RecursiveTask<Integer> {
    private int[] arr;

    private static final int THRESHOLD = 20;

    public CustomRecursiveTask(int[] arr) {
        this.arr = arr;
    }

    @Override
    protected Integer compute() {
        if (arr.length > THRESHOLD) {
            return ForkJoinTask.invokeAll(createSubTasks())
              .stream()
              .mapToInt(ForkJoinTask::join)
              .sum();
        } else {
            return processing(arr);
        }
    }

    private Collection<CustomRecursiveTask> createSubTasks() {
        List<CustomRecursiveTask> dividedTasks = new ArrayList<>();
        dividedTasks.add(new CustomRecursiveTask(
          Arrays.copyOfRange(arr, 0, arr.length / 2)));
        dividedTasks.add(new CustomRecursiveTask(
          Arrays.copyOfRange(arr, arr.length / 2, arr.length)));
        return dividedTasks;
    }

    private Integer processing(int[] arr) {
        return Arrays.stream(arr)
          .filter(a -> a > 10 && a < 27)
          .map(a -> a * 10)
          .sum();
    }
}