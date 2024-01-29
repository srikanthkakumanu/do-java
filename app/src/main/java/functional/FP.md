<div text-align="justify">

# **Functional Programming in Java**

## **Functional Basics**

**_Lambda Calculus_*is a way to express computations with abstract functions and how to apply variables to them.
The below are pillars for the foundational concept for Lambda calculus.

- **Abstraction*- An anonymous function is a **_lambda_*that accepts a single input.
- **Application*- An abstraction (i.e. a lambda/anonymous function) is applied to a value to create a result. It is a function or method call.
- β**Reduction*- The substitute of the abstraction's variable with applied argument.

For example, an abstraction of a function that calculates a quadratic value **𝛌x.x*x*is identical to the java version mentioned below.

```java
Function<Integer, Integer> quadratic = value -> value * value;
```

There are some key principles for functional programming:

- First-class and higher-order functions
- Pure functions and Impure functions
- Referential transparency
- Immutability


## **First-Class and Higher Order Functions**

- **A first-class function is a function treated as first-class citizens**, i.e. treated like any other variable.
- A first-class function can be:
  - **Functions as an argument**- passed as an argument to another function. It can also be called as higher-order function.
  - **Function return functions**- can be returned as a result of a function. It can also be called as higher-order function.
  - **Functions as values**- assigned to a regular variable. It can also be stored as an array, object, map etc.
- A **higher-order function** is only possible because of first-class functions. It can either a function that receives another function as an argument or a function that returns a new function.

## **Pure functions and Referential transparency**

FP categorizes functions into two categories: _pure_ and _impure_.

**_Pure functions_ have two elemental guarantees**:
- **Deterministic**— The same input always creates the same output. The return value of a _pure_ function must solely depend on its input arguments.
- **No side effects*— They are self-contained without any kind of side effect. The code cannot affect the global or local state, like changing argument values or using any I/O. No side effects means, **it should be immutable**.

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

## **Referential Transparency**

Another aspect of side-effect-free expressions or pure functions is their deterministic nature, which makes them referentially transparent. That means you can replace them with their respective evaluated results for any further invocations without changing the behavior of your program. *For referential transparency, we need our functions should be pure and immutable. We call an expression referentially transparent if replacing it with its corresponding value has no impact on the program's behavior.*This enables powerful techniques such as higher-order functions and lazy evaluation.

Example of Referential transparency:
```text
Function: f(x) = x*x
Replacing evaluated expressions:
result = f(5) + f(5)
         25 + f(5)
         25 + 25
```

## **Immutability**

Immutability refers to the property that an entity can't be modified after being instantiated. In Java, data structures are not immutable by default. Thus, we have to create immutable data structures and Java provides several built-in immutable types.

- All fields of immutable data structure must be immutable.
- This must apply to all the nested types and collections (including what they contain) as well.
- There should be one or more constructors for initialization as needed.
- There should only be accessor methods, possibly with no side effects.



## **Functional Programming Techniques**

The above-mentioned functional programming principles enable us to use several techniques to benefit from functional programming.

- Function composition
- Monads
- Currying
- Recursion

## **Function Composition**

Pure functions can be combined to create more complex expressions. In mathematical terms, this means that the two functions `f(x)` and `g(y)` can be combined to a function `h(x) = g(f(x))`. This way, functions can be small and therefore easier to reuse.
Function composition means composing complex functions by combining simpler functions. In Java, it can be achieved by using functional interfaces, which are target types for lambda expressions and method references.
Any interface with single abstract method (SAM) can serve as a functional interface. Method chaining is the technique to compose multiple functions.


## **Monads**

A monad is just a monoid in the category of endofunctors.

- Many FP concepts derived from Category theory (A general theory of functions in Mathematics).
- Monad is an abstraction, which allows us to wrap a value, apply a set of transformations and get the value back with all transformations applied.
- Monads are containers or structures that encapsulate values and computations. It is an object that can map itself to different results based on transformations.

There are many monads in Java and some of them are Optional, Stream, CompletableFuture etc.

There are two types of Monads:

- **Unit Monads**: Represent a type that wraps a given value. ***this**operation is responsible for wrapping the value. In Java, ***this**operation can accept values from different types just by leveraging Generics.

