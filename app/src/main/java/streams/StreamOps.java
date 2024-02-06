package streams;

import java.io.File;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.DoubleStream;
import java.util.regex.Pattern;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.charset.Charset;
import java.util.stream.Collectors;

public class StreamOps {
    public static void main(String[] args) throws IOException {
        // Different ways to create a stream
        Stream<Integer> nums = Stream.empty(); // empty stream
        Stream<String> words = Arrays.asList("A", "B", "C").stream(); // from collection
        words = Stream.of("X", "Y", "Z"); // from array directly
        // create a stream out of an existing array or part of an array
        String[] str = new String[] {"a", "b", "c", "d", "e"};
        words = Arrays.stream(str);
        words = Arrays.stream(str, 0, 2);
        words.forEach(System.out::println);

        // builder
        Stream<String> sBuilder = Stream.<String>builder()
                                        .add("x")
                                        .add("y")
                                        .add("z")
                                        .build();

        // Generator to generate same value 10 times
        Stream<String> sGenr = Stream.generate(() -> "A").limit(20);
        sGenr.forEach(System.out::println);

        // create an infinite stream using iterator
        nums = Stream.iterate(1, v -> v * 2).limit(20);
        nums.forEach(System.out::println);

        // create stream with primitive stream types
        // range - does not include last value, it is just an upper bound
        // rangeClosed - includes last value as well
        IntStream ints = IntStream.range(1, 11);
        LongStream longs = LongStream.rangeClosed(12, 31);
        ints.forEach(System.out::println);
        longs.forEach(System.out::println);

        // Generate Stream using a Random
        Random rand = new Random();
        DoubleStream doubles = rand.doubles(20); // likewise ints(), longs() were also provided
        doubles.forEach(System.out::println);

        // create IntStream from characters
        ints = "abcdef".chars();

        // breaks a string to sub string with a regex
        Stream<String> regexStream = Pattern.compile(", ").splitAsStream("A, B, C, D, E, D, C, Z, X, Y, X, Z");
        regexStream.forEach(System.out::println);

        // Stream from a file
        System.out.println(System.getProperty("user.dir"));
        Path p = Paths.get(System.getProperty("user.dir")
                .concat("\\app\\src\\main\\java\\streams\\")
                .concat("README.md"));

        //Stream<String> sLines = Files.lines(p);
        Stream<String> sLinesWithCharset = Files.lines(p, Charset.forName("UTF-8"));
        sLinesWithCharset.forEach(System.out::println);
        List<String> matched = Stream.of("A", "B", "C", "D", "E", "C", "F", "C")
                                        .filter(e -> e.contains("C"))
                                        .collect(Collectors.toList());
        Optional<String> any = matched.stream().findAny();
        Optional<String> first = matched.stream().findFirst();
        System.out.println(any.get() + first.get());

        // some more examples
        bookExample();
        shapeExample();
    }

