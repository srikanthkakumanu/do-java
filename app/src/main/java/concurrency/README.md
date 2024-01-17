<div style="text-align: justify">
<H2>Concurrency Notes</H2>


<H3><B>1. Thread Management</B></H3>

<B>interrupt(), join(), yield() are basic mechanisms to communicate among threads.</B>

Java provides built-in wait(), notify(), notifyAll() methods to support wait and notification mechanism to all Monitor objects (at java.lang.Object level). It is to address the problem of <B>Busy Waiting</B> which occurs when a thread repeatedly check to see if the condition is true there by wasting CPU cycles. 

<H4><B>1.2 Thread Local</B></H4>
- ThreadLocal variables maintain a local copy of a value per thread so that the value is not shared/visible across multiple threads.

- <B>ThreadLocal is way of providing thread safety instead of using synchronized blocks.</B> Synchronized blocks still share the data (as the variables can be accessed via object across threads) but ThreadLocal variables are not shared.

- InheritableThreadLocal is available to share the variable state across parent and child threads (which are created within a parent thread).

- Cleanup is necessary when ThreadLocal variables are using along with ThreadPool. Because ThreadPool reuses the same threads and previous state of ThreadLocal still preserved. Hence the cleanup/removal is required. ExecutorService (ThreadPoolExecutor) gives an option of beforeExecute() and afterExecute() methods to do the necessary.

<H3><B>2. Thread Synchronization</B></H3>

Java provides 2 types of synchronization.

- <B>Mutual Exclusion (Mutex)</B> - Enable multiple threads to access an updated shared data without the occurance of race conditions. 

- <B>Co-ordination</B> - Enable the threads to cooperatively schedule their interactions.

It provides various ways to support synchronization.

1. Basic synchronization (synchronized keyword)
2. Locks
3. Condition Object - Allow thread to block until a condition becomes true.

The below is also useful for synchronization and thread coordination which offer out-of-the-box solution.
<B>Concurrent Utilities for synchronization or Thread co-ordination</B>

4. Semaphore - Maintain the permits. The permits that control thread access to limited no. of shared resources.
5. CountDownLatch - Allow one or more threads to wait until a set of operations to perform in other threads complete.
6. CyclicBarrier - Allow a set of threads to all wait for each other to reach a common barrier point.
7. Phaser - A more flexible reusable synchronization barrier. It is more flexible than CountDownLatch and CyclicBarrier.
8. Exchanger - Simplifies the exchange of data between two threads. Exchanger simply waits until two separate threads call its exchange() method.
9. BlockingQueues - BlockingQueue, LinkedBlockingQueue (Unbounded), LinkedTransferQueue (Unbounded), ArrayBlockingQueue (Bounded), SynchronousQueue (Bounded), PriorityBlockingQueue (Bounded)
10. DelayQueue
11. Future and CompletableFuture - Asynchronous processing

| Synchronizers  | Description                                                                         |
|----------------|-------------------------------------------------------------------------------------|
| Semphore       | A classic semaphore that maintain the permits (by using counter - no. of threads).  |
| CountDownLatch | Waits until a specified number of events have occurred. Maintains internal counter. |
| CyclicBarrier  | Enable a group of threads to wait at a predefined execution point.                  |
| Exchanger      | Exchanges the data between two threads.                                             |
| Phaser         | Synchronize the threads that advance through multiple phases of an operation.       |

<H4><B>2.1 Synchronized</B></H4>

Any Java object can be used a <B>Monitor</B> object:

- By marking methods (which require mutual exclusion aka Mutex) with synchronized keyword.
- By declaring any code block marked with synchronized keyword.
- synchronized block ensures that only one thread can obtain a lock on a specific object (called Monitor).
- synchronized is not recommended to use with primitive classes/objects ex. Integer, String etc.
- We can declare static synchronized methods but lock applies only at class level. It means only one thread can access static synchronized objects/methods at a time for the whole class.
- We can declare static synchronized blocks and synchronized blocks in the same class but above rules apply.
- They can only block threads that are running within same JVM.

<B>Limitations of Synchonrized blocks</B>
- Only one thread can enter a synchronized block at a time.
- No guarantee of sequence in which waiting threads gets access to the synchronized block.
- Performance overhead of entering and exiting of synchronized blocks.
    - Low overhead - when sync. block is not already blocked.
    - High overhead - when sync. block is already locked by another thread. 

<H4><B>2.2 Locks</B></H4>

Locks are an alternative to basic synchronization.