- **Bind Monads**: It allows transformation to be executed using the held value and returns a new monad value (a value wrap in the monad type).

Every monad needs to obey three important laws:

- **left identity**: When applying to a monad, it should yield the same outcome as applying the transformation to the held value.
- **right identity**: When sending a monad transformation (convert the value to a monad), the yield outcome must be the same as wrapping the value in a new monad.
- **associativity**: When chaining transformations, it should not matter how transformations are nested.

## **Currying**

Function currying means converting a function from taking multiple arguments into a sequence of functions that each takes only a single argument. The currying technique borrows its name from the mathematician and logician Haskell Brooks Curry (1900–1982). He’s not only the namesake of the functional technique called currying, he also has three different programming languages named after him: Haskell, Brook, and Curry.

- Currying is a mathematical technique of converting a function that takes multiple arguments into a sequence of functions that take a single argument.
- It is a powerful function composition technique where we don’t need to call a function with all its arguments.
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



## **Recursion**

Recursion is a problem-solving technique that solves a problem by partially solving problems of the same form and combining the partial results to finally solve the original problem. In layperson’s terms, recursive functions call themselves, but with a slight change in their input arguments, until they reach an end condition and return an actual value.
Pure functional programming often prefers using recursion instead of loops or iterators. Some of them, like Haskell, go a step further and don’t have loops like `for` or `while` at all. The repeated function calls can be inefficient and even dangerous due to the risk of the stack overflowing. That’s why many functional languages utilize optimizations like “unrolling” recursion into loops or tail-call optimization to reduce the required stack frames. Java does not support any of these optimization techniques.

- It allows us to break down a problem into smaller pieces.
- The main benefit of recursion is that it helps to eliminate the side effects, i.e., looping (in imperative paradigm).
- **Head Recursion**:
    - Making the recursive call before calculating the result at each step or in words at the head of the calculation.
    - A **drawback for this type of recursion*is that every step has to hold the state of all previous steps until we reach the base case. This is not really a problem for small numbers, but holding the state for large numbers can be inefficient.
- **Tail Recursion**:
    - We ensure that the recursive call is the last call a function makes.
    - Java still does not have support for this tail-call recursion optimization.

## **Closures**

In many programming languages, **functions can return functions**. And when a function returns a function, the returning function keeps a reference to all the variables that it needs to execute, which are declared in the parent function.

That's exactly what a closure is: **It is a bucket of references to variables a function needs to execute which are declared outside its scope.**

## Advantages of functional programming

**Simplicity** — Without mutable state and side effects, our functions tend to be smaller. <BR>
**Consistency** — Immutable data structures are reliable and consistent.<BR>
**Correctness (Mathematical)** — Simpler code with consistent data structures will automatically lead to “more correct” code with a smaller bug surface. The “purer” your code, the easier it will be to reason with, leading to simpler debugging and testing. <BR>
**Modularity** — Small and independent functions lead to simpler usability and modularity. Combined with functional composition and partial application, you have powerful tools to build more complex tasks out of these smaller parts easily. <BR>
**Testability** — Many of the functional concepts, like pure functions, referential transparency, immutability, and the separation of concerns make testing and verification easier. <BR>

## Disadvantages of functional programming

**Learning Curve** — The advanced mathematical terminology and concepts that functional programming is based on can be quite intimidating. Nevertheless, you’re confronted with new and often unfamiliar terms and concepts. <BR>
**Dealing with state** — Handling state isn’t an easy task, regardless of the chosen paradigm. Even though FP’s immutable approach eliminates a lot of possible bug surfaces, it also makes it harder to mutate data structures if they actually need to change. <BR>
**Performance implications** — Despite their many benefits, many functional techniques, like immutability or recursion, can suffer from the required overhead. That’s why many FP languages utilize a plethora of optimizations to mitigate, like specialized data structures that minimize copying, or compiler optimizations for techniques like recursion. <BR>
**Optimal problem context** — Not all problem contexts are a good fit for a functional approach. Domains like high-performance computing, I/O heavy problems, or low-level systems and embedded controllers, where you need fine-grained control over things like data locality and explicit memory management, don’t mix well with functional programming. <BR>


</div>