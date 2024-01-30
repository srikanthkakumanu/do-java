<div text-align="justify">

# **Concepts and Features in Java**

## **Enhanced Switch**


Since Java 14, The traditional switch statement's enhanced further with these following additions.

1. **Switch expression**-- A switch expression is essentially that returns a value. One way to return a value of a switch expression is by using a `yield` statement.
2. **yield statement**--
   - `yield` terminates the switch immediately just like the break statement, **but the difference is that yield can return a value**.
   - In switch expression when `yield` is used, **the `break` statement is not allowed**.
   - `yield` is a context-sensitive keyword, means that outside the switch statement, `yield` is just an identifier and has no meaning.
3. Support for a list of case constants (**aka Case stacking**): -
   - From Java 14, traditional Case stacking can be enhanced by **writing all constants** with commas in a single case. I.e., case constant list. <br>
        Example: -
        ```java
            int priorityLevel;

            int eventCode = 6010;
            switch (eventCode) {
                case 1000, 1205, 8900: // case stacking
                    priorityLevel = 1;
                    break;
                case 2000, 6010, 9128:
                    priorityLevel = 2;
                    break;
                case 1002, 7023, 9300:
                    priorityLevel = 3;
                    break;
                default:
                    priorityLevel = 0;
            } ```
   
4. The case with an arrow (**case 'X' ->**):
     - **Differences: - (case 'X' :) vs. (case 'X' ->)**
       - arrow case does not fall through to the next case. Thus, no need to use the `break` statement.
       - The arrow case provides a **shorthand** way to supply a value when used in a switch expression.
       - the target of an arrow case can be:
         - case constant -> expression;
         - case constant -> { block of expression } - when using a block, you must use yield to supply a value to a switch expression.
         - case constant -> throw an exception.
       - We can avoid `yield` or `break` statements.
     - We cannot mix arrow cases with traditional colon (:) cases in the same switch. We must choose one or the other.

    ```java
         double surface = switch(shape) {
            case Circle(int radius) when radius > 0 ->
                    Math.PI * radius * radius;
            case Square(int edge) ->
                    edge * edge;
            case null, default -> 0d;
        };
    ```

## **Pattern Matching with instanceof**


In Java 14, an improved version of **`instanceof`** is introduced i.e., Pattern matching as an experimental feature and became permanent feature from Java 16.

Traditional `instanceof` approach:
1. For each conditional block, we are testing the animal parameter to determine its type, convert it via a cast and declaring a local variable. Then we can perform operations specific to that particular animal.
2. **Drawbacks:**
   - need to test the type and make a cast for every conditional block
   - repeat the type name three times for every if block.
   - Readability is poor
   - the problem magnifies each time we add a new animal.

Pattern Matching for **`instanceof`**:
1. It is a mechanism that determines if a value fits a general form. Pattern matching is used to test the type of value. A type must be a reference type.
2. This kind of pattern is called a type pattern. If the pattern matches, a pattern variable will receive a reference to the object matched by the pattern.
3. If `instanceof` succeeds, pattern variable will be created and contain a reference to the object that matches the pattern. If it fails, pattern variable is never created. </br>

Example: -

```text

abstract class Animal {}
final class Cat extends Animal {
    void meow() {}
}
final class Dog extends Animal {
    void woof() {}
}

// pattern matching for instanceof
        // Note: cat and dog are not an existing variable,
        // they are pattern variables.
        if(c instanceof Cat cat)
            cat.meow();
        if(d instanceof Dog dog)
            dog.woof();

        // another example
        Number n = Integer.valueOf(9);
        // traditional approach
        if(n instanceof Integer) {
            Integer num = (Integer) n;
            System.out.println(num);
        }
        // pattern matching approach
        if(n instanceof Integer num)
            System.out.println(num);

        // pattern variables can be used in logical AND expressions
        if((n instanceof Integer num) && (num >= 0))
            System.out.println("num is greater than 0");

        // It can also be used in loops
        Object[] obj = {"Alpha", "Beta", "Omega", Integer.valueOf(10)};
        for(int i = 0; ((obj[i] instanceof String s) && (i < obj.length)); i++) {
            System.out.println("Processing " + s);
        }

```
## **Regular Expressions**


