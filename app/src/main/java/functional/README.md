<div style="text-align: justify">

# **Functional programming in Java**

Functional programming (FP) uses **mathematical principles** to solve problems 
by utilizing a **declarative code style**.

Key to the understanding Java's functional programming/implementation 
of Lambda expressions are two constructs: **Lambda Expressions and Functional Interfaces**.


## 1. **Lambda Expressions**

**A Lambda expression is an anonymous (i.e. unnamed) method.** However, this method is not executed on its own. Instead, it is used to implement a method defined by a functional interface. Thus, a lambda expression results in a form of anonymous class. 

- **Lambda expressions are commonly referred to as Closures.**
- `->` is lambda operator.
- A lambda expression itself cannot specify type parameters. Thus, a lambda expression cannot be generic. But a functional interface can be generic. 
- A lambda expression can throw an exception. However, if it throws a checked exception, then it must be compatible with the exception mentioned at throws clause of abstract method in a functional interface.

### 1.1 **Method References (:: Operator)**

- A method reference provides a way to refer to a method without executing it. **It relates to lambda expressions because It too requires a target type context that consists of compatible functional interface.**
- Method references (:: operator) are special type of lambda expressions. They are often used to create simple lambda expressions by referencing existing methods.
- When evaluated, a method reference also creates an instance of the functional interface.

- There are four types of method references.
    1. **static methods**
    2. **Bounded non-static method reference** — If you want to refer to a non-static method of an already existing object, you need bounded non-static method reference. Bounded non-static method references are a great way to use already existing methods on variables, the current instance (this::) or superclass (super::). The lambda arguments are passed as the method arguments to the reference method of that specific object. You don't need an intermediate variable, you can combine the return value of another method call or field access directly with :: operator.
    3. **Unbounded non-static method reference** — Unbound non-static method reference not bound to a specific object, but they refer to an instance method of a type. 
    4. **Constructor reference**

## 2. **Functional Interface**

**A functional interface is an interface that contains one and only one abstract method.**
However, it can contain many _default, static, private_ methods. 
Furthermore, a functional interface defines the target type of lambda expression. 
A _functional_ interface is sometimes referred to as a _Single Abstract Method (SAM)_.

When a lambda expression occurs in a targeted context:
- An instance of a class is automatically created which implements the functional interface.
- With the lambda expression which defines the behavior for that abstract method of functional interface.
- When that abstract method is called, the lambda expression is executed. 
- Thus, **a lambda expression transform a code segment into a object.**


There are **four fundamental functional interfaces** in Java which are defined in `java.util.function` package.

However, there are some variations in addition to the above functional interfaces.

There are also some operator-based functional interfaces 
such as `UnaryOperator` (derived from `Function`), 
`BinaryOperator`(derived from `BiFunction`).

- **Consumer<T>** — It only accepts arguments and returns a result.
  It has a functional method (abstract method) `accept(T t)` that returns nothing i.e. void.
  It also has a default method `andThen(Consumer<T>)`.<BR>
    `Consumer<String> consumer = (s) -> System.out.println(s);`<BR>
  **Consumer Variations** — `BiConsumer`, `DoubleConsumer`, `IntConsumer`,
  `LongConsumer`, `ObjDoubleConsumer`, `ObjIntConsumer`, `ObjLongConsumer`.<BR>


- **Supplier<T>** — It does not accept any arguments and only returns a result.
  It has a functional method (abstract method) `get()` that has no arguments and returns a type.
  <BR>
    `Supplier<Double> supplier = () -> Math.PI;`<BR>
  **Supplier Variations** — `BooleanSupplier`, `DoubleSupplier`, `IntSupplier`, `LongSupplier`.<BR>


- **Function<T, R>** — It is a function that accepts arguments and returns a result.
  It has a functional method (abstract method) `R apply(T)`.
  T is the argument, and R is the return type.<BR>
    `Function<String, Integer> length = s -> s.length();`<BR>
  **Function Variations** — `BiFunction`, `DoubleFunction`, `DoubleToIntFunction`,
  `DoubleToLongFunction`, `IntFunction`, `IntToDoubleFunction`, `IntToLongFunction`,
  `LongFunction`, `LongToDoubleFunction`, `LongToIntFunction`, `ToDoubleBiFunction`,
  `ToDoubleFunction`, `ToIntBiFunction`, `ToLongBiFunction`, `TolongFunction`.<BR>