There are three important Lock implementations, which are:
- ReentrantLock 
    - provides recursive locks. But both reads and writes under one lock.
    - boolean true value in the ReentrantLock(boolean fairness) provides lock fairness which may add additional overhead but ensures no lock congestion happens.
- ReentrantReadWriteLock 
    - More efficient than Reentrant lock as it provides separate locking mechanism for reads and writes. 
    - It improves performance when resources more read than writes.
    - It provides only <B>Pessimistic Locking</B>.
    - Lock downgrading (from write to read) is possible. But not read to write.
    - It implements ReadWriteLock, and uses AbstractQueuedSynchronizer internally.
- StampedLock (From Java 8+) 
    - More efficient and scalable than above two locks.
    - It is NOT Re-entrant lock implementation. However it provides three locking modes described below.
    - It provides <B>Optimistic Locking</B>.
    - It does not implement ReadWriteLock, and does not use AbstractQueuedSynchronizer internally.
    - It provides three locking modes:
        - Read
        - Write
        - Optimistic Read: It is main difference between StampedLock and ReentrantReadWriteLock and synchronization overhead is very low.
    
<H4><B>2.3 Condition</B></H4>

- A Lock can be associated with one or more conditions. Condition provides the ability for a thread to wait for some condition to occur while executing the <B>critical section</B>.
- It is an alternative to `wait()`, `notify() ` and `notifyAll()` (from `java.lang.Object`). Condition interface provides `await()`, `signal()` and `signalAll()` methods.
- The purpose of conditions is to allow threads to have control of a lock and check whether a condition is true or not. 
- If the condition is false, thread will be suspended until another thread wakes it up.
- The `java.util.concurrent.locks.Condition` interface provide mechanisms to suspend and wakes up a thread.

<H4><B>2.4 Semaphore</B></H4>

- A semaphore controls the access to a shared resource: 
  - **through the use of a counter (no. of permits)**. In general, **counter's value is set to no. of threads (one thread per core)**.
  - If the counter is greater than zero, then access is allowed (using `acquire()`).
  - If the counter is zero then access is denied (using `release()`).
  - **What the counter is counting are _permits_ that allow access to a shared resource**. Thus, to access the resource, a thread must be granted a permit from the semaphore.
    - The thread that wants access to shared resource tries to acquire the permit. If semaphore's count is greater than zero, then the **thread acquires the permit and the counter is decremented**.
    - Otherwise, the thread **will be blocked until a permit can be acquired**.
    - When the thread no longer needs access to the shared resource, **it releases the permit, which causes the semaphore's count to be incremented**.

- <B>A **semaphore is a non-negative integer**</B> that can be atomically incremented and decremented to control access to a shared resource/object. <B>It acts as a counter that control access</B> to one ore more shared resources/objects.
- It is used to synchronize interactions between multiple threads.
- There are two types of semaphores:
    - **Counting semaphores**: Allow an arbitrary resource count.
    - **Binary semaphores**: Restricts the count to values either 0 or 1. Inherently synonymous to lock vs. unlock or unavailable vs. available.
- It’s a synchronization tool that does not require busy waiting. Hence, the OS does not waste the CPU cycles when a process can’t operate due to a lack of access to a resource.
- <B>Semaphores are more flexible than basic synchronization and locks.</B>
- When used for a resource pool, it tracks how many resources are free and not which resources are free.
- Semaphores are used when the limited resources are available.

<H4><B>2.5 CountDownLatch</B></H4>

- The CountDownLatch is required when we want our thread to wait until one or more events occurred.
  - A CountDownLatch is created with a count of the number of events that must occur before the latch is released.
  - Every time an event happens, the count is decremented.
  - When the count reaches zero, the latch opens.
- CountDownLatch is a powerful yet **easy-to-use synchronization object that is appropriate whenever a thread must wait for one or more events to occur**. 
- A CountDownLatch is a construct that a thread waits on while other threads count down on the latch until it reaches zero.
- Essentially, by using CountDownLatch, we can cause a thread to block until other threads have completed given task. A CountDownLatch (like Semaphore) has a counter field which you can decrement when required. We can use it to block a calling thread until it is down to zero.
- While doing parallel processing, we set the counter with same value of no. of cores we have.
- A CountDownLatch maintains a count of tasks.
- CountDownLatch is different from CyclicBarrier, because the count never resets.
- <B>In CountDownLatch, the number of threads cannot be configured dynamically as the number must be supplied when creating the instance but in Phaser it is possible.</B>

<H4><B>2.6 CyclicBarrier</B></H4>