A regular expression comprises normal characters, character classes (sets of characters), wildcard characters, and quantifiers.

#### **pattern matching or string matching**

| Pattern   | Description                                        |
|-----------|----------------------------------------------------|
| [abc]     | a,b or c                                           |
| [^abc]    | inverted - any character except a,b,c              |
| [a-z]     | A set of characters - any character between a to z |
| [A-Z]     | A set of characters - any character between A to Z |
| [a-z,A-Z] | a to z, A to Z                                     |
| [0-9]     | 0 to 9                                             |


#### **Quantifiers**

A quantifier determines how many times an expression is matched.

| Quantifier | Description                                    |
|------------|------------------------------------------------|
| +          | Match one or more.                             |
| *          | Match zero or more.                            |
| ?          | Match zero or one.                             |
| {n}        | Exactly _n_ times.                             |
| {n, m}     | Exactly _n_ times but not more than _m_ times. |


## **Sealed classes and interfaces**


1. The sealed classes and interfaces were added officially in Java 17.
   - It is a preview feature in Java 15 and 16.
   - They enable more fine-grained inheritance control in Java and do these checks at compile-time.
   - Sealing allows classes and interfaces to define their permitted subtypes.

2. Any normal/abstract class or interface can be declared as sealed.

3. If a sealed class/interface is declared,
   - A sealed class must have a subclass.
   - A subclass must implement a sealed interface.

4. A Sealed class/interface can restrict (permission to extend/implement) the inheritance/implementation by using permits keyword at declaration level.

5. The subclass/implementation class of a sealed class/interface must be either of final/sealed/non-sealed.
   - `final` means no further inheritance of that subclass/interface.
   - `sealed` means further it must have a subclass/implementation class and permits rule also can be applied.
   - Non-sealed means it is open for further inheritance or implementation.
     - Any normal class can extend `non-sealed` class.

6. We can also perform switch expression pattern matching with sealed classes/interfaces.
7. All permitted subclass/implementation classes must belong to the same module as the sealed class/interface.
8. Records also can implement sealed interfaces and note that records are implicitly final by default.
9. `sealed`, `non-sealed`, `permits` are context-sensitive keywords.

## **Records**

### Tuple

_Data aggregation_ is the process of gathering data from multiple sources and assembling it in a format. 
The most well known **data aggregation type is tuple**. 

**Tuple** — Mathematically speaking, a **tuple is a finite-ordered sequence of elements**. 
In programming, **a tuple is a data structure aggregating multiple values or objects**.<br> 

There are two kinds of tuples: **Structural tuples and Nominal tuples.**<br>

**Structural tuples** — rely on the order of the contained elements and are therefore accessible only via their indices.
(Python supports it)<br>
**Nominal tuples** — don't use an index to access their data but use component names.<br>

### Records

**Record is a plain data aggregator type, and the purpose is to carry just the data and no boilerplate code is required.**
They are **immutable data classes** that require only the type and field names and no setXXX() are generated. 
However, if a record holds a reference to some object, you can make a change to that object, but you cannot change to what object the
reference in the record refers. Thus, in Java terms, **records are said to be shallowly immutable**.

In short, **A record is a nominal tuple**. 
Like nominal tuples, Records aggregate an ordered sequence of values and provide access via names instead of indices.

Once the Record is declared — compiler generates:
- `equals()`, `toString()`, `hashCode()` methods.
- private and final fields.
- public canonical constructor (with fields defined at record declaration level).

#### Records—Canonical, compact and custom constructors

**Canonical Constructor** —
A constructor identical to the Record's component's definition is automatically generated/available, 
called the canonical constructor.<br>

**Compact Canonical Constructor** — A compact form of canonical constructor declaration.
- The constructor omits all the arguments, including the parentheses.
- Field assignments are not allowed in the compact canonical constructor, but you can customize or normalize data before it’s assigned.
- The components will be assigned to their respective fields automatically, no additional code required.
**Note: We can define either a standard canonical or compact canonical constructor but not both in the same record.**<br>

