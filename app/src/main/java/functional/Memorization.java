package functional;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 Memorization is a form caching that accelerates the performance
 of repetitive recursive operations. In FP, it can be implemented
 as a generic wrapper for any pure function.
 <p>
 Memoization is a technique used in FP to speed up computer programs
 by eliminating the repetitive computation of results, and by avoiding
 repeated calls to functions that process the same input.
 */
public class Memorization {
    Map<String, Object> cache = HashMap.newHashMap(5);

    public static void main(String[] args) {
        Memorization m = new Memorization();
        var calculated = m.memorizeCall("Hello, World!", 42);
        var memorized = m.memorizeCall("Hello, World!", 42);
        System.out.println(calculated);
        System.out.println(memorized);
    }

    <T> T memorize(String identifier, Supplier<T> fn) {
        return (T) cache.computeIfAbsent(identifier, key -> fn.get());
    }

    Integer expensiveCall(String first, Integer second) {
        System.out.println("Sleeping for 3 secs..");
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return second;
    }

    Integer memorizeCall(String first, Integer second) {
        var compoundKey = String.format("expensiveCall:%s-%d", first, second);
        return memorize(compoundKey, () -> expensiveCall(first, second));
    }
}
