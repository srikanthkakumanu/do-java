package functional.exceptions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Note:
 * This is one of the techniques that are used to circumvent the exceptions or
 * an imperfect solution in functional code.
 */


public class UncheckExceptions {

    /**
     * Function interface - The wrapper extends the original type to act
     * as a drop-in replacement.
     */
    @FunctionalInterface
    interface ThrowingFunction<T, R> extends Function<T, R> {
        // This single abstract method (SAM) mimics the original but throws an Exception.
        R applyThrows(T element) throws Exception;

        /**
         *
         * This original SAM is implemented as a default method to wrap any Exception
         * as an unchecked RuntimeException.
         */
        @Override
        default R apply(T t) {
            try { return applyThrows(t); }
            catch (Exception e) { throw new RuntimeException(e); }
        }

        // A static helper to uncheck any throwing Function<T, R> to circumvent
        // the catch-or-specify requirement.
        public static <T, R> Function<T, R> uncheck(ThrowingFunction<T, R> fn) {
            return fn;
        }
    }

    static String safeRead(Path path) {
        try {
            return Files.readString(path);
        } catch (IOException e) {
            //throw new RuntimeException(e);
            System.err.println("IOException thrown ");
            return null;
        }
    }
    public static void main(String[] args) {
        var paths = Stream.of(Path.of("C:\\books\\text1.txt"),
                Path.of("C:\\books\\text2.txt"),
                Path.of("C:\\books\\text3.txt"));

        // ThrowingFunction<Path, String> throwingFn = Files::readString;
        // Alternatively, can be done like below
        paths.map(ThrowingFunction.uncheck(Files::readString))
                .filter(Objects::nonNull)
                .forEach(System.out::println);
    }
}
