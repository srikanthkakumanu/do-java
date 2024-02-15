<div style="text-align: justify">

# **Java Concurrency**

## **Concurrency Vs. Parallelism**

**Concurrency is about dealing with a lot of things at once. Parallelism is about doing a lot of things at once.**
Concurrency is structuring things in a way that might allow parallelism to actually execute them simultaneously.
**But parallelism is not the goal of concurrency. The goal of concurrency is good structure and possibility to implement execution modes like parallelism.**

**Concurrency** — **It is the general concept of multiple tasks running in overlapping time periods competing over the available resources.**
A single CPU core interleaves them by scheduling and switching between tasks as it sees fit. 
Switching between tasks is relatively easy and fast.

**Parallelism** — It is about simultaneous execution of interleaved tasks and not about managing them.
If more than one CPU core is available, the tasks can run in parallel on different cores.

To use data structures in concurrent environments, they have to be **thread-safe**, usually requiring coordination with locks, semaphores, etc., to work correctly and guarantee safe access to any shared state. 
Executing code in parallel usually lacks such coordination because it’s focused on the execution itself. This makes it safer, more natural, and easier to reason with.


### **1. Thread Management**

**interrupt(), join(), yield() are basic mechanisms to communicate among threads.**

Java provides built-in `wait()`, `notify()`, `notifyAll()` methods to support wait and notification mechanism to all Monitor objects (at `java.lang.Object` level). 
It is to address the problem of **Busy Waiting** which occurs when a thread repeatedly check to see if the condition is true there by wasting CPU cycles. 

#### **1.2 Thread Local**

- ThreadLocal variables maintain a local copy of a value per thread so that the value is not shared/visible across multiple threads.

- **ThreadLocal is a way of providing thread safety instead of using synchronized blocks.** Synchronized blocks still share the data (as the variables can be accessed via an object across threads), but ThreadLocal variables are not shared.

- InheritableThreadLocal is available to share the variable state across parent and child threads (which are created within a parent thread).

- Cleanup is necessary when ThreadLocal variables are used along with ThreadPool. Because ThreadPool reuses the same threads and previous state of ThreadLocal still preserved. Hence the cleanup/removal is required. ExecutorService (ThreadPoolExecutor) gives an option of beforeExecute() and afterExecute() methods to do the necessary.

### **2. Thread Synchronization**

Java provides two types of synchronization.

- **Mutual Exclusion (Mutex)** — Enable multiple threads to access updated shared data without the occurrence of race conditions. 

- **Co-ordination** — Enable the threads to cooperatively schedule their interactions.

It provides various ways to support synchronization.

1. Basic synchronization (`synchronized` keyword)
2. Locks
3. Condition Object — Allow thread to block until a condition becomes true.

The below is also useful for synchronization and thread coordination which offer an out-of-the-box solution.
**Concurrent Utilities for synchronization or Thread co-ordination**

4. Semaphore — Maintain the permits. The permits that control thread access to limit no. of shared resources.
5. CountDownLatch — Allow one or more threads to wait until a set of operations to perform in other threads complete.
6. CyclicBarrier — Allow a set of threads to all wait for each other to reach a common barrier point.
7. Phaser — A more flexible reusable synchronization barrier. It is more flexible than CountDownLatch and CyclicBarrier.
8. Exchanger — Simplifies the exchange of data between two threads. Exchanger simply waits until two separate threads call its exchange() method.
9. BlockingQueues — BlockingQueue, LinkedBlockingQueue (Unbounded), LinkedTransferQueue (Unbounded), ArrayBlockingQueue (Bounded), SynchronousQueue (Bounded), PriorityBlockingQueue (Bounded)
10. DelayQueue
11. Future and CompletableFuture — Asynchronous processing

| Synchronizers  | Description                                                                         |
|----------------|-------------------------------------------------------------------------------------|
| Semaphore      | A classic semaphore that maintain the permits (by using counter - no. of threads).  |
| CountDownLatch | Waits until a specified number of events have occurred. Maintains internal counter. |
| CyclicBarrier  | Enable a group of threads to wait at a predefined execution point.                  |
| Exchanger      | Exchanges the data between two threads.                                             |
| Phaser         | Synchronize the threads that advance through multiple phases of an operation.       |

