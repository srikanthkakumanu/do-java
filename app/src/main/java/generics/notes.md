<div style="text-align: justify">

**What is a Generic?**

Generics introduced in J2SE 5. It allows a type (classes and interfaces) or method to operate on objects of various types while providing compile-time type safety.

**Why Generics?**

- It allows abstraction over types (classes and interfaces). They enable types (classes and interfaces) to be parameters when defining classes, interfaces and methods. These type parameters provide a way to re-use the same code with different inputs. The difference is that the input to formal/normal parameters are values; whereas the input to type parameters are types.
- It eliminates explicit type casting.
- provides compile-time type safety.
- It ensures no overhead during runtime while providing compile-time type safety. Because compiler applies a process called **Eraser** at compile-time on generic types.
- Generics does not work with primitive types such as int, long, double, char etc.

**Generic Methods**

- Generic methods a type<> (class or interface) parameter before the return type of method declaration.</br>
    Ex:- </br>`public <T> List<T> arrayToList(<T> a)`
- Generic methods can have more than one type parameter separated by commas in method signature.</br>
Ex:- </br>`public <T,G> List<T> arrayToList(<T> a, Function<T, G> mapper)`
- Method body for a generic method is just like any normal method.


</div>