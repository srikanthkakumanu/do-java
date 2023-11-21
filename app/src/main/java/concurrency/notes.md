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
3. Semaphore - Maintain the permits. The permits that control thread access to limited no. of shared resources.
4. Condition Object - Allow thread to block until a condition becomes true.
5. CountDownLatch - Allow one or more threads to wait until a set of operations to perform in other threads complete.
6. CyclicBarrier - Allow a set of threads to all wait for each other to reach a common barrier point.
7. Phaser - A more flexible reusable synchronization barrier. It is more flexible than CountDownLatch and CyclicBarrier.

<H4><B>2.1 Synchronized</B></H4>

Any Java object can be used a <B>Monitor</B> object:

- By marking methods (which require mutual exclusion aka Mutex) with synchronized keyword.
- By declaring any code block that are marked with synchronzied keyword.
- synchronized block ensure that only one thread can obtain a lock on a specific object (called Monitor).
- synchronized is not recommended to use with primitive classes/objects ex. Integer, String etc.
- We can declare static synchronized methods but lock applies only at class level. It means only one thread can access static synchronized objects/methods at a time for the whole class.
- We can declare static synchronized blocks and synchronized blocks in the same class but above rules apply.
- They can only block threads that are running within same JVM.

<B>Limitations of Synchonrized blocks</B>
- Only one thread can enter synchronized block at a time.
- No guarantee of sequence in which waiting threads gets access to the synchronized block.
- Performance overhead of entering and exiting of synchronized blocks.
    - Low overhead  - when sync. block is not already blocked.
    - High overhead - when sync. block is already locked by another thread. 

<H4><B>2.2 Locks</B></H4>

Locks are an alternative to basic synchronization.

There are 3 important Lock implementations, which are:
- ReentrantLock 
    - provides recursive locks. But both reads and writes under one lock.
    - boolean true value in the ReentrantLock(boolean fairness) provides lock fairness which may add additional overhead but ensures no lock congestion happens.
- ReentrantReadWriteLock 
    - More efficient than Reentrant lock as it provides separate locking mechanism for reads and writes. 
    - It improves performance when resources more read than writes.
    - It provides only <B>Pessimistic Locking</B>.
    - Lock downgrading (from write to read) is possible. But not read to write.
- StampedLock (From Java 8+) 
    - More efficient than above two locks.
    - It provides <B>Optimistic Locking</B>.

</div>