package functional;

import java.util.Objects;
import java.util.function.Predicate;


/**
 The below two scenarios are called
  "Lambda compatible but type incompatible"
  <p></p>
  Error:
  incompatible types: LikePredicate<java.lang.String> cannot be
  converted to java.util.function.Predicate<java.lang.String>
  Predicate<String> wontCompile = isNull;
  <p></p>
  Exception java.lang.ClassCastException: class LikePredicate
  cannot be cast to class java.util.function.Predicate
  Predicate<String> wontCompileEither = (Predicate<String>) isNull;
  <p></p>
  From Lambda perspective, both SAMs are identical, they both
  accept String argument and return a boolean result.
  <p></p>
  For Java type system, they have no connection whatsoever, thus,
  making a cast between them impossible.
  Hence, the Lambda compatible but type incompatible.
  <p></p>
  This scenario can be resolved by:
  Using a method reference creates a new dynamic call site to be
  invoked by bytecode opcode invokedynamic instead of implicit and explicit
  type casting.
 *
 */

public class BridgeTypeIncompatible {
    public static void main(String[] args) {
        LikePredicate<String> isNull = Objects::nonNull;
        // Predicate<String> wontCompile = isNull; // compile-error
        // Predicate<String> wontCompileEither = (Predicate<String>) isNull; // compile-error

        Predicate<String> thisIsFine = isNull::test; // solution
        thisIsFine.test(null);
    }
}
interface LikePredicate<T> { boolean test(T value); }
