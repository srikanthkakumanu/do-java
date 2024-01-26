package functional;

import java.util.function.BiFunction;

public class TriFunctions {
    public static void main(String[] args) {
        // Bi function takes two args
        BiFunction<Integer, Integer, Integer> addTwo = Integer::sum;
        System.out.println(addTwo.apply(199, 299));

        // custom tri function takes three args
        TriFunction<Integer, Integer, Integer, Integer> addThree = (x, y, z) -> x + y + z;
        System.out.println(addThree.apply(10, 20, 30));
    }
}

@FunctionalInterface
interface TriFunction<T, U, V, R> {
    R apply(T t, U u, V v);
}
