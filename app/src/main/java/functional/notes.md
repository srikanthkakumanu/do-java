<div style="text-align: justify">

# **Functional programming in Java**

Key to the understanding Java's functional programming/implementation of Lambda expressions are two constructs: Lambda Expressions and Functional Interfaces.


## **Lambda Expressions**

**A Lambda expression is an anonymous (i.e. unnamed) method.** However this method is not executed on its own. Instead, it is used to implement a method defined by a functional interface. Thus, a lambda expression results in a form of anonymous class. 

- **Lambda expressions are commonly referred to as Closures.**
- `->` is lambda operator.
- A lambda expression itself cannot specify type parameters. Thus, a lambda expression cannot be generic. But a functional interface can be generic. 
- A lambda expression can throw an exception. However if it throws a checked exception, then it must be compatible with the exception that is mentioned at throws clause of abstract method of functional interface.

### **Method References (:: Operator) **

- A method reference provides a way to refer to a method without executing it. **It relates to lambda expressions because It too requires a target type context that consists of compatible functional interface.**
- When evaluated, a method reference also creates an instance of the functional interface.
- Method references are special type of lambda expressions. They are often used to create a simple lamdba expressions by referencing existing methods.
- There are four types of method references.
    1. static methods
    2. Instance methods of particular objects / Reference to an instance method of an object
    3. Instance methods of an orbitrary object of a particular type
    4. Constructor reference


## **Functional Interface**

**A functional interface is an interface that contains one and only one abstract method.** However, it can contain many default, static, private methods. Furthermore, a functional interface defines the target type of a lambda expression. A funtional interface is sometimes referred to as a Single Abstract Method (SAM).

When a lambda expression occurs in a targeted context:
- An instance of a class is automatically created which implements the functional interface.
- With the lambda expression which defines the behaviour for that abstract method of functional interface.
- When that abstract method is called, the lambda expression is executed. 
- Thus, **a lambda expression transform a code segment into a object.**


There are **four fundamental functional interfaces** in Java which are defined in java.util.function package.

However, there are some variations in addition to above functional interfaces.

There are also some operator based functional interfaces such as UnaryOperator (derived from Function), BinaryOperator(derived from BiFunction).

- **Consumer\<T>:-** It has a functional method (abstract method) `accept(T t)` that returns nothing i.e. void. It also has a default method `andThen(Consumer<T>)`.<BR>
    `Consumer<String> consumer = (s) -> System.out.println(s);`<BR>
  **Consumer Variations** - BiConsumer, DoubleConsumer, IntConsumer, LongConsumer, ObjDoubleConsumer, ObjIntConsumer, ObjLongConsumer.

- **Supplier\<T>:-** It has a functional method (abstract method) `get()` that has no arguments and returns a type. <BR>
    `Supplier<Double> supplier = () -> Math.PI;`<BR>
  **Supplier Variations** - BooleanSupplier, DoubleSupplier, IntSupplier, LongSupplier.

- **Function\<T, R>:-** It has a functional method (abstract method) `R apply(T)`. T is the argument and R is the return type.<BR>
    `Function<String, Integer> length = s -> s.length();`<BR>
  **Function Variations** - BiFunction, DoubleFunction, DoubleToIntFunction, DoubleToLongFunction, IntFunction, IntToDoubleFunction, IntToLongFunction, LongFunction, LongToDoubleFunction, LongToIntFunction, ToDoubleBiFunction, ToDoubleFunction, ToIntBiFunction, ToLongBiFunction, TolongFunction.

- **Predicate\<T>:-** A predicate means it is a boolean valued function. It has a functional method `boolean test(T t)`. T is the argument and it returns a boolean.<BR>
    `Predicate<Integer> predicate = i -> i%2 == 0;`<BR>
**Predicate Variations** - BiPredicate, DoublePredicate, IntPredicate, LongPredicate.



## **Functional Programming (FP) concepts**

### **Closures**

In many programming languages, **functions can return functions**. And when a function returns a function, the returning function keeps a reference to all the variables that it needs to execute, which are declared in the parent function. 

Thats exactly what a closure is: **It is a bucket of references to variables a function needs to execute which are declared outside its scope.**

</div>




