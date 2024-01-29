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


Record purpose is to carry just the data and no boilerplate code is required. It is commonly referred to as **an aggregate type**.
Records are immutable data classes that require only the type and field names.
No setXXX() are generated because all **records are immutable**. 
However, if a record holds a reference to some object, you can make a change to that object, but you cannot change to what object the
reference in the record refers. Thus, in Java terms, **records are said to be shallowly immutable**.

Once Record is declared - compiler generates the equals(), toString(), hashCode() methods, private and
final fields, and a public canonical constructor (with fields defined at record declaration level).

1. By default, All record declarations **implicitly inherit from Record**. So, **no more extends are allowed as Java does not support multiple inheritance**.
2. By default, all records create a canonical constructor with the arguments that are defined at declaration level. But we can override that canonical constructor.
3. We can **override the default constructor with Compact constructor**. This is special to records only and useful to override constructors in more compact way.
4. We can **define multiple constructors with different combinations of arguments**.
5. Records **can implement interfaces**.
6. By default, **All records are final hence it cannot be extended further**.
7. Once declared, **all the record fields are immutable hence cannot be changed**. Hence, **they are thread-safe and thereby, no synchronization is required**.
8. It allows **only static constants to be declared**. no instance level fields (non-static) are allowed. Any fields are needed, they should be declared as arguments at record declaration level.
9. A record can be generic i.e. it supports generic just like any other types. However, **A constructor cannot be generic, and it cannot include a throws clause either at constructor or getter method level**.


</div>