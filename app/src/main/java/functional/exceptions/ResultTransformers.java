package functional.exceptions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Note: A functional Result object to handle exceptions in quite simplistic manner with
 * minimal code. If you intend to use a type like Result<V, E>, you should check out
 * one of the functional libraries of the Java ecosystem.
 * Projects like vavr, jOOλ (pronounced “JOOL”), and Functional Java provide quite
 * comprehensive and battle-tested implementations that are ready to use.
 *
 * This example has:
 * - added transformers to Result such as mapSuccess, mapFailure, map
 * - To react to a certain state, added ifSuccess, ifFailure, and handle.
 *      However, The implementation is almost equivalent to the mapper methods,
 *      except they use a Consumer instead of a Function.
 * These two additions are side-effect-only and, therefore, not very functional
 * in the purest sense. Nevertheless, such additions provide an excellent stopgap
 * between imperative and functional approaches.
 *
 * - Added convenience methods for providing fallback values. The most obvious
 *      ones are orElse, orElseGet, and orElseThrow.
 * - Adding an orElseThrow as a shortcut to rethrow the inner Throwable isn’t as
 *      straightforward because it still has to adhere to the catch-or-specify
 *       requirement, hence the addition of sneakyThrow method.
 *
 */
public class ResultTransformers {

    public record Result<V, E extends Throwable> (V value, E throwable, boolean isSuccess) {

        public static <V, E extends Throwable> Result<V, E> success(V value) {
            return new Result<>(value, null, true);
        }

        public static <V, E extends Throwable> Result<V, E> failure(E throwable) {
            return new Result<>(null, throwable, false);
        }

        public <R> Optional<R> mapSuccess(Function<V, R> fn) {
            return this.isSuccess ? Optional.ofNullable(this.value).map(fn) : Optional.empty();
        }

        public <R> Optional<R> mapFailure(Function<E, R> fn) {
            return this.isSuccess ? Optional.empty() : Optional.ofNullable(this.throwable).map(fn);
        }

        public <R> R map(Function<V, R> successFn, Function<E, R> failureFn) {
            return this.isSuccess ? successFn.apply(this.value) //
                    : failureFn.apply(this.throwable);
        }

        /**
         * The below implementation methods are almost equivalent to the above-mentioned
         * mapper methods, except they use a Consumer instead of a Function.
         */
        public void ifSuccess(Consumer<? super V> action) {
            if (this.isSuccess) {
                action.accept(this.value);
            }
        }

        public void ifFailure(Consumer<? super E> action) {
            if (!this.isSuccess) {
                action.accept(this.throwable);
            }
        }

        public void handle(Consumer<? super V> successAction,
                           Consumer<? super E> failureAction) {
            if (this.isSuccess) {
                successAction.accept(this.value);
            } else {
                failureAction.accept(this.throwable);
            }
        }

        /**
         * added convenience methods for providing fallback values.
         */
        public V orElse(V other) {
            return this.isSuccess ? this.value
                    : other;
        }

        public V orElseGet(Supplier<? extends V> otherSupplier) {
            return this.isSuccess ? this.value
                    : otherSupplier.get();
        }

        private <E extends Throwable> void sneakyThrow(Throwable e) throws E {
            throw ((E) e);
        }

        public V orElseThrow() {
            if (!this.isSuccess) {
                sneakyThrow(this.throwable);
                return null;
            }

            return this.value;
        }
    }

    static Result<String, IOException> safeReadString(Path path) {
        try {
            return Result.success(Files.readString(path));
        }
        catch (IOException e) {
            return Result.failure(e);
        }
    }

    public static void main(String... args) {
        // HANDLE ONLY SUCCESS CASE
        var successOnly = Stream.of(Path.of("C:\\books\\text1.txt"),
                        Path.of("C:\\books\\text2.txt"),
                        Path.of("C:\\books\\text3.txt"))
                .map(ResultTransformers::safeReadString)
                .map(result -> result.mapSuccess(String::toUpperCase))
                .flatMap(Optional::stream)
                .toList();

        System.out.println("Found (successOnly): " + successOnly.size());

        // HANDLE BOTH CASES
        var result = safeReadString(Paths.get("invalid")).map(
                String::toUpperCase,
                failure -> "IO-Error: " + failure.getMessage()
        );

        System.out.println("Result: " + result);
    }
}