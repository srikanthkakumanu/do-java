I<div style="text-align: justify">

## **Java Collections**

</div>

A **Collection** is a container - is an object that groups multiple elements/objects into a single unit. It is used to store, retrieve, modify and aggregate data.

A Collection Framework is a unified architecture for representing and manipulating collections. It contains interfaces (abstract types), concrete implementations, algorithms (such as searching and sorting).

The core collection interfaces are foundation to the framework.

**Iterable** - Root interface for enhanced for-each.

**Map** - Root interface for all key-value pair and it is a not a true collection. **SortedMap** extends from Map.

**Collection** - It extends from **Iterable** and root interface for all collections such as **List, Set, Queue, Deque**. All core collection interfaces are ***generic types***.

## **Collection**

* The root interface for all collections such ***List, Set, Queue and Deque***.
* It extends from ***Iterable***.
* All core collection interfaces are ***generic types***.

## **List**

* It is an **ordered** collection (sometimes called **sequence**).
* Allows **duplicates**.
*

## **Set**

* It is an **Un-ordered** collection.
* Does **not allow duplicates**.
* It models the mathematical set abstraction.

## **SortedSet**

* It is extended from Set.
* It is an **Ordered** collection unlike standard Set mentioned above.
* It maintain elements in **ascending order**.
* Does **not allow duplicates**.
*

## **Queue**

It is **ordered** collection in FIFO manner.

It order elements in **FIFO (First-In and First-Out)** manner except the elements natural order or Priority Queues.

Along with standard collection interface methods, it provides additional insertion, removal, retrieval/inspection operations.

head - Removes an element via `remove()` and `poll()` methods.

tail - Insert new elements at the tail of the queue via `offer()` and `add()` methods.

Get/Offer - retrieve/get an element via `element()` and `peek()` methods.

## **Deque (Double ended Queue)**

* It is **ordered** collection in both FIFO and LIFO manner.
* It can be used **both as FIFO (First-In and First-Out)** and **LIFO (Last-In and First-Out)**.
* Along with standard collection interface methods, it provides additional insertion, removal, retrieval/inspection operations.
* All new elements **can be inserted, removed, retrieved from both ends**.

## **Map**

* It maps keys to values.
* It is **not ordered**.
* It does **not allow duplicate keys**.


## **SortedMap**

* It is extended from Map.
* It is **ordered for keys unlike standard Map mentioned above**Sl.
* It does **not allow duplicate keys**.
