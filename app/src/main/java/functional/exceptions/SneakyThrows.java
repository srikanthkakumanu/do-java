package functional.exceptions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * Note:
 * This is one of the techniques that are used to circumvent the exceptions or
 * an imperfect solution in functional code.
 */
public class SneakyThrows {

    static <E extends Throwable> void sneakyThrow(Throwable e) throws E {
        System.out.println("Exception thrown : " + e);
        throw ((E) e);
    }

    static String sneakyRead(Path path) {
        try { return Files.readString(path); }
        catch (IOException e) {
            System.out.println("Exception thrown at sneakyRead : " + e);
            sneakyThrow(e);
        }
        return null;
    }
    public static void main(String[] args) {
        var paths = Stream.of(Path.of("C:\\books\\text1.txt"),
                Path.of("C:\\books\\text2.txt"),
                Path.of("C:\\books\\text3.txt"));
        var result = paths.map(SneakyThrows::sneakyRead).toList();
        System.out.println("Found: " + result.size());
    }
}