    private static void bookExample() {
        enum Genre { DYSTOPIAN, HORROR, SCIENCE_FICTION; }
        record Book(String title, int year, Genre genre) {}

        List<Book> bks = new ArrayList<>(
                List.of (new Book("Dracula", 1897, Genre.HORROR),
                        new Book("Brave New World", 1932, Genre.DYSTOPIAN),
                        new Book("1984", 1949, Genre.DYSTOPIAN),
                        new Book("Dune", 1965, Genre.SCIENCE_FICTION),
                        new Book("Do Androids Dream of Electric Sheep", 1968, Genre.SCIENCE_FICTION),
                        new Book("The Shining", 1977, Genre.HORROR),
                        new Book("Neuromancer", 1984, Genre.SCIENCE_FICTION),
                        new Book("The Handmaid's Tale", 1985, Genre.DYSTOPIAN)));

        // Collections.sort(books, Comparator.comparing(Book::title));
        bks.sort(Comparator.comparing(Book::title));

        // traditional approach
//        List<String> result = new ArrayList<>();
//
//        for(var book : books) {
//            if(book.year() >= 1970)
//                continue;
//
//            if(book.genre() != Genre.SCIENCE_FICTION)
//                continue;
//
//            var title = book.title();
//            result.add(title);
//
//            if(result.size() == 3) break;
//        }
//
//        result.forEach(System.out::println);

        // Functional approach
        var result = bks.stream()
                .filter(book -> book.year() >= 1970)
                .filter(book -> book.genre() == Genre.SCIENCE_FICTION)
                .map(Book::title)
                .sorted()
                .limit(3)
                .toList();

        result.forEach(System.out::println);

    }
    private static void shapeExample() {
        List<Shape> shapes = List.of(
                Shape.circle(), Shape.triangle(), Shape.square(),
                Shape.triangle(), Shape.circle(), Shape.square(),
                Shape.square(), Shape.triangle(), Shape.circle(),
                Shape.square(), Shape.square(), Shape.square(),
                Shape.triangle(), Shape.triangle(), Shape.triangle()
        );

        System.out.println("%%%%%----SELECTING ELEMENTS----%%%%%\n\n");
        System.out.println("\n-----Filtering----\n\n");
        var result = shapes.stream().filter(Shape::hasCorners);
        result.forEach(System.out::println);

        System.out.println("\n-----dropwWhile----\n\n");
        result = shapes.stream().dropWhile(Shape::hasCorners);
        result.forEach(System.out::println);

        System.out.println("\n-----takeWhile----\n\n");
        result = shapes.stream().takeWhile(Shape::hasCorners);
        result.forEach(System.out::println);

        System.out.println("\n-----limit----\n\n");
        result = shapes.stream().limit(2);
        result.forEach(System.out::println);

        System.out.println("\n-----skip----\n\n");
        result = shapes.stream().skip(5);
        result.forEach(System.out::println);

        System.out.println("\n-----distinct----\n\n");
        result = shapes.stream().distinct();
        result.forEach(System.out::println);

        System.out.println("\n-----sorted----\n\n");
        result = shapes.stream().sorted();
        result.forEach(System.out::println);

        System.out.println("%%%%%----MAPPING or TRANSFORMING ELEMENTS----%%%%%\n\n");
        System.out.println("\n-----map - corner Count----\n\n");
        var mapResult = shapes.stream().map(Shape::corners);
        System.out.println(mapResult.count());

        System.out.println("\n-----flatMap----\n\n");
        Stream<Shape> flatMap =
                Stream.of(Shape.square(), Shape.triangle(), Shape.circle())
                                .map(Shape::twice)
                                        .flatMap(List::stream);
        flatMap.forEach(System.out::println);

        System.out.println("%%%%%----PEEKING INTO STREAM----%%%%%\n\n");
        System.out.println("\n-----peek----\n\n");
        // peek mainly used for debugging purposes to peek inside each element
        var anotherResult = Stream.of(Shape.square(), Shape.triangle(), Shape.circle())
                .map(Shape::twice)
                .flatMap(List::stream)
                .peek(shape -> System.out.println("Current: " + shape))
                .filter(shape -> shape.corners() < 4)
                .collect(Collectors.toList());
        anotherResult.forEach(System.out::println);

        // - Comparing with map(), mapMulti() implementation is more direct
        // since we don’t need to invoke so many stream intermediate operations.
        //- Another advantage is that the mapMulti implementation is imperative,
        //giving us more freedom to do element transformations.

        System.out.println("\n-----mapMulti----\n\n");
        Stream<Shape> mapMulti =
                Stream.of(Shape.square(), Shape.triangle(), Shape.circle())
                        .mapMulti((shape, downstream) -> shape
                                .twice()
                                .forEach(downstream /* or downstream::accept */));
        mapMulti.forEach(System.out::println);

        System.out.println("\n-----Another mapMulti----\n\n");

        // - Comparing with map(), mapMulti() implementation is more direct
        // since we don’t need to invoke so many stream intermediate operations.
        //- Another advantage is that the mapMulti implementation is imperative,
        //giving us more freedom to do element transformations.
        List<Integer> ints = Arrays.asList(1, 2, 3, 4, 5);
        double percentage = .01;
        List<Double> evenDoubles = ints.stream()
                .<Double>mapMulti((v, consumer) -> {
                    if((v % 2) == 0)
                        consumer.accept((double) v * (1 + percentage));
                })
                .toList();
        evenDoubles.forEach(System.out::println);

        // map operation equivalent to above mapMulti
        evenDoubles = ints.stream()
                .filter(v -> v % 2 == 0)
                .<Double>map(v -> ((double) v * (1 + percentage)))
                .toList();

        // reduce operations (also called as Immutable reduction operations)
        System.out.println("\n-----reduce----\n\n");
        // reduce can also combine map + reduce into one call
        var reduceOnly = Stream.of("apple", "orange", "banana")
                .reduce(0, (acc, str) -> acc + str.length(), Integer::sum); // (seed/intial, accumulator, combiner)

        var mapReduce = Stream.of("apple", "orange", "banana")
                .mapToInt(String::length)
                //.reduce(0, (acc, length) -> acc + length) // or, can use the below
                .reduce(0, Integer::sum); // (seed/initial, accumulator)
        System.out.println(reduceOnly);
        System.out.println(mapReduce);

        // Aggregating elements with a reduce operation to a collection type
        // but aggregate operations are much less verbose than the below reduce
        var withReduce = Stream.of("apple", "orange", "banana", "peach")
                .reduce(new ArrayList<>(),
                    (acc, fruit) -> { // accumulator
                        var list = new ArrayList<>(acc);
                        list.add(fruit);
                        return list;
                    },
                    (lhs, rhs) -> { // combiner
                        var list = new ArrayList<>(lhs);
                        list.addAll(rhs);
                        return list;
                    });

        // Aggregate (also called as mutable reduction) operations
        var withCollect = Stream.of("apple", "orange", "banana", "peach")
                .map(String::toUpperCase)
                .toList();

        // peek operation
        System.out.println("\n-----peak----\n\n");
        var withPeak = Stream.of("apple", "orange", "banana", "melon")
                .filter(str -> str.contains("e"))
                .peek(str -> System.out.println("Peek 1: " + str))
                .map(str -> {
                    System.out.println("map: " + str);
                    return str.toLowerCase();
                })
                .peek(str -> System.out.println("Peek 2: " + str))
                .count();
        System.out.println(withPeak);

        // for loop equivalent stream Java 8
        // The most significant advantage of an iterative Stream over a for-loop is
        // that you can still use a loop-like iteration but gain the benefits of a
        // lazy functional Stream pipeline.
        System.out.println("\n-----iterate (Java 8 & 9)----\n\n");
        IntStream.iterate(1, i -> i + 1)
                .limit(5L)
                .forEachOrdered(System.out::println);
        // for loop equivalent stream Java 9
        IntStream.iterate(1, i -> i < 5, i -> i + 1)
                .forEachOrdered(System.out::println);

        System.out.println("\n-----generate----\n\n");
        //  The lack of a starting seed value affects the Stream’s characteristics,
        //  making it UNORDERED, which can be beneficial for parallel use.
        var uuid = Stream.generate(UUID::randomUUID).limit(4L);
        uuid.forEach(System.out::println);
        // The downside of unordered Streams is that they won’t guarantee that
        // a limit operation will pick the first n elements in a parallel environment.
        // That may result in more calls to the element-generating Supplier than
        // are actually necessary for the result of the Stream.

        // From Arrays to stream and back
        System.out.println("\n-----arrays to stream and back to array----\n\n");
        String[] fruits = new String[] { "Banana", "Melon", "Orange" };

        String[] arrayResult = Arrays.stream(fruits)
                .filter(fruit -> fruit.contains("a"))
                .toArray(String[]::new);
        int[] fibonacci = new int[] { 0, 1, 1, 2, 3, 5, 8, 13, 21, 34 };
        int[] evenNumbers = Arrays.stream(fibonacci)
                .filter(value -> value % 2 == 0)
                .toArray();
        Stream.of(evenNumbers).forEach(System.out::println);

    }

    // Another example
    static record Shape (int corners) implements Comparable<Shape> {
        public boolean hasCorners() {
            return corners() > 0;
        }

        public List<Shape> twice() {
            return List.of(this, this);
        }

        public int compareTo(Shape o) {
            return Integer.compare(corners(), o.corners());
        }

        // Factory Methods
        public static Shape circle() {
            return new Shape(0);
        } // circle has 0 corners
        public static Shape triangle() {
            return new Shape(3);
        }
        public static Shape square() {
            return new Shape(4);
        }
    }

}
