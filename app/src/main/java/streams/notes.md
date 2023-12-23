<div style="text-align: justify">

# **Java Streams**

Java streams functionality are introduced in Java 8. `java.util.stream` contains classes for processing sequences of elements. The base interface is `Stream<T>`. 

Streams can be created from different element sources e.g. collections or arrays with the help of `stream()` and `of()` methods.

## **Stream Operations**

Stream processing has three stages. Please note that stream operations do not change the source of data.

1. Source:- A source can be a collection, array, or sequences of elements.
2. Intermediate operations:- They return `Stream<T>` after processing the source of data. They support method chaining.
3. Terminal operations:- They return a result of definite type. 
</div>