# **Functional programming in Java**

## **Functional Interface**


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










