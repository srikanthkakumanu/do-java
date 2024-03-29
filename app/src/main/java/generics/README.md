<div style="text-align: justify">

**What is a Generic?**

Generics introduced in J2SE 5. It allows a type (classes and interfaces) or method to operate on objects of various types while providing compile-time type safety. **Generics means Parameterized Types*and supports **Parameterized Polymorphism**.

**Why Generics?**

- It allows abstraction over types (classes and interfaces). They enable types (classes and interfaces) to be parameters when defining classes, interfaces and methods. These type parameters provide a way to re-use the same code with different inputs. The difference is that the input to formal/normal parameters are values; whereas the input to type parameters are types.
- It eliminates explicit type casting.
- provides compile-time type safety.
- It ensures no overhead during runtime while providing compile-time type safety. Because compiler applies a process called <I>**Eraser**</I> at compile-time on generic types.
- Generics does not work with primitive types such as int, long, double, char etc.
- When you define a class/interface with a generic then it is called <I>**Type parameter**</I> just like below, Here T is called type parameter.</br>
    `public class Box<T>`</br>
- When you use the defined type parameter, then it is called <I>**Type argument**</I>. Just like below, Here Integer is called type argument.</br>
    `Box<Integer> b = new Box<>()`

**Benefits of Generics**

- It  eliminates explicit type casts.
- Provide stronger type checks at compile-time.
- Enables the implementation of generic classes and  algorithms.


**Standard Generic conventions*(Not rules)

- E = Element (used in Collections framework)
- K = Key
- V = Value
- N = Number
- T = Type
- S, U, V = 2nd, 3rd, 4th types

**Raw Types**

- A <I>**raw type**</I> is the name of a class or interface without any type arguments **when we use a generic type (class or interface)**.
- **Note:-*A non-generic class or interface type is not a <I>**raw type**</I>.
- For backward compatibility, assigning a parameterized type to a <I>**raw type**</I> is allowed.

    Ex:- </br>
    ```
    // Generic Box class
    public class Box<T> {
        public void set(T t) { /*..*/ }
    }

    // To create parameterized type of Box<T>, you supply type argument Integer for T.
    Box<Integer> b = new Box<>();
    
    // to create a raw type of Box<T>
    Box rb = new Box()
    
    // Therefore, Box is the raw type of the generic type Box<T>
    ```
- 

**Generic Methods**

- Generic methods are methods that introduce their own type parameters. But its scope is limited with in the method. Both static, non-static methods can be generic methods and constructors as well.
- Generic methods a type<> (class or interface) parameter before the return type of method declaration.</br>
    Ex:- </br>`public <T> List<T> arrayToList(<T> a)`
- Generic methods can have more than one type parameter separated by commas in method signature.</br>
Ex:- </br>`public <T,G> List<T> arrayToList(<T> a, Function<T, G> mapper)`
- Method body for a generic method is just like any normal method.
- While using generic method, mentioning types is optional just like below. Because compiler uses <I>**type inference**</I> to identify the types.
    Ex:-
    ```
    // Can be used like this.
    GenericMethods2.<Integer, String>compare(p1, p2);
    // Or, Also can be used like below because of type inference
    GenericMethods2.compare(p1, p2)
    ```

**Bounded Types**

- In general, generic type parameter can accept any type (except primitive data types). However, if we wish to  restrict the generic type parameter to accept only specific types then we can use **Bounded Types*with the use <I>**extends**</I> keyword.
- Bounded type parameters are useful when you want to restrict the types that can be used as type arguments in a parameterized type.
- When we use <I>**extends**</I> keyword in Generics context, it extends a class (if the restricted type is a class) and implements an interface (if the restricted type is an interface).It means we can use that super class/interface methods within bounded type class. Ex:- `t.intValue();`
- A type parameter can have <I>multiple bounds</I> (Ex:-`<T extends B1 & B2 & B3>`).
- A type variable with multiple bounds is a subtype of all the types listed in the bound. If one of the bounds is a class, it must be specified first. 
- There are two types of bounded types, upper bound and lower bound.
- The upper bound we specify with <I>**extends**</I> keyword. (Ex:-`<T extends B1>`)
- The lower bound we specify with <I>**super**</I> keyword. (Ex:-`<T super B>`)

**Wild cards**
- ? is used as wild card for unknown type.

**Comparator and Comparable**

When working with custom types or trying to compare objects that are not directly comparable, we need to make use of a comparison strategy. The comparison strategy can be built simply by using `Comparator` and `Comparable` interfaces.

- `Comparable` interface used to define a strategy of comparing an object with other objects of the same type. This is called the class’s **“natural ordering.”**
</div>