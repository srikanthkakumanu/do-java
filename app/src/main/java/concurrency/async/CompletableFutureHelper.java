package concurrency.async;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * The Helper scaffold
 *
 * Although the CompletableFuture API is massive, it’s still missing certain use cases.
 * For example, when combining tasks, the return type of the static helper allOf is
 * CompletableFuture<Void>, so you don’t have access to any result of the given
 * instances in later stages. It’s a flexible coordination-only method that accepts
 * any kind of CompletableFuture<?> as its arguments but with the trade-off of not
 * having access to any of the results.
 *
 * To make up for this, you can create a helper to complement the existing API as needed.
 */
public final class CompletableFutureHelper {
    private final static Predicate<CompletableFuture<?>> EXCEPTIONALLY =
            Predicate.not(CompletableFuture::isCompletedExceptionally);

    @SafeVarargs
    private static <T> Function<Void, List<T>> gatherResultsFn(
            CompletableFuture<T>... cfs) {
        return unused -> Arrays.stream(cfs)
                .filter(Predicate.not(EXCEPTIONALLY))
                .map(CompletableFuture::join)
                .toList();
    }

    /**
     * An alternative to allOf for certain use cases when the results should
     * be easily accessible.
     */
    @SafeVarargs
    public static <T> CompletableFuture<List<T>> eachOf(
            CompletableFuture<T>... cfs) {
        return CompletableFuture.allOf(cfs)
                .thenApply(gatherResultsFn(cfs));
    }

    @SafeVarargs
    public static <T> CompletableFuture<List<T>> bestEffort(
            CompletableFuture<T>... cfs) {
        return CompletableFuture.allOf(cfs)
                .exceptionally(ex -> null)
                .thenApply(gatherResultsFn(cfs));
    }

    private CompletableFutureHelper() {
        // suppress default constructor
    }
}