#### **2.1 Synchronized**

Any Java object can be used a **Monitor** object:

- By marking methods (which require mutual exclusion aka Mutex) with synchronized keyword.
- By declaring any code block marked with synchronized keyword.
- synchronized block ensures that only one thread can obtain a lock on a specific object (called Monitor).
- synchronized is not recommended to use it with primitive classes/objects ex. Integer, String, etc.
- We can declare the static synchronized methods but lock applies only at class level. It means only one thread can access static synchronized objects/methods at a time for the whole class.
- We can declare static synchronized blocks and synchronized blocks in the same class, but the above rules apply.
- They can only block threads that are running within the same JVM.

**Limitations of Synchronized blocks**
- Only one thread can enter a synchronized block at a time.
- No guarantee of sequence in which waiting threads get access to the synchronized block.
- Performance overhead of entering and exiting of synchronized blocks.
    - Low overhead - when sync. block is not yet blocked.
    - High overhead - when sync. the block is already locked by another thread. 

#### **2.2 Locks**

Locks are an alternative to basic synchronization.

There are three important Lock implementations, which are:
- **ReentrantLock** 
    - provides recursive locks. But both reads and writes under one lock.
    - boolean true value in the ReentrantLock(boolean fairness) provides lock fairness which may add additional overhead but ensures no lock congestion happens.
- **ReentrantReadWriteLock** 
    - More efficient than Reentrant lock as it provides separate locking mechanism for reads and writes. 
    - It improves performance when resources more read than writes.
    - It provides only **Pessimistic Locking**.
    - Lock downgrading (from write to read) is possible. But not read to write.
    - It implements ReadWriteLock, and uses AbstractQueuedSynchronizer internally.
- **StampedLock (From Java 8+)** 
    - More efficient and scalable than above two locks.
    - It is NOT Re-entrant lock implementation. However it provides three locking modes described below.
    - It provides <B>Optimistic Locking</B>.
    - It does not implement ReadWriteLock, and does not use AbstractQueuedSynchronizer internally.
    - It provides three locking modes:
        - Read
        - Write
        - Optimistic Read: It is main difference between StampedLock and ReentrantReadWriteLock and synchronization overhead is very low.
    
#### **2.3 Condition**

- A Lock can be associated with one or more conditions. Condition provides the ability for a thread to wait for some condition to occur while executing the **critical section**.
- It is an alternative to `wait()`, `notify() ` and `notifyAll()` (from `java.lang.Object`). Condition interface provides `await()`, `signal()` and `signalAll()` methods.
- The purpose of conditions is to allow threads to have control of a lock and check whether a condition is true or not. 
- If the condition is false, the thread will be suspended until another thread wakes it up.
- The `java.util.concurrent.locks.Condition` interface provide mechanisms to suspend and wakes up a thread.

#### **2.4 Semaphore**

- A semaphore controls the access to a shared resource: 
  - **through the use of a counter (no. of permits)**. In general, **counter's value is set to no. of threads (one thread per core)**.
  - If the counter is greater than zero, then access is allowed (using `acquire()`).
  - If the counter is zero then access is denied (using `release()`).
  - **What the counter is counting are _permits_ that allow access to a shared resource**. Thus, to access the resource, a thread must be granted a permit from the semaphore.
    - The thread that wants access to shared resource tries to acquire the permit. If semaphore's count is greater than zero, then the **thread acquires the permit and the counter is decremented**.
    - Otherwise, the thread **will be blocked until a permit can be acquired**.
    - When the thread no longer needs access to the shared resource, **it releases the permit, which causes the semaphore's count to be incremented**.

