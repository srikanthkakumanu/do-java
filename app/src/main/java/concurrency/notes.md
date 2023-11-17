<div style="text-align: justify">
<H2>Concurrency Notes</H2>


<H3><B>1. Thread Management</B></H3>

<B>interrupt(), join(), yield() are basic mechanisms to communicate among threads.</B>

<H4><B>1.2 Thread Local</B></H4>
- ThreadLocal variables maintain a local copy of a value per thread so that the value is not shared/visible across multiple threads.

- <B>ThreadLocal is way of providing thread safety instead of using synchronized blocks.</B> Synchronized blocks still share the data (as the variables can be accessed via object across threads) but ThreadLocal variables are not shared.

- InheritableThreadLocal is available to share the variable state across parent and child threads (which are created within a parent thread).

- Cleanup is necessary when ThreadLocal variables are using along with ThreadPool. Because ThreadPool reuses the same threads and previous state of ThreadLocal still preserved. Hence the cleanup/removal is required. ExecutorService (ThreadPoolExecutor) gives an option of beforeExecute() and afterExecute() methods to do the necessary.

<H3><B>2. Thread Synchronization</B></H3>

<H4><B>2.1 Synchronized</B></H4>

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
</div>