- **Predicate<T>** — A predicate means it is a boolean-valued function.
  Predicate accept arguments to test against an expression and return a boolean primitive as their result.
  A Predicate is a functional interface for testing conditions and has a functional method `boolean test(T t)`.
  T is the argument, and it returns a boolean.<BR>
    `Predicate<Integer> predicate = i -> i%2 == 0;`<BR>
**Predicate Variations** — `BiPredicate`, `DoublePredicate`, `IntPredicate`, `LongPredicate`.<BR>

## 3. **Optional**

`Optional<T>` is a _monad_ in Java.
We shouldn't rely solely on Optionals to handle Null values.
There are some prior techniques which are useful before the introduction
of Optional in Java.

Before `Optional<T>`, there are three different ways to handle Null values.

- Best practices
- Tool assisted Null checks
- Specialized types similar to Optional<T> — such as a rudimentary Optional type provided by Google Guava framework.


### 3.1 **Best Practices for handling Null**

- **Don't initialize a variable to Null** — Variables should always have a Non-Null value.
  The additional benefit is that it makes the variable effectively final if you don’t reassign it later,
  so you can use them as out-of-body variables in lambda expressions.
```text
// DON'T

String value = null;

if (condition) {
  value = "Condition is true";
} else {
  value = "Fallback if false";
}

// DO

String asTernary = condition ? "Condition is true"
                             : "Fallback if false";

String asRefactored = refactoredMethod(condition);

```
- **Don't pass/accept/return Null** — As variables should not be null,
  so should any arguments and return values avoid being null.
  Non-required arguments being Null can be avoided by overloading a method or constructor.
  If method signatures clash due to identical argument types, you can always resort to
  _static_ methods with more explicit names instead.
  After providing specific methods and constructors for non-mandatory values,
  you should not accept Null in the original ones if it’s appropriate.
  The easiest way to do this is using the static `requireNonNull()` method available on `Java.util.Objects`
```java
public record User(long id, String firstname, String lastname) {

  // DO: Additional constructor with default values to avoid null values
  public User(long id) {
    this(id, "n/a", "n/a");
  }
}
```
- **Null is acceptable as an implementation detail** — Avoiding null is essential for
  the public surface of your code but is still sensible as an implementation detail.
  Internally, a method might use null as much as needed as long as it won’t return
  it to the callee.


**Optional<T> is a safe wrapper around an actual value or a possible Null value
and supports functional call chains**.
The original design goal was to create a new type to support the _optional return idiom_,
meaning that it represents the result of a query or Collection access.
That behavior is clearly visible in the Optional-based terminal Stream operations.
Contrary to Streams, though, they are not lazily connected until a terminal-like
operation is added to the pipeline.
Every operation resolves as soon as it’s added to the fluent call.

## 4. **Exception Handling**

The following are the techniques that can be **used to circumvent the exceptions** in the functional paradigm of Java,  
however, all these options **are imperfect workarounds**
to mitigate exception handling in functional code and not the real solution.

- Safe method extraction
- Un-checking the exceptions
- Sneaky throws

### 4.1 **Safe Method Extraction**

**Safe method extraction** — Creating a “safe” method decouples the actual work
from handling any Exception, restoring the principle of the caller being responsible
for any checked Exceptions.
Any functional code can use the safe method instead.
It is an improvement over using try-catch blocks in a lambda
because you keep the expressiveness of inline lambdas and method references and have
a chance to handle any Exceptions.

Safe method extraction is akin to a more localized version of the _facade pattern_.
Instead of wrapping a whole class to provide a safer, context-specific interface,
only specific methods get a new facade to improve their handling for particular
use cases.
That reduces the affected code and still gives you the advantages of a facade,
such as reduced complexity and improved readability.
It’s also a good starting point for future refactoring efforts.