- A **semaphore is a non-negative integer** that can be atomically incremented and decremented to control access to a shared resource/object. **It acts as a counter that control access** to one ore more shared resources/objects.
- It is used to synchronize interactions between multiple threads.
- There are two types of semaphores:
    - **Counting semaphores**: Allow an arbitrary resource count.
    - **Binary semaphores**: Restricts the count to values either 0 or 1. Inherently synonymous to lock vs. unlock or unavailable vs. available.
- It’s a synchronization tool that does not require busy waiting. Hence, the OS does not waste the CPU cycles when a process can’t operate due to a lack of access to a resource.
- **Semaphores are more flexible than basic synchronization and locks**.
- When used for a resource pool, it tracks how many resources are free and not which resources are free.
- Semaphores are used when the limited resources are available.

#### **2.5 CountDownLatch**

- The CountDownLatch is required when we want our thread to wait until one or more events occurred.
  - A CountDownLatch is created with a count of the number of events that must occur before the latch is released.
  - Every time an event happens, the count is decremented.
  - When the count reaches zero, the latch opens.
- CountDownLatch is a powerful yet **easy-to-use synchronization object that is appropriate whenever a thread must wait for one or more events to occur**. 
- A CountDownLatch is a construct that a thread waits on while other threads count down on the latch until it reaches zero.
- Essentially, by using CountDownLatch, we can cause a thread to block until other threads have completed a given task. A CountDownLatch (like Semaphore) has a counter-field which you can decrement when required. We can use it to block a calling thread until it is down to zero.
- While doing parallel processing, we set the counter-value equivalent to the no. of cores that we have.
- A CountDownLatch maintains a count of tasks.
- CountDownLatch is different from CyclicBarrier, because the count never resets.
- <B>In CountDownLatch, the number of threads cannot be configured dynamically as the number must be supplied when creating the instance, but in Phaser it is possible.</B>

#### **2.6 CyclicBarrier**

- A CyclicBarrier is used when n a set of **two or more threads must wait at a predetermined execution point until all threads in the set have reached that point**.
  - **_numThreads_** specifies the number of threads that must reach the barrier before execution continues.
- A CyclicBarrier is a synchronizer that allows a set of threads to **wait for each other to reach a common execution point**.
- **That execution point is called barrier. That barrier is called cyclic because it can be re-used after the waiting threads are released**.
- A CyclicBarrier is a reusable construct where a group of threads waits together until all the threads arrive at a common point.
- A CyclicBarrier maintains a count of threads.
- When the barrier trips in CyclicBarrier, the count resets to its original value.


#### **2.7 Phaser**

- A Phaser is a barrier on which **dynamic number of threads** need to wait before continuing execution. 
- A Phaser allows us to build a logic in which threads need to wait on the barrier before going to next step of execution.
- A Phaser is a mix of CyclicBarrier and CountDownLatch. We can define the number of parties registered may vary over time (Like count in CountDownLatch and tasks/parties in CyclicBarrier).

#### **2.8 Exchanger**

- It is designed to **simplify the exchange of data between two threads**.
- The operation of an Exchanger is astoundingly simple: **it simply waits until two separate threads call its exchange() method**.
  - When that occurs, it exchanges the data supplied by the threads.

### **3. Executors**

Java concurrency provides executors as below.

- Executor Framework — define 3 executor object types.
- Thread Pools — are the most common way of executor implementation.
- Fork/Join Framework — From Java 7, It takes advantage of multiple processors.

#### **3.1 Executor Framework**

- Executor framework is a task scheduling framework. 
- It decouples creation and management of Threads from the rest of the application logic.
- It standardizes task invocation, scheduling, execution and control of asynchronous tasks according to a set of execution policies.

Executor Framework has the following executor interfaces, and implementations, and utilities.

- `Executor` - A simple and high level interface that supports launching new tasks.
- `ExecutorService` - A sub interface of `Executor`, that manages the life cycle of tasks and `Executor` itself. It supports asynchronous task execution.
- `ScheduledExecutorService` - A sub interface of `ExecutorService` that supports future and/or periodic tasks.
- `Executors` - A factory and utility class for `Executor`, `ExecutorService`, `ScheduledExecutorService`, `ThreadFactory` and `Callable`.
- Thread Pool Implementations - `ThreadPoolExecutor`, `ScheduledThreadPoolExecutor`.

