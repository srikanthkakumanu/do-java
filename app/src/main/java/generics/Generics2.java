package generics;

import java.util.List;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;
import generics.model.Pair;
import generics.model.OrderedPair;
import generics.model.Box;

public class Generics2 {

    public static void main(String[] args) {

        // first example
        Integer[] a = {1,2,3,4, 5};    
        List<String> sList = Generics2.arrayToList(a, Object::toString);
        System.out.println(sList);

        // second example for multiple type parameters
        Pair<String, Integer> p1 = new OrderedPair<>("Even", 8);
        Pair<String, String> p2 = new OrderedPair<>("hello", "world");
        
        // third example for parameterized types
        // We can substitute a type parameter (i.e. K or V) with a 
        // parameterized type (i.e. List<String>) like below.
        OrderedPair<String, Box<Integer>> op = new OrderedPair<>("primes", new Box<Integer>());
        op.getValue().set(10);
        System.out.println(op.getValue().get());
    }

    public <T> List<T> arrayToList(T[] a) {
        return Arrays.asList(a);
    }

    public static <T, G> List<G> arrayToList(T[] a, Function<T, G> mapper) {
        return Arrays.stream(a)
                    .map(mapper)
                    .collect(Collectors.toList());
    }
}



