package basic.nio;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileOps {
    public static void main(String[] args) {
        System.out.println("\n-----NIO File IO with streams----\n\n");
        var dir = Paths.get("C:\\practice\\do-java");
        try (var stream = Files.list(dir)) {
            stream.map(Path::getFileName).forEach(System.out::println);
        } catch (IOException e) { System.out.println(e); }

        // walk - depth-first directory traversal
        System.out.println("\n-----NIO walk - depth first directory traversal with streams----\n\n");
        var start = Paths.get("C:\\practice\\do-java");
        try (var stream = Files.walk(start)) {
            stream.map(Path::toFile)
                    .filter(Predicate.not(File::isFile))
                    .sorted()
                    .forEach(System.out::println);
        } catch (IOException e) { System.out.println(e); }

        System.out.println("\n-----NIO find - depth first directory traversal with streams----\n\n");
        start = Paths.get("C:\\practice\\do-java");

        BiPredicate<Path, BasicFileAttributes> matcher =
                (path, attr) -> attr.isDirectory();

        try (var stream = Files.find(start, Integer.MAX_VALUE, matcher)) {
            stream.sorted()
                    .forEach(System.out::println);
        } catch (IOException e) { System.out.println(e); }

        countWords();
    }

    private static void countWords() {
        System.out.println("\n-----NIO count words of occurrence in a file----\n\n");
        var location = Paths.get("C:\\practice\\do-java\\app\\war-and-peace.txt");
        // CLEANUP PATTERNS
        var punctionaction = Pattern.compile("\\p{Punct}");
        var whitespace     = Pattern.compile("\\s+");
        var words          = Pattern.compile("\\w+");

        Map<String, Integer> wordCount = null;

        try (Stream<String> stream = Files.lines(location)) {
            wordCount =
                    // CLEAN CONTENT
                    stream.map(punctionaction::matcher)
                            .map(matcher -> matcher.replaceAll(""))
                            // SPLIT TO WORDS
                            .map(whitespace::split)
                            .flatMap(Arrays::stream)
                            // ADDITIONAL CLEANUP
                            .filter(word -> words.matcher(word).matches())
                            // NORMALIZE
                            .map(String::toLowerCase)
                            // COUNTING
                            .collect(Collectors.toMap(Function.identity(),
                                    word -> 1,
                                    Integer::sum));
        } catch (IOException e) { System.out.println(e); }
        System.out.println(wordCount);
    }
}