#### **3.1.1 How to choose Ideal num. of Threads**

- **No. of CPUs any computer has**. If one CPU has two cores, then we should consider it as two CPUs. At most a CPU will run only one thread at a time. That means for one CPU with two cores, It can run two threads at time. There are some CPUs as hardware specification can run more than one thread. For example, one CPU with 4 core and each core can run 2 threads at a time. That means 4 2 2 = 16 threads we can run. 

- **Type of task a thread performs** such as I/O operation, network operation etc.
- **Desired fairness between threads**.

#### **3.2 ForkJoin Framework**

- Java 7 introduced the _Fork/Join_ framework to help speed up parallel processing by attempting to use all the available processor cores. 
- It follows **_divide and conquer_** approach. It means that the framework first forks recursively breaking tasks into smaller independent subtasks until they are simple enough to run asynchronously.
- After that, join part begins, the result of all subtasks will recursively join into a single result.
- Internally, it balances the workload of threads with the help of **Work Stealing Algorithm**. To Simply put, free threads try to “steal” work from deque's of busy threads.
- To provide effective parallel execution, It uses a pool of threads called `ForkJoinPool`. This pool manages worker threads of type `ForkJoinWorkerThread`.
- Worker threads can execute only one task at a time, but the `ForkJoinPool` doesn’t create a separate thread for every single subtask. Instead, each thread in the pool has its own **double-ended queue (or deque, pronounced “deck”)** that stores tasks.
- `ForkJoinTask` - Base type for tasks that are executed inside `ForkJoinPool`.
- `RecursiveAction` - for void tasks.
- `RecursiveTask<V>` - for tasks that return value.

There are two approaches to submit tasks to fork/join thread pool.
1. via `submit()` or `execute()`: Need to explicitly call join after the submit or execute operation.

`forkJoinPool.execute(customRecursiveTask);
int result = customRecursiveTask.join();`

2. via `invoke()`: It forks the task wait for the result, does not need any manual joining.

`int result = forkJoinPool.invoke(customRecursiveTask);`

### **4. Asynchronous Processing with `Future<T>` and `CompletableFuture<T>`**

#### **4.1 Future**

Java 5 introduced the interface `java.util.concurrent.Future<T>` as a non-blocking 
container type for an eventual result of an asynchronous computation.
To create a `Future`, a task in the form of a `Runnable` or a `Callable<T>` 
gets submitted to an `ExecutorService` which starts the task 
in a separate thread but immediately returns a Future instance. 
New `Future` instances are created by submitting tasks to an `ExecutorService` 
which returns an instance with its task already started.
This way, the current thread can continue to do more work without waiting for 
the eventual result of the `Future` computation.
The result is retrievable by calling the `get()` method on a `Future` instance, which might block the current thread, 
though, if the computation hasn’t finished yet.

#### **4.2 CompletionStage and CompletableFuture**

`CompletableFuture<T>` is an implementation of `CompletionStage<T>`. 
`CompletionStage<T>` represents a single stage of an asynchronous pipeline with a massive API.
Please note that `CompletableFuture<T>` does not require an explicit `ExecutorService` 
to schedule tasks as it provide several static methods.
`CompletableFuture<T>` (Implementation of `CompletionStage<T>`) has many advantages over `Future` such as:

- callback methods for completion or failure.
- Supports functional composition such as chaining and combining.
- Integrated error handling and recovery possibilities.
- Manual creation and completion of tasks without an `ExecutorService`.

`CompletableFuture` API is a _Promise_ by another name. 
_Promises_ are the building blocks for asynchronous pipelines with
- co-ordination tools that allow chaining and combining multiple tasks.
- includes error handling.
- Such a building block is either _pending_ (not settled), _resolved_ (settled/computation completed), _rejected_ (settled, but in error state).



</div>