However, the actual caller of the method—the Stream operation—gets no chance to deal
with the Exception, making the handling opaque and inflexible.

```java
public class SafeMethodExtract {
    public static void main(String[] args) {
        SafeMethodExtract sme = new SafeMethodExtract();
        Stream<Path> paths = Stream.of(
                Path.of("C:\\books\\text1.txt"),
                        Path.of("C:\\books\\text2.txt"),
                        Path.of("C:\\books\\text3.txt"));
        paths.map(sme::safeRead)
                .filter(Objects::nonNull)
                .forEach(System.out::println);
    }

    String safeRead(Path path) {
        try {
            return Files.readString(path);
        } catch (IOException e) {
            //throw new RuntimeException(e);
            System.err.println("IOException thrown ");
            return null;
        }
    }
}
```

### 4.2 **Un-checking the exceptions**

**Un-checking the exceptions** — It is an approach that uses specialized functional interfaces
that use the _throws_ keyword to wrap the offending lambda or method reference.
It catches the original Exception and rethrows it as an unchecked RuntimeException,
or one of its siblings.

It is to deal with checked Exceptions goes against the
fundamental purpose of using checked Exceptions in the first place.
Instead of dealing with a checked Exception directly,
you hide it in an unchecked Exception to circumvent the catch-or-specify requirement.
It’s a nonsensical but effective way to make the compiler happy.

```java
public class UncheckExceptions {

    /**
     * Function interface - The wrapper extends the original type to act
     * as a drop-in replacement.
     */
    @FunctionalInterface
    interface ThrowingFunction<T, R> extends Function<T, R> {
        // This single abstract method (SAM) mimics the original but throws an Exception.
        R applyThrows(T element) throws Exception;

        /**
         *
         * This original SAM is implemented as a default method to wrap any Exception
         * as an unchecked RuntimeException.
         */
        @Override
        default R apply(T t) {
            try { return applyThrows(t); }
            catch (Exception e) { throw new RuntimeException(e); }
        }

        // A static helper to uncheck any throwing Function<T, R> to circumvent
        // the catch-or-specify requirement.
        static <T, R> Function<T, R> uncheck(ThrowingFunction<T, R> fn) {
            return fn;
        }
    }

    static String safeRead(Path path) {
        try {
            return Files.readString(path);
        } catch (IOException e) {
            //throw new RuntimeException(e);
            System.err.println("IOException thrown ");
            return null;
        }
    }
    public static void main(String[] args) {
        var paths = Stream.of(Path.of("C:\\books\\text1.txt"),
                Path.of("C:\\books\\text2.txt"),
                Path.of("C:\\books\\text3.txt"));

        // ThrowingFunction<Path, String> throwingFn = Files::readString;
        // Alternatively, can be done like below
        paths.map(ThrowingFunction.uncheck(Files::readString))
                .filter(Objects::nonNull)
                .forEach(System.out::println);
    }
}
```

### 4.3 **Sneaky throws**

**Sneaky throws** — Sneaky throws idiom is a hack to throw a checked exception without
declaring it with the _throws_ keyword in a method signature.

Instead of throwing a checked Exception using the _throw_ keyword in a method’s body,
which requires a _throws_ declaration in the method signature, the actual Exception
is thrown by another method.

Note:—Regardless of the actual type for the argument e,
the compiler assumes throws E to be a RuntimeException
and thereby exempts the method from the catch-or-specify requirement.
The compiler might not complain, **but this approach is highly problematic**.

### 4.4 **Right Approach — Functional way of handling exceptions**

To handle the exceptions in a functional way, **We can follow multiple approaches**.
Please refer the below examples for more details:

