package collections.functional;

import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.UnaryOperator;
import java.util.function.BinaryOperator;

/**
 * There are four fundamental functional interfaces in Java 8.
 * Consumer, Supplier, Function, Predicate
 */
public class FunctionalInterfaces {
    public static void main(String[] args) {
        consumer();        
        supplier();
        function();
        predicate();

        // Consumer variation interfaces examples
        BiConsumer<String, String> biCon = (s1, s2) -> System.out.println(s1 + s2);
        biCon.accept("Hello", "World");

        // Function variation interfaces examples
        BiFunction<String, String, Integer> biFunc = (s1, s2) -> s1.indexOf(s2);
        System.out.println(biFunc.apply("Hello World", "World"));

        // Predicate variation interfaces examples
        BiPredicate<Integer, Integer> bip = (i1, i2) -> i1 > i2;
        System.out.println(bip.test(21, 20));

        // Operator based variation interfaces examples
        UnaryOperator<String> unary = s -> s.toUpperCase();
        System.out.println(unary.apply("Prince Leonor"));

        BinaryOperator<String> binary = (s1, s2) -> s1 + ", " + s2;
        System.out.println(binary.apply("Funny", "Examples"));
    }

    /**
     * Demonstrates the examples of Predicate functional interface.
     */
    private static void predicate() {
        System.out.println("Predicate Functional Interface examples Executed..");
        Predicate<Integer> p = i -> i%2 == 0;
        System.out.println(p.test(323));
    }

    /**
     * Demonstrates the examples of Function functional interface.
     */
    private static void function() {
        System.out.println("Function Functional Interface Examples Executed..");
        Function<String, Integer> f = (i) -> i.length();
        System.out.println(f.apply("Hello World"));
    }

    /**
     * Demonstrates the examples of Supplier functional interface.
     */
    private static void supplier() {
        System.out.println("Supplier Functional Interface Examples Executed..");
        Supplier<Double> sp = () -> Math.PI;
        System.out.println(sp.get());

        Supplier<Integer> sp2 = () -> Integer.MAX_VALUE;
        System.out.println(sp2.get());
    }

    /**
     * Demonstrates the examples of Consumer functional interface.
     */
    private static void consumer() {
        System.out.println("Consumer Functional Interface Examples Executed..");
        // 1st example
        // Consumer<String> c1 = (x) -> System.out.println(x.toLowerCase());
        // c1.andThen(c1).accept("Completed");

        // 2nd example
        // Consumer<String> first = x -> System.out.printf("\nFirst Executed - Passed Value: %s, Processed to: %s\n", x, x.toLowerCase());
        // Consumer<String> second = y -> System.out.printf("\nSecond Executed - Passed Value: %s, Processed to: %s\n", y, y.toUpperCase());
        // Consumer<String> result = first.andThen(second);
        // result.accept("Srikanth");

        // 3rd Example
        class Message<T> {
            private T t;

            public Message(T t) { this.t = t; }
            public T getMessage() { return t; }
            public String toString() { return getMessage().toString(); }
        }        

        Consumer<Message<?>> mc = (x) -> System.out.println("MC Executed - Passed Value: " + x);
        Consumer<Message<?>> ec = (y) -> System.out.println("EC Executed - Passed Value: " + y);

        Message<Double> m = new Message<>(23.99);
        mc.andThen(ec).accept(m);

        Message<String> s = new Message<>("Consumer");
        ec.andThen(mc).accept(s);
    }


}
