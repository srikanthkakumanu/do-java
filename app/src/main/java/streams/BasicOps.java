package streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.LinkedList;
import java.util.Set;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Collector;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class BasicOps {
    public static void main(String[] args) {
        aggregateOps();
    }

    /**
     * Basic stream operations such as:
     * count
     * of
     * parallel stream
     * distinct
     * filter
     * Atomic operation (concurrency)
     * map
     * collect
     * reduce
     */
    private static void aggregateOps() {
        String[] ar = new String[] {"a", "b", "c", "d", "e", "a", "b", "c", "d", "e"};
        // Basic stream creation
        Stream<String> stream = Arrays.stream(ar);
        System.out.println(stream.count());

        stream = Stream.of(ar);
        // stream = Stream.of("a", "b", "c");
        System.out.println(stream.count());

        // Multithreaded i.e. parallel streams
        Stream.of(ar).parallel().forEach(e -> System.out.println(e));
        Stream.of(ar).parallel().forEachOrdered(e -> System.out.println(e));

        // Basic stream ops
        stream = Stream.of(ar);
        System.out.println(stream.distinct().count());

        // parallel streams
        Collection<Book> books = new ArrayList<>();
        books.add(new Book("Thousand Lights in the Sea", "Ten Tsu", 1923));
        books.add(new Book("Art of War", "Ten Tsu", 1932));
        books.add(new Book("A Autobiograph of a Monk", "Swami Yogananda", 1915));
        books.add(new Book("The Berlin Downfall", "Antony Beevor", 1965));
        books.add(new Book("Hampi - a Forgotten Empire", "Fernio Nunez", 1970));

        AtomicLong countOfBooks = new AtomicLong();
        books.parallelStream().forEach(
            book -> {
            if(book.getYearPublished() == 1915)
                countOfBooks.incrementAndGet();
        });

        System.out.printf("Total Count of Books for 1915: %d \n", countOfBooks.get());


        // distinct + count operation
        List<Integer> nums = List.of(32, 53, 32, 34, 32, 21, 43, 53);
        long distinctCount = nums
                                .stream() // source of data
                                .distinct() // intermediate operation - which returns Stream<T>
                                .count(); // terminal operation - which gives a definite result
        System.out.println(distinctCount);

        boolean isExist = nums
                            .stream() // source of data
                            .anyMatch(e -> e == 32); // takes a Predicate and returns definite result
        System.out.println(isExist);

        // filter operation
        ArrayList<String> list = new ArrayList<>();
        list.add("One");
        list.add("OneAndOnly");
        list.add("Derek");
        list.add("Change");
        list.add("factory");
        list.add("justBefore");
        list.add("Italy");
        list.add("Italy");
        list.add("Thursday");
        list.add("");
        list.add("");

        Stream<String> sm = list.stream().filter(v -> v.contains("d"));
        sm.forEachOrdered(System.out::println);

        // map operation
        list.stream()
            .filter(v -> v.contains("d"))
            .map(v -> v.concat("###"))
            .forEachOrdered(System.out::println);
        

        // flatmap operation
        List<List<String>> strList = Arrays.asList(
                                            Arrays.asList("A"), 
                                            Arrays.asList("B"));
        
        strList.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList()).stream().forEachOrdered(System.out::println);;

        // match operations
        System.out.println(list.stream().anyMatch(v -> v.contains("h")));
        System.out.println(list.stream().allMatch(v -> v.contains("h")));
        System.out.println(list.stream().noneMatch(v -> v.contains(("h"))));

        System.out.println(Stream.empty().anyMatch(Objects::nonNull));

        // reduce operation
        nums = List.of(2, 3, 4);
        // System.out.println(nums.stream().reduce(1, (a, b)-> {
        //     int result = (a + b);
        //     System.out.printf("[a = %d, b = %d, result = %d] \n", a, b, result);
        //     return result;
        // }));
        System.out.println(nums.stream().reduce(1, (a, b) -> a + b));

        // map + reduce operation
        // System.out.println(nums.stream()
        //     .map(v -> {
        //         int result = v + 2;
        //         System.out.printf("\nmap - [v = %d, result(v+2) = %d] \n", v, result);
        //         return result;
        //     })
        //     .reduce(1, (a, b)-> {
        //         int result = (a + b);
        //         System.out.printf("\nreduce - [a = %d, b = %d, result = %d] \n", a, b, result);
        //         return result;
        //     }));
        System.out.println(nums.stream().map(v -> v + 2).reduce(1, (a, b) -> a + b));
        
        /*
         * reduce() can have three params: 
         * - identity = initial value for an accumulator
         * - accumulator = A function to aggregate elements
         * - combiner = A function to aggregate results of accumulator
         */
        OptionalInt optInt = IntStream
                                .rangeClosed(1, 5)
                                .reduce((a, b) -> a + b);
        System.out.println(optInt.getAsInt());
        
        System.out.println(IntStream
                                .rangeClosed(0, 5)
                                .reduce(10, (a, b) -> a + b));

        int vv = Stream.of(1, 2)
                        .reduce(10, 
                                (a, b) -> a + b, 
                                (a, b) -> {
                                    return a + b;
                                });
        System.out.println("vv = " + vv);
        
        // collect operation
        List<Product> products = Arrays.asList(
                                                new Product(10, "potatoes"), 
                                                new Product(12, "Orange"), 
                                                new Product(22, "Bread"),
                                                new Product(12, "Lemon"));
        
        products.stream()
                .map(Product::getName)
                .collect(Collectors.toList())
                .forEach(System.out::println);

        System.out.println(products.stream()
                .map(Product::getName)
                .collect(Collectors.joining(", ", "[", "]")));
        
        System.out.println(products.stream().collect(Collectors.averagingInt(Product::getPrice)));
        System.out.println(products.stream().collect(Collectors.summingInt(Product::getPrice)));
        System.out.println(products.stream().collect(Collectors.summarizingInt(Product::getPrice)));

        // grouping
        Map<Integer, List<Product>> groupByPrice = products.stream()
                                                            .collect(Collectors.groupingBy(Product::getPrice));
        groupByPrice.entrySet().forEach(System.out::println);

        // partitioning
        Map<Boolean, List<Product>> partitionByPrice = products.stream()
                                                                .collect(Collectors
                                                                            .partitioningBy(element -> element.getPrice() > 15));
        partitionByPrice.entrySet().forEach(System.out::println);

        Set<Product> unmodifiableSet = products.stream()
                                                    .collect(Collectors.collectingAndThen(Collectors.toSet(),
                                                                                                        Collections::unmodifiableSet));
        
        // custom collector - convert to LinkedList
        Collector<Product, ?, LinkedList<Product>> toLinkedList =
                                                Collector.of(LinkedList::new, LinkedList::add, 
                                                                (first, second) -> { 
                                                                        first.addAll(second); 
                                                                        return first; 
                                                                });
        LinkedList<Product> linkedListOfProducts = products.stream().collect(toLinkedList);                                                                                                        
                                
    }
}

@Data
@AllArgsConstructor
class Product {
    private int price;
    private String name;

}

@Data
@AllArgsConstructor
class Book { 
    private String name, author;
    private int yearPublished;
}