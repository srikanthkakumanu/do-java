package collections;

import java.util.Iterator;
import java.util.List;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Basic example of all Collection<E> methods usage by using a ArrayList concrete implementation.
 */
public class TheCollection {
    public static void main(String[] args) {
        bulk();
    }

    /**
     * Examples of all standard/old methods of collection which
     * modify the original collection when used.
     * Note: Aggregate methods does not change the original collection.
     */
    private static void bulk() {
        Collection<Integer> c = List.of(2, 23, 43, 22, 43);
        Collection<Integer> some = List.of(23, 43);

        System.out.println(c.contains(2));
        System.out.println(c.containsAll(some));
        System.out.println(c.add(15));
        System.out.println(c.addAll(List.of(494, 38389)));
        
    }
    /**
     * Examples of all standard aggregate methods that collection provides.
     */
    private static void aggreateOps() {

        Collection<Integer> c = List.of(2, 23, 43, 22, 43);
        Collection<Integer> mirrors = List.of(23, 43);

        // filter and for-each
        c.parallelStream()
                .filter(e -> e != 2)
                .forEach(System.out::println);

        // map and collect
        String joiner = c.parallelStream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));

        System.out.println(joiner);

        // only reduce
        System.out.println(c.parallelStream().reduce(0, Integer::sum));


    }
}
