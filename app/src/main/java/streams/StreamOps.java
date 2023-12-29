package streams;

import java.util.stream.Stream;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.DoubleStream;
import java.util.Random;
import java.util.regex.Pattern;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.charset.Charset;
import java.util.List;
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
        Path p = Paths.get(System.getProperty("user.dir").concat("\\app\\src\\main\\java\\streams\\").concat("notes.md"));
        //Stream<String> sLines = Files.lines(p);
        Stream<String> sLinesWithCharset = Files.lines(p, Charset.forName("UTF-8"));
        sLinesWithCharset.forEach(System.out::println);
        List<String> matched = Stream.of("A", "B", "C", "D", "E", "C", "F", "C")
                                        .filter(e -> e.contains("C"))
                                        .collect(Collectors.toList());
        Optional<String> any = matched.stream().findAny();
        Optional<String> first = matched.stream().findFirst();
        System.out.println(any.get() + first.get());
        
    }
}
