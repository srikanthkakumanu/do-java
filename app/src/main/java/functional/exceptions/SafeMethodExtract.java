package functional.exceptions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Note:
 * This is one of the techniques that are used to circumvent the exceptions or
 * an imperfect solution in functional code.
 */
public class SafeMethodExtract {
    public static void main(String[] args) {
        SafeMethodExtract sme = new SafeMethodExtract();
        Stream<Path> paths = Stream.of(
                Path.of("C:\\books\\text1.txt"),
                        Path.of("C:\\books\\text2.txt"),
                        Path.of("C:\\books\\text3.txt"));
        paths.map(sme::safeRead)
                .filter(Objects::nonNull)
                .forEach(System.out::println);
    }

    String safeRead(Path path) {
        try {
            return Files.readString(path);
        } catch (IOException e) {
            //throw new RuntimeException(e);
            System.err.println("IOException thrown ");
            return null;
        }
    }
}