[TryFunction Example](https://github.com/srikanthkakumanu/do-java/blob/master/app/src/main/java/functional/exceptions/TryFunction.java) <BR>
[ResultTransformers Example](https://github.com/srikanthkakumanu/do-java/blob/master/app/src/main/java/functional/exceptions/ResultTransformers.java) <BR>
- Can use `java.util.concurrent.CompletableFuture`.
- **Define a Record with Generics** ex. `Result<V, E>` to deal with:
  - **Add Transformer methods to** `Result<V, E>`: Transform a value or Exception by declaring some dedicated map methods or a combined one to handle both use cases at once.
  - **Add certain state methods** to `Result<V, E>`: To react to a certain state such as success or exception scenarios, add _ifSuccess_, _ifFailure_, and _handle_ methods.
  - **Provide a fallback value to** `Result<V, E>`: add convenience methods for providing fallback values. add methods like _orElse_, _orElseGet_ and _orElseThrow_ (For _orElseThrow_, along with _sneakyThrow_ approach).
- The **Try/Success/Failure Pattern**: Scala way of dealing with exceptions by **defining a Scaffold type such as** `Try<T, R>`.
  - **Define a pipeline methods** for different scenarios such as _success_, _failure_, etc.
- Use an established functional third-party library like **vavr, or jOOλ (JOOL)** which provides a versatile `Try` type and much more. They are quite comprehensive and battle-tested implementations that are ready to use.

## 5. **Lazy Evaluation**

The strictness of a language describes the semantics of how your code is evaluated.
Most programming languages are neither fully lazy nor fully strict.
Java is considered a strict language, but with some noteworthy lazy exceptions 
on a language level and in the available types of the JDK.

**Laziness Vs. Strictness**

**_Strict_ evaluation** — happens as soon as possible, such as declaring or setting a variable 
or passing an expression as an argument.

**Lazy evaluation** — is defined as evaluating expressions only when their result is needed.
That means the declaration of an expression doesn't trigger its immediate evaluation.
Lazy or non-strict evaluation happens when the result of an expression is actually needed.
This way, expressions can have a value even if one or more sub-expressions fail to evaluate.

**Java, as a strict language, evaluates the expression immediately**. 
**Method arguments are always passed-by-value in Java**, which means 
they’re evaluated before being passed to the method. 
In the case of non-primitive types, arguments are passed as object handles by the JVM 
with a special type called _references_. 
These are technically still passed-by-value.

By using _lambda expressions_ in Java, it is possible to separate 
the _creation_ and _consumption_ of expressions.
**Therefore, Java's lambda expressions are the perfect match for lazy evaluation**.

Example:- 

```java
import java.util.function.IntSupplier;

public class LazyEvals {
    public static void main(String[] args) {

        // Java's standard/default strict evaluation
        try {
            var result = standardAdd(5, (1 / 0));
            System.out.println(result);
        } catch (ArithmeticException e) {
            System.out.println(e.getMessage()); }

        // lazy evaluation using suppliers aka lambda expressions
        var result = add(() -> 5, () -> 1 / 0);
        System.out.println(result);


    }

    /**
     * By default, Java does the strict evaluation.
     * The below method throws an exception even though
     * we don't use the y parameter argument in the code.
     * Therefore, lambda expressions are perfect candidates
     * for lazy evaluation.
     *
     * The declaration of the IntSupplier instances, or their inline equivalents,
     * is a strict statement and is evaluated immediately. The actual lambda body,
     * however, does not evaluate until it’s explicitly called with getAsInt,
     * preventing the ArithmeticException.
     */
    static int standardAdd(int x, int y) {
        return x + x;
    }
    /**
     * Lazy evaluation using Suppliers
     */
    static int add(IntSupplier x, IntSupplier y) {
        int actualX = x.getAsInt();
        return actualX + actualX;
    }
}
```

Apart from _lambda expressions_, Java has some other types which support the lazy evaluation.

**Short Circuit Evaluation**

_Logical short-circuit evaluation_ with the **logical operators** 
&& (double ampersand - _AND_) and || (double pipe - _OR_). 
These operators evaluate their operands left to right and only as required.
If the logical expression is satisfied by the expression left of the operator, 
the right operand isn’t evaluated at all.

**Note**:- The Bitwise operator — The similar _bitwise operators_ & (single ampersand) 
and | (single pipe) evaluate _eagerly_ and serve a different purpose than their 
logical brethren. 
Bitwise operators compare individual bits of integer types, resulting in an integer result.

**Control Structures**

Lazy structures in Java that support _lazy evaluation_.

| Branching control structures | Looping structures |
|------------------------------|--------------------|
| if-else                      | for                |
| ?: (ternary) operator        | while              |
| switch                       | do-while           |
| catch                        |                    |


**Lazy types**

**Lazy Maps** — `Map` type received a more concise and functional alternative with 
its `computeIf-` methods (Ex: - `computeIfAbsent` and `computeIfPresent`).

**Streams** — Java Streams are the perfect example of lazy functional pipelines. 
We can define an intricate Stream scaffold filled with expensive functional operations 
that will only start evaluation after calling a terminal operation.

**Optionals** — There are lazy operations available such as the `T orElseGet(Supplier<? extends T> supplier)` 
method that utilizes a `Supplier` to delay the execution to when it is necessary.



#  **Functional Programming (FP) Principles and Concepts**

## 4.1 Functional Basics

Functional programming (FP) uses **mathematical principles** to solve problems
by utilizing a **declarative code style**.

**_Lambda Calculus_** is a way to express computations with abstract functions and how to apply variables to them.
The below are pillars for the foundational concept for Lambda calculus.

- **Abstraction** — An anonymous function is a **_lambda_** that accepts a single input.
- **Application** — An abstraction (i.e. a lambda/anonymous function) is applied to a value to create a result. It is a function or method call.
- β**Reduction** — The substitute of the abstraction's variable with applied argument.

For example,
an abstraction of a function
that calculates a quadratic value **`𝛌x.x*x`** is identical to the java version 
mentioned below.

```java
Function<Integer, Integer> quadratic = value -> value * value;
```

There are some key principles for functional programming:

- First-class and higher-order functions
- Pure functions and Impure functions
- Referential transparency
- Immutability


## 4.2 **First-Class and Higher Order Functions**

- **A first-class function is a function treated as first-class citizens**, i.e. treated like any other variable.
- A first-class function can be:
  - **Functions as an argument** — passed as an argument to another function. It can also be called as higher-order function.
  - **Function return functions** — can be returned as a result of a function. It can also be called as higher-order function.
  - **Functions as values** — assigned to a regular variable. It can also be stored as an array, object, map etc.
- A **higher-order function** is only possible because of first-class functions. 
It can either a function that receives another function as an argument or a function that returns a new function.

## 4.3 **Pure functions and Referential transparency**

FP categorizes functions into two categories: _pure_ and _impure_.

**_Pure functions_ have two elemental guarantees**:
- **Deterministic** — The same input always creates the same output. The return value of a _pure_ function must solely depend on its input arguments.
- **No side effects** — They are self-contained without any kind of side effect. The code cannot affect the global or local state, like changing argument values or using any I/O. No side effects means, **it should be immutable**.

Example of a _Pure function_:
```java
// It accepts an argument without effecting anything outside its context
public String toLowerCase(String value) { return value; }
```
Example of an _Impure function_:
```java
// It is impure as it uses current time for its logic.
public String buildGreeting(String name) {
    var now = LocalTime.now();
    if(now.getHour() < 12) {
        return "Good Morning " + name;
    } else {
        return "Hello " + name;
    }
}
```

## 4.4 **Referential Transparency**

Another aspect of side-effect-free expressions or pure functions is their deterministic nature, which makes them referentially transparent. That means you can replace them with their respective evaluated results for any further invocations without changing the behavior of your program. *For referential transparency, we need our functions should be pure and immutable. We call an expression referentially transparent if replacing it with its corresponding value has no impact on the program's behavior.*This enables powerful techniques such as higher-order functions and lazy evaluation.

Example of Referential transparency:
```text
Function: f(x) = x*x
Replacing evaluated expressions:
result = f(5) + f(5)
         25 + f(5)
         25 + 25
```

## 4.5 **Immutability**

Immutability removes the risk of race conditions, side effects, or a simple unintended change. 
Immutability refers to the property that an entity can't be modified after being instantiated. In Java, data structures are not immutable by default. Thus, we have to create immutable data structures and Java provides several built-in immutable types.

- All fields of immutable data structure must be immutable.
- This must apply to all the nested types and collections (including what they contain) as well.
- There should be one or more constructors for initialization as needed.
- There should only be accessor methods, possibly with no side effects.

There are **three different immutable parts** available in JDK.

- Strings in Java
- Immutable collections
  - **Unmodifiable collections** — Creates an unmodifiable view by using `Collections.unmodifiableXXX()` methods.
  - **Immutable Collection factory methods (Java 9+)** — Create an immutable collection by using `of(...)` methods ex: `List.of()`.
  - **Immutable copies (Java 10+)** — They support deeper level of immutability by using `copyOf()` static method.
- Primitives and Primitive wrappers

### 4.5.1 **Strings In Java**

Strings are immutable in Java and are not a primitive value-based type.
JVM applies various optimization techniques when string operations are performed.

**String concatenation**<BR>

String concatenation (Using +) is now optimized from Java 9 onwards. 
Each time a string is concatenated or modified, a new object is allocated at heap level.
It uses **`invokedynamic`(aka Indy)** rather than the `StringBuilder`. 

**String constant pooling for String Literals**<BR>

When we create a String object using the new() operator, it always creates a new object in heap memory. 
On the other hand, if we create an object using String literal syntax (e.g. “Bob”), 
then it may return an existing object from the String pool if it already exists. 
Otherwise, it will create a new String object and put in the string pool for future re-use.


Identical string literals are stored only once in a special memory region
called **String constant pool** by the JVM and reused to save precious heap space. 


If a string could change, it would change for everyone using a reference to it in the pool. 
To avoid this impact, 
we can either create a new string by using constructor instead of creating a literal to circumvent string pooling. 


Another technique is
to use `intern()` method on any instance so that it returns a string with the same content from the string pool. 

**Before Java 7**, the JVM placed the Java String Pool in the PermGen space, 
which has a fixed size — it can’t be expanded at runtime and is not eligible 
for garbage collection.
The risk of interning Strings in the _PermGen_ (instead of the _Heap_) 
is that we can get an `OutOfMemory` error from the JVM if we intern too many Strings.


**From Java 7** onwards, the Java String Pool is stored in the _Heap space_, 
which is garbage collected by the JVM. 
The advantage of this approach is the reduced risk of `OutOfMemory` error 
because unreferenced Strings will be removed from the pool, thereby releasing memory.


**Until Java 8**, Strings were internally represented as an array of characters – char[], encoded in UTF-16, so that every character uses two bytes of memory.


**With Java 9** a new representation is provided, called **Compact Strings**. 
This new format will choose the appropriate encoding between char[] and byte[] 
depending on the stored content.
Since the new String representation will use the _UTF-16_ encoding only when necessary, 
the amount of heap memory will be significantly lower, which in turn causes less 
Garbage Collector overhead on the JVM.


## 4.6 **Functional Programming Techniques**

The above-mentioned functional programming principles enable us to use several 
techniques to benefit from functional programming.

- Function composition
- Monads
- Currying
- Recursion

## 4.7 **Function Composition**

Pure functions can be combined to create more complex expressions.
In mathematical terms,
this means that the two functions `f(x)` and `g(y)` can be combined to a 
function `h(x) = g(f(x))`.
This way, functions can be small and therefore easier to reuse.
Function composition means composing complex functions by combining simpler functions.
In Java, it can be achieved by using functional interfaces,
which are target types for lambda expressions and method references.
Any interface with single abstract method (SAM) can serve as a functional interface.
Method chaining is the technique to compose multiple functions.
Creating default methods in functional interfaces and static helper classes 
for functional interfaces is another approach
to accommodate function composition.


## 4.8 **Monads**

A monad is just a _monoid_ in the category of _endofunctors_.

- Many FP concepts derived from Category theory (A general theory of functions in Mathematics).
- Monad is an abstraction, which allows us to wrap a value, apply a set of 
transformations and get the value back with all transformations applied.
- Monads are containers or structures that encapsulate values and computations. 
It is an object that can map itself to different results based on transformations.

There are many monads in Java and some of them are `Optional`, `Stream`, `CompletableFuture` etc.

There are two types of Monads:

- **Unit Monads** — Represent a type that wraps a given value. 
**this** operation is responsible for wrapping the value. 
In Java, **this** operation can accept values from different types just by 
leveraging Generics.

- **Bind Monads**: It allows transformation to be executed using the held value 
and returns a new monad value (a value wrap in the monad type).

Every monad needs to obey three important laws:

- **left identity** — When applying to a monad, it should yield the same outcome 
as applying the transformation to the held value.

- **right identity** — When sending a monad transformation (convert the value to a monad), 
the yield outcome must be the same as wrapping the value in a new monad.

- **associativity** — When chaining transformations, it should not matter how 
transformations are nested.

## 4.9 **Currying**

Function currying means converting a function from taking multiple arguments into 
a sequence of functions that each takes only a single argument. 
The currying technique borrows its name from the mathematician and 
logician Haskell Brooks Curry (1900–1982). 
He’s not only the namesake of the functional technique called currying, 
he also has three different programming languages named 
after him: Haskell, Brook, and Curry.

- Currying is a mathematical technique of converting a function that takes 
multiple arguments into a sequence of functions that take a single argument.
- It is a powerful function composition technique where we don’t need to 
call a function with all its arguments.
- A curried function does not realize its effect until it receives all the arguments.
- **Currying depending on the language to provide two fundamental features: lambda expressions and closures**.

Examples:

Initial function: `x = f(a, b, c)` <BR>

Curried functions:
```text
                    h = g(a)
                    i = h(b)
                    x = i(c)
```

Sequence of curried functions: `x = g(a)(b)(c)`



## 4.10 **Recursion**

Recursion is a problem-solving technique that solves a problem by partially 
solving problems of the same form and combining the partial results to 
finally solve the original problem. 

Another definition is that Recursion is an approach to solving a problem that
can be broken down into smaller versions of itself.

In layperson’s terms, recursive functions call themselves, but with a slight 
change in their input arguments, until they reach an end condition and return 
an actual value.
Pure functional programming often prefers using recursion instead of 
loops or iterators. 
Some of them, like Haskell, go a step further and don’t have loops 
like `for` or `while` at all. 
The repeated function calls can be inefficient and even dangerous due to the 
risk of the stack overflowing and may result in throwing `StackOverflowError`. 
That’s why many functional languages utilize optimizations like “unrolling” 
recursion into loops or tail-call optimization to reduce the required stack frames. 
Java does not support any of these optimization techniques.

- It allows us to break down a problem into smaller pieces.
- The main benefit of recursion is that it helps to eliminate the side effects, i.e., looping (in imperative paradigm).
- Recursive calls fall into two categories _head_ and _tail_ recursion, **depending on the location of the recursive call in the method body**.
- The main **difference between _head_ and _tail_ recursion is how the call stack is constructed**.
- **Head Recursion**:
  - Making the recursive call before calculating the result at each step or in words at the head of the calculation.
  - A **drawback for this type of recursion** is that every step has to hold the state of all previous 
  steps until we reach the base case. 
  This is not really a problem for small numbers, but holding the state for large 
  numbers can be inefficient.
- **Tail Recursion**:
  - We ensure that the recursive call is the last call a function makes.
  - Java still does not have support for this tail-call recursion optimization.

**Stack Frame**

A stack frame contains the state of a single method invocation. 
Each time your code calls a method, the JVM creates and pushes a new frame on the 
thread’s stack. 
The default stack size of most JVM implementations is one _megabyte_. 
You can set a bigger stack size with the flag `-Xss`.
After returning from a method, its stack frame gets popped and discarded.
The actual maximum stack depth depends on the available stack size, and what’s 
stored in the individual frames.

**StackOverflowError**

Each time recursion method is called, it is considered as a separate method call and, 
therefore, a new stack frame on the call stack. 
The problem is, though, that the available stack size is finite. 
Too many calls will fill up the available stack space and 
eventually throw a `StackOverflowError`. 
To prevent the stack from overflowing, many modern compilers use 
_tail-call optimization/elimination_ to remove no-longer-required frames in 
recursive call chains. 
If no additional calculations take place after a recursive call, 
the stack frame is no longer needed and can be removed. 
That reduces the stack frame 
space complexity of the recursive call from `O(N)` to `O(1)`, resulting in faster 
and more memory-friendly machine code without an overflowing stack.
Sadly, the Java compiler and runtime lack that particular ability, as of early 2023.


## 4.11 **Closures**

In many programming languages, **functions can return functions**. And when a function returns a function, the returning function keeps a reference to all the variables that it needs to execute, which are declared in the parent function.

That's exactly what a closure is: **It is a bucket of references to variables a function needs to execute which are declared outside its scope.**

## 4.12 **Advantages of functional programming**

**Simplicity** — Without mutable state and side effects, our functions tend to be smaller. <BR>
**Consistency** — Immutable data structures are reliable and consistent.<BR>
**Correctness (Mathematical)** — Simpler code with consistent data structures will automatically lead to “more correct” code with a smaller bug surface. The “purer” your code, the easier it will be to reason with, leading to simpler debugging and testing. <BR>
**Modularity** — Small and independent functions lead to simpler usability and modularity. Combined with functional composition and partial application, you have powerful tools to build more complex tasks out of these smaller parts easily. <BR>
**Testability** — Many of the functional concepts, like pure functions, referential transparency, immutability, and the separation of concerns make testing and verification easier. <BR>

## 4.13 **Disadvantages of functional programming**

**Learning Curve** — The advanced mathematical terminology and concepts that functional programming is based on can be quite intimidating. Nevertheless, you’re confronted with new and often unfamiliar terms and concepts. <BR>
**Dealing with state** — Handling state isn’t an easy task, regardless of the chosen paradigm. Even though FP’s immutable approach eliminates a lot of possible bug surfaces, it also makes it harder to mutate data structures if they actually need to change. <BR>
**Performance implications** — Despite their many benefits, many functional techniques, like immutability or recursion, can suffer from the required overhead. That’s why many FP languages utilize a plethora of optimizations to mitigate, like specialized data structures that minimize copying, or compiler optimizations for techniques like recursion. <BR>
**Optimal problem context** — Not all problem contexts are a good fit for a functional approach. Domains like high-performance computing, I/O heavy problems, or low-level systems and embedded controllers, where you need fine-grained control over things like data locality and explicit memory management, don’t mix well with functional programming. <BR>

## 4.14 **Memorization**

Memorization is a form of caching that accelerates the performance of repetitive recursive operations. In FP, it can be implemented
as a generic wrapper for any pure function. 

Memoization is a technique used in FP to speed up computer programs by eliminating the repetitive computation of results,
and by avoiding repeated calls to functions that process the same input.

## 4.15 **Tuples**

_Data aggregation_ is the process of gathering data from multiple sources and assembling it in a format.
The most well known **data aggregation type is tuple**.

**Tuple** — Mathematically speaking, a **tuple is a finite-ordered sequence of elements**.
In programming, **a tuple is a data structure aggregating multiple values or objects**.<br>

There are two kinds of tuples: **Structural tuples and Nominal tuples.**<br>

**Structural tuples** — rely on the order of the contained elements and are therefore accessible only via their indices. (Python supports it)<br>
**Nominal tuples** — don't use an index to access their data but use component names.

In Java, **Record is a nominal tuple**.

# 5. **Functional Design Patterns**

Functional principles allow the removal of a lot of the boilerplate code 
usually required by many object-oriented design patterns.

The most commonly used design patterns in functional paradigm are:

| Category   | Patterns             |
|------------|----------------------|
| Creational | _Factory_, _Builder_ |
| Structural | _Decorator_          |
| Behavioral | _Strategy_           |


</div>




