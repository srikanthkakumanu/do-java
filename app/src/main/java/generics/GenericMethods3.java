package generics;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class GenericMethods3 {
    public static void main(String[] args) {
        List<String> strings = List.of("Hello", "World");
        System.out.println(reverse(strings));
        System.out.println(strings);

        List<Integer> integers = List.of(1, 2, 3, 4, 5);
        System.out.println(reverse(integers));
        System.out.println(integers);

    }

    private static <T> List<T> reverse(List<T> original) {
        List<T> reversed = new ArrayList<>(original);
        Collections.reverse(reversed);
        return reversed;
    }
}