**Custom Constructors** — We can define several custom constructors by using standard form or in custom form but must start with an explicit invocation of the canonical constructor as its first statement.


1. By default, All record declarations implicitly inherit from `java.lang.Record`. So, **it does not support multiple inheritance**.
2. By default, all records create a canonical constructor with the arguments that are defined at declaration level. But we can override that canonical constructor.
3. We can **override the default canonical constructor with Compact constructor as well**. This is special to records only and useful to override constructors in more compact way.
4. We can override the default canonical constructor either by using a _standard canonical_ form or _compact canonical_ form but cannot define both in the same record.
5. We can define custom constructors, i.e., **multiple constructors with different combinations of arguments**.
6. Records **can implement interfaces**.
7. By default, **All records are _final_ hence it cannot be extended further**.
8. Once declared, **all the record fields are immutable hence cannot be changed**. Hence, **they are thread-safe and thereby, no synchronization is required**.
9. It allows **only static constants to be declared**. no instance level fields (non-static) are allowed. Any fields are needed, they should be declared as arguments at record declaration level.
10. A record also supports generics (it can be generic) i.e. it supports generic just like any other types. However, **A constructor cannot be generic, and it cannot include a _throws_ clause either at constructor or getter method level**.
11. We can create a record in step-by-step fashion by using a _Builder design pattern_.
12. Records are the best place to put validations and scrubbing logic. Throwing Exceptions is one way to go. Another option is to scrub the data and adjust component values with sensible alternatives to form a valid Record.
13. Records **can be Serialized** by using `java.io.Serializable`.
14. **RecordBuilder annotation** generates a flexible builder for any Record, and all you have to do is add a single annotation. Note: this is not part core JDK and Refer to https://github.com/randgalt/record-builder
15. **Dynamic tuples (Local Records)** — **This feature is missing in Java**. Other programming languages usually use those as dynamic data aggregators without requiring an explicitly defined type. **Java Records are simple data aggregators and can be considered nominal tuples. But we can use Java records as localized on-the-fly data aggregators (Local Records are records that are defined within a method)**. Contextually localized Records simplify and formalize data processing and bundle functionality.


Example: -

```java
import java.time.LocalDateTime;
import java.util.Objects;

public class Rough {
    public static void main(String[] args) {
        User u = new User("Bob", true);
        System.out.println(User.hello(User.MR) + u.name());

        Container<String> text = new Container<>("Hello, ", "this is a string container");
        String content = text.content();
    }

    record User(String name, boolean active, LocalDateTime lastLogin) {

        // We can define static constants
        public static final String MR = "Mr. ";
        public static final String MS = "Ms. ";

        /**
         * An Important Note:
         * A constructor cannot be generic.
         * It cannot include a "throws" clause either at constructor or getter method level.
         */
        /**
         * A Canonical constructor - that is automatically generated, but
         * we can override it to include additional code.
         */
//        public User(String name, boolean active, LocalDateTime lastLogin) {
//            Objects.requireNonNull(name);
//            Objects.requireNonNull(lastLogin);
//            this.name = name;
//            this.active = active;
//            this.lastLogin = lastLogin;
//        }
        /**
         * A compact canonical constructor - to avoid boiler plate code
         */
        public User {
            Objects.requireNonNull(name);
            Objects.requireNonNull(lastLogin);
            name = name.toLowerCase();
        }
        /**
         * A custom constructor but it must call default
         * canonical constructor as a first statement.
         */
        public User(String name, boolean active) {
            // It must call default canonical constructor as a first statement.
            this(name, active, LocalDateTime.now());
        }
        /**
         * We can define additional getter methods without compromising immutability
         */
        public String nameInUpperCase() { return name.toUpperCase(); }
        /**
         * We can define static getter methods without compromising immutability
         */
        public static String hello() {
            return "Hello, ";
        }

        public static String hello(String connotation) {
            return "Hello, " + connotation;
        }
    }

    /**
     * An Important Note:
     * A constructor cannot be generic.
     * It cannot include a "throws" clause either at constructor or getter method level.
     */
    record Container<T> (T content, String identifier) {}
}
```

</div>