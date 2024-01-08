<div style="text-align: justify">

# **Functional programming in Java**

Key to the understanding Java's functional programming/implementation of Lambda expressions are two constructs: Lambda Expressions and Functional Interfaces.


## **Lambda Expressions**

**A Lambda expression is an anonymous (i.e. unnamed) method.** However this method is not executed on its own. Instead, it is used to implement a method defined by a functional interface. Thus, a lambda expression results in a form of anonymous class. 

- **Lambda expressions are commonly referred to as Closures.**
- `->` is lambda operator.
- A lambda expression itself cannot specify type parameters. Thus, a lambda expression cannot be generic. But a functional interface can be generic. 
- A lambda expression can throw an exception. However if it throws a checked exception, then it must be compatible with the exception that is mentioned at throws clause of abstract method of functional interface.

### **Method References (:: Operator)**

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



## **Functional Programming (FP) Principles and Concepts**

There some some key principles for functional programming:

- First-class and higher-order functions
- Pure functions
- Immutability
- Referential transparency


### **First-Class and Higher Order Functions**

- **A first-class function is a function that is treated as first-class citizens**, i.e. treated like any other variable. 
- A first-class function can be: 
    - **Funcions as an argument** - passed as a argument to another function. It can also be called as higher-order function.
    - **Functions return funtions** - can be returned as a result of a function. It can also be called as higher-order function.
    - **Functions as values** - assigned to a regular variable. It can also be stored as a array, object, map etc.
- A **higher-order function** is only possible because of first-class functions. It can either a function that receives another function as an argument or a function that returns a new function.

### **Pure functions**

A pure function should be deterministic, means that it should return a value based only on the arguments and should not have any side-effects (means it should be immutable). side-effects can be as simple as updating a local or global state. 

### **Immutability**

Immutability refers to the property that an entity can't be modified after being instantiated. In Java, data structures are not immutable by default. Thus, we have to create immutable data structures and Java provides several built-in immutable types.

- All fields of immutable data structure must be immutable.
- This must apply to all the nested types and collections (including what they contain) as well.
- There should one or more constructors for initialization as needed.
- There should only be accessor methods, possibly with no side-effects.

### **Referential Transparency**

**For referential transparency, we need our functions should be pure and immutable. We call an expression referentially transparent if replacing it with its corresponding value has no impact on the program's behavior.** This enables powerful techniques such as higher-order functions and lazy evaluation.

## **Functional Programming Techniques**

The above mentioned functional programming principles enable us to use several techniques to benefit from functional programming.

- Function composition
- Monads
- Currying
- Recursion

### **Function Composition**

Function composition means composing complex functions by combining simpler functions. In Java, it can be achieved by using functional interfaces, which are target types for lambda expressions and method references.
Any interface with single abstract method (SAM) can serve as a functional interface. Method chaining is the technique to compose multiple functions.

### **Monads**

- Many FP concepts derived from Category theory (A general theory of functions in Mathematics). 
- Monard is an abstraction, which allows us to wrap a value, apply set of transformations and get the value back with all transformations applied.
- Monads are containers or structures that encapsulate values and computations. It is an object that can map itself to different results based on transformations.

There are many monads in Java and some of them are Optional, Stream, CompletableFuture etc.

There are two types of Monads:

- **Unit Monads**: Represent a type that wraps a given value. ***this*** operation is responsible for wrapping the value. In Java, ***this*** operation can accept values from different types just by leveraging Generics.

- **Bind Monads**: It allows transformation to be executed using the held value and returns a new monad value (a value wrap in the monad type).

Every monad need to obey three important laws:

- **left identity**: When applying to a monad, it should yield the same outcome as applying the transformation to the held value. 
- **right identity**: When sending a monad transformation (convert the value to a monad), the yield outcome must be the same as wrapping the value in a new monad.
- **associativity**: When chaining transformations, it should not matter how transformations are nested.

### **Currying**

- Currying is a mathematical technique of converting a function that takes multiple arguments into a sequence of functions that take a single argument.
- It is a powerful function composition technique where we don’t need to call a function with all its arguments.
- A curried function does not realize its effect until it receives all the arguments.
- **Currying depending on the language to provide two fundamental features: lambda expressions and closures**.

### **Recursion**

- It allows us to breakdown a problem into smaller pieces.
- The main benefit of recursion is that it helps to eliminate the side effects i.e. looping (in impertive paradigm).
- **Head Recursion**: 
  - Making the recursive call before calculating the result at each step or in words at the head of the calculation.
  - A **drawback of this type of recursion** is that every step has to hold the state of all previous steps until we reach the base case. This is not really a problem for small numbers, but holding the state for large numbers can be inefficient.
- **Tail Recursion**:
  - We ensure that the recursive call is the last call a function makes.
  - Java still does not have support for this tail-call recursion optimization.

### **Closures**

In many programming languages, **functions can return functions**. And when a function returns a function, the returning function keeps a reference to all the variables that it needs to execute, which are declared in the parent function. 

Thats exactly what a closure is: **It is a bucket of references to variables a function needs to execute which are declared outside its scope.**


</div>




