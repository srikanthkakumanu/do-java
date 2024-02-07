<div style="text-align: justify">

# **Java Streams**

Java streams functionality is introduced in Java 8.
It contains classes for processing sequences of elements. 
The base interface is `Stream<T>`. 

Streams can be created from different element sources e.g.,
collections or arrays with the help of `stream()` and `of()` methods.

Streams can be summed up as _**lazy sequential data pipelines**_. 
Such pipelines are a higher-level abstraction for traversing sequential data. 
They are sequences of higher-order functions to process their elements in a fluent, expressive, and functional way.

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

## **Stream Advantages**

Streams as a data processing approach and having **an internal iterator**,
has some advantages from functional point of view.

**Declarative approach** — Builds multistep data processing pipelines with a fluent call chain.

**Composability** —
Stream operations provide a scaffold made of higher-order functions to be filled with data processing logic.

**Laziness** —
The most significant advantage of Streams over loops is their laziness.
Instead of iteration over all elements,
they get pulled one by one through the pipeline after the last operation is attached to it,
reducing the required number of operations to a minimum.
Each time you call an intermediate operation on a Stream, it’s not applied immediately.
Instead, the call simply “extends” the pipeline further and returns a new lazily evaluated Stream.
The pipeline accumulates all operations, and no work starts before you call its terminal operation.
The flow of Stream elements follows a **depth-first** approach, reducing the required CPU cycles, memory footprint, and stack depth. This way, even infinite data sources are possible because the pipeline is responsible for requesting the required elements and terminating the Stream.

**Performance optimization** —
Streams optimize the traversal process automatically depending on their data source and different kinds of operations used, including short-circuiting operations if possible.
The flow of Stream elements follows a **depth-first** approach, reducing the required CPU cycles, memory footprint, and stack depth. This way, even infinite data sources are possible because the pipeline is responsible for requesting the required elements and terminating the Stream.

**Parallel data processing** — Built-in support for parallel processing is used by simply changing a single call in the call chain.


## **Stream Operations**

Stream processing has three stages. 
Please note that stream operations do not change the source of data. 
Once created, the instance will not modify its source, 
therefore allowing the creation of multiple instances from a single source.

1. Source — A source can be a collection, array, or sequences of elements.
2. Intermediate operations — They return `Stream<T>` after processing the source of data. They support method chaining. Note that **all intermediate operations are Lazy** i.e. they will be invoked only if it is necessary for the terminal operation execution.
3. Terminal operations — They return a result of definite type. They fall into four categories such as Reductions (also called as _fold_ operations), Aggregations (also called as **mutable reduction operations**), Finding and matching and Consuming.

### **Stream-Intermediate Operations**


**`map()` vs. `multiMap()`**

**`<R> Stream<R> mapMulti(BiConsumer<T, Consumer<R>> mapper)`** — 

- The `BiConsumer` takes a Stream element T, if necessary, transforms it into type R, 
and invokes the mapper’s `Consumer::accept()`. 
- In `mapMulti()`, **the _mapper_ is a buffer** that implements the Consumer functional interface. 
- Each time we invoke `Consumer::accept`, 
**it accumulates the elements in the buffer and passes them to the stream pipeline**.  
- Comparing with `map()`, **`mapMulti()` 
implementation is more direct since we don’t need to invoke so many stream intermediate operations**.
- **Another advantage is that the mapMulti implementation is imperative, 
giving us more freedom to do element transformations**.
- We should use `mapMulti()` (instead of `map()`), **when replacing each stream element with a small (possibly zero) number of elements. Using this method avoids the overhead of creating a new Stream instance for every group of result elements, as required by flatMap**. <BR> The imperative implementation of `mapMulti()` is more performant — we don’t have to create intermediate streams with each processed element as we do with the declarative approach of `flatMap()`.

### **Stream-Terminal Operations**

Terminal operations — They return a result of definite type. 
They fall into four categories such as — 

- **Reductions** — They are also called as **_fold_** operations or **immutable reduction operations**.
- **Aggregations** — They are also called as **mutable reduction operations**. The `Stream` terminal operation `collect()` accepts a `Collector` to aggregate elements. Instead of reducing elements by combining Stream elements to a single result by repeatedly applying an _accumulator_ operator, these operations use a _mutable results container_ as an intermediate data structure.
- **Finding and matching**
- **Consuming**

**Reducing Vs. collecting elements**

The terminal operations `reduce` and `collect` are two sides of the same coin:
**both are reduction—or fold—operations**. 
The difference lies in the general approach to recombining the results: **immutable versus mutable accumulation**. 
This difference leads to quite different performance characteristics.

The rule of thumb of choosing which type of reduction is —
choose `collect`  (mutable) if the result is a Collection-based type,
like List or Map; choose `reduce` (immutable) if the result is an accumulated single value.

### **Downstream Collectors**

The **downstream collector** is a collector that accepts another collector as an argument.
The `java.util.stream.Collectors` factory methods accept an additional collector to manipulate downstream elements.
It is almost like a secondary stream pipeline working on previously collected elements.

Downstream collector tasks include:

- Transform
- Reduce
- Flattening
- Filtering
- Composite Collector operations

**Composite Collectors** 

**Teeing** — 
It differs from others because **it accepts two downstream collectors at once and combines both results into one**. 
The name teeing originates from one of the most common pipe fittings the **T-fitting** which has the shape of a capital letter T.
The Stream’s elements **first pass through both downstream Collectors, 
so a BiFunction can merge both results as the second step**.

</div>