- A CyclicBarrier is used when n a set of **two or more threads must wait at a predetermined execution point until all threads in the set have reached that point**.
  - **_numThreads_** specifies the number of threads that must reach the barrier before execution continues.
- A CyclicBarrier is a synchronizer that allows a set of threads to **wait for each other to reach a common execution point**.
- <B>That execution point is called barrier. That barrier is called cyclic because it can be re-used after the waiting threads are released.</B>
- A CyclicBarrier is a reusable construct where a group of threads waits together until all the threads arrive at a common point.
- A CyclicBarrier maintains a count of threads.
- When the barrier trips in CyclicBarrier, the count resets to its original value.


<H4><B>2.7 Phaser</B></H4>

- A Phaser is a barrier on which <B>dynamic number of threads</B> need to wait before continuing execution. 
- A Phaser allows us to build a logic in which threads need to wait on the barrier before going to next step of execution.
- A Phaser is a mix of CyclicBarrier and CountDownLatch. We can define number of parties registered may vary over time (Like count in CountDownLatch and tasks/parties in CyclicBarrier).

<H4><B>2.8 Exchanger</B></H4>

- It is designed to **simplify the exchange of data between two threads**.
- The operation of an Exchanger is astoundingly simple: **it simply waits until two separate threads call its exchange( ) method**.
  - When that occurs, it exchanges the data supplied by the threads.

<H3><B>3. Executors</B></H3>

Java concurrency provides executors as below.

- Executor Framework - define 3 executor object types.
- Thread Pools - are the most common way of executor implementation.
- Fork/Join Framework - From Java 7, It takes advantage of multiple processors.

<H4><B>3.1 Executor Framework</B></H4>

- Executor framework is a task scheduling framework. 
- It decouples the creation and management of of Threads from rest of the application logic.
- It standardizes task invocation, scheduling, execution and control of asynchronous tasks according to a set of execution policies.

Executor Framework has the following executor interfaces, implementations and utilities.

- `Executor` - A simple and high level interface that supports launching new tasks.
- `ExecutorService` - A sub interface of `Executor`, that manages the life cycle of tasks and `Executor` itself. It supports asynchronous task execution.
- `ScheduledExecutorService` - A sub interface of `ExecutorService` that supports future and/or periodic tasks.
- `Executors` - A factory and utility class for `Executor`, `ExecutorService`, `ScheduledExecutorService`, `ThreadFactory` and `Callable`.
- Thread Pool Implementations - `ThreadPoolExecutor`, `ScheduledThreadPoolExecutor`.

<H4><B>3.1.1 How to choose Ideal num. of Threads</B><H4>

- <B>No. of CPUs any computer has.</B> If one CPU has two cores, then we should consider it as 2 CPUs. At most a CPU will run only one thread at a time. That means for one CPU with 2 cores, It can run two threads at time. There are some CPUs as hardware specification can run more than one thread. For example, one CPU with 4 core and each core can run 2 threads at a time. That means 4 * 2 * 2 = 16 threads we can run. 

- <B>Type of task a thread performs </B>such as I/O operation, network operation etc.
- <B>Desired fairness between threads</B>.

<H4><B>3.2 ForkJoin Framework</B></H4>

- Java 7 introduced Fork/Join framework to help speed up parallel processing by attempting to use all the available processor cores. 
- It follows <B>divide and conquer approach</B>. It means framework first forks recursively breaking tasks into smaller independant sub tasks until they are simple enough to run asynchronously.
- After that, join part begins, the result of all sub tasks recursively join into a single result.
- Internally it balances the workload of threads with the help of <B>Work Stealing Algorithm</B>. Simply put, free threads try to “steal” work from deques of busy threads.
- To provide effective parallel execution, It uses a pool of threads called `ForkJoinPool`. This pool manages worker threads of type `ForkJoinWorkerThread`.
- Worker threads can execute only one task at a time, but the `ForkJoinPool` doesn’t create a separate thread for every single subtask. Instead, each thread in the pool has its own <B>double-ended queue (or deque, pronounced “deck”)</B> that stores tasks.
- `ForkJoinTask` - Base type for tasks that are executed inside `ForkJoinPool`.
- `RecursiveAction` - for void tasks.
- `RecursiveTask<V>` - for tasks that return value.

There are two approaches to submit tasks to fork/join thread pool.
1. via `submit()` or `execute()`: Need to explicitly join after submit or execute.

`forkJoinPool.execute(customRecursiveTask);
int result = customRecursiveTask.join();`

2. via `invoke()`: It forks the task wait for the result, does not need any manual joining.

`int result = forkJoinPool.invoke(customRecursiveTask);`

</div>