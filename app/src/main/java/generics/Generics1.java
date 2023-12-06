package generics;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

// Generic class
class GenericClass<T> {
    private T data;

    public GenericClass(T data) { this.data = data; }

    public T getData() { return this.data; }
}

public class Generics1 {
    public static void main(String[] args) {
        // Initialize the generic class with Integer data
        GenericClass<Integer> igc = new GenericClass<>(5);
        System.out.println(igc.getData());

        // Initialize the generic class with String data
        GenericClass<String> sgc = new GenericClass<>("Hello Generics!");
        System.out.println(sgc.getData());

        // ? stands for unknown type or any type
        GenericClass<?> any = new GenericClass<>("Funny Generics!");
        System.out.println(any.getData());
        any = new GenericClass<>(480.383);
        System.out.println(any.getData());

        // Another example of Generics
        List<Integer> ints = GenericList.toList(1, 2, 3);
        List<String> words = GenericList.toList("hello", "world");
        List<Double> doubles = GenericList.toList(1.23, 2.43, 43.53);
        System.out.println(ints);
        System.out.println(words);
        System.out.println(doubles);
    }
}

class GenericList {
    public static <T> List<T> toList(T... array) {
        List<T> list = new ArrayList<>();
        for( T element : array) 
            list.add(element);
        return list;
    }
}