package functional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.DoubleFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 1. Functional programming principles and concepts using Java
 
 1.1 First-class Functions: All first-class functions are implicitly higher-order function
      1.1.1 Pass a function as a function argument
      1.1.2 Return a function from a function
      1.1.3 Function as values (first-class citizens)
 
 1.2. Higher-Order Functions: Possible only with first-class functions
      1.2.1 Pass a function as a function argument
      1.2.2 Return a function from a function
 
 1.3. Pure Functions: A pure function should be deterministic, means that it should return a 
      value based only on the arguments and should not have any side-effects (means it should
      be immutable). side-effects can be as simple as updating a local or global state.
 
 1.4. Immutability: Immutability refers to the property that an entity can't be modified after 
      being instantiated. In Java, data structures are not immutable by default. Thus, we have 
      to create immutable data structures and Java provides several built-in immutable types.
 
 1.5. Referential Transparency: <B> For referential transparency, we need our functions should be 
      pure and immutable.</B> We call an expression referentially transparent if replacing it 
      with its corresponding value has no impact on the program's behavior.
 
 2. Functional Programming Techniques:
 
 2.1 Function composition: Function composition means composing complex functions by combining 
      simpler functions. In Java, it can be achieved by using functional interfaces, which are 
      target types for lambda expressions and method references. Any interface with single 
      abstract method (SAM) can serve as a functional interface. Method chaining is the technique 
      to compose multiple functions.
      
 2.2 Monads: Many FP concepts derived from Category theory (A general theory of functions in Mathematics). 
      Monard is an abstraction, which allows us to wrap a value, apply set of transformations and get the 
      value back with all transformations applied.
      Monads are containers or structures that encapsulate values and computations. 
      It is an object that can map itself to different results based on transformations.
      There are two types of Monads: Unit and Bind.
      Every Monad must obey three laws: left idenity, right identity and associativity.
      In Java, Monad examples are: Optional, Stream, CompletableFuture etc.
 
 2.3 Currying: It is a mathematical technique of converting a function that takes multiple 
      arguments into a sequence of functions that take a single argument. It is a powerful 
      function composition technique where we don’t need to call a function with all its 
      arguments. A curried function does not realize its effect until it receives all 
      the arguments.
 
 2.4 Recursion: It allows us to breakdown a problem into smaller pieces. 
                The main benefit of recursion is that it helps to eliminate 
                the side effects i.e. looping (in impertive paradigm).
      Head recursion:- making the recursive call before calculating the result at each step 
                       or in words at the head of the calculation.
      Tail recursion:- we ensure that the recursive call is the last call a function makes.
                      Java still does not have support for this tail-call recursion.
 */
public class LambdasAndFunctionals {
    public static void main(String... args) {
        // 1.1.3 Treat a function as first-class citizen
        Function<String, String> fn = param -> param.concat(" from lambda!");
        // 1.1.1 & 1.2.1 first-class function and higher-order function - Pass a function as a function argument
        String result = passFunctionAsArgument("Message", fn); 
        System.out.println(result);

        // 1.1.2 & 1.2.2 first-class function and higher-order function - Return a function from a function
        IntFunction<String> some = returnFunctionFromFunction(); 
        System.out.println(some.apply(299));

        // 1.3. pure function - a function depends only on arguments and no side-effects
        System.out.println(pureFunction(Arrays.asList(1, 48, 37, 28, 29)));

        // 1.4. Immutability
        ImmutableRecord ir = new ImmutableRecord("Shogun");
        System.out.println(ir.name()); // no setters for immutable data structures

        ImmutableClass ic = new ImmutableClass("Shogun", new AnotherImmutable(19));
        System.out.println(ic.getName() + ic.getAnotherImmutable().getValue());
        
        // 2.1 Function composition
        Function<Double, Double> log = v -> Math.log(v);
        Function<Double, Double> sqrt = v -> Math.sqrt(v);
        
        // compose - applies the function passed in the argument first and 
        // then the function on which it’s invoked.
        Function<Double, Double> logThenSqrt = sqrt.compose(log);
        System.out.println(String.valueOf(logThenSqrt.apply(3.14)));
        
        // andThen - first applies the function on which it is invoked, and 
        // then the function passed in the argument later.
        Function<Double, Double> sqrtThenLog = log.andThen(sqrt);
        System.out.println(String.valueOf(sqrtThenLog.apply(3.14)));

        // 2.2 Monads example using Optional
        Monad m = new Monad();
        System.out.println(m.apply(2));

        // 2.2 Monads properties/laws example
        MonadLaws ml = new MonadLaws();
        System.out.println(ml.leftIdentity());
        System.out.println(ml.rightIdentity());
        System.out.println(ml.associativity());
        System.out.println(ml.fail());

        // 2.3 Currying
        // A function to calculate our weight on a planet. 
        // While our mass remains the same, gravity varies by the planet we’re on.
        // Function<Double, Function<Double, Double>> weight = gravity -> mass -> {
        //         System.out.println("Mass: " + mass + " Gravity: " + gravity);
        //         return mass gravity;
        // };
        Function<Double, Function<Double, Double>> weight = gravity -> mass -> mass * gravity;

        Function<Double, Double> weightOnEarth = weight.apply(9.81); // 9.81 - gravity
        System.out.println("My Weight on Earth : " + weightOnEarth.apply(60.0)); // 60.0 - mass

        Function<Double, Double> weightOnMars = weight.apply(3.75); // 3.75 - gravity
        System.out.println("My Weight On Mars: " + weightOnMars.apply(60.0)); // 60.0 - mass


        // 2.4 Recursion
        System.out.println(factorialWithHeadRecursion(10)); // head recursion
        System.out.println(factorialWithTailRecursion(10, 1)); // tail recursion

    }

    /**
     Passing a function as an argument to a function
     @param s
     @param fn
     @return
     */
    static String passFunctionAsArgument(String s, Function<String, String> fn) {
        return fn.apply(s);
    }

    /**
     1.1.2 & 1.2.2 First-class function and Higher order function - A function that returns a function
     @return
     */
    static IntFunction<String> returnFunctionFromFunction() { 
        // 1.1.2 & 1.2.2 Return a function from a function
        return it -> "doSome(): Return this function (lambda expression) as value from a function i.e. doSome() " + it;
    }

    /**
     1.3. Pure function - It should be deterministic, means it depends only on function arguments and 
      no side-effects - should not change local or global state i.e. immutable.
     @param nums
     @return
     */
    static Integer pureFunction(final List<Integer> nums) {
        return nums.stream() // this is a aggregate operation so, no change to original collection
                    .collect(Collectors.summingInt(Integer::intValue));
    }

    /**
     2.4 Recursion
     Head call Recursion - Making the recursive call before calculating the result 
     at each step or in words at the head of the calculation.
     A drawback of this type of recursion is that every step has to hold the state of 
     all previous steps until we reach the base case. This is not really a problem for 
     small numbers, but holding the state for large numbers can be inefficient.
     @param n
     @return
     */
    private static Integer factorialWithHeadRecursion(Integer n) {
        return (n == 1) ? 1 : n * factorialWithHeadRecursion(n - 1);
    }

    /**
     2.4 Recursion
     Tail call Recursion - we ensure that the recursive call is the last call a function makes.
     @param n
     @param result
     @return
     */
    private static Integer factorialWithTailRecursion(Integer n, Integer result) {
        return (n == 1) ? result : factorialWithTailRecursion(n - 1, result * n);
    }

}

// 1.4. Immutable: record is an immutable data structure by default. Thus, no setXXX() methods exist
record ImmutableRecord(String name) {}

// 1.4. Immutable class
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
class ImmutableClass {
    private final String name;
    private final AnotherImmutable anotherImmutable;
}

// 1.4. Another immutable class
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
class AnotherImmutable {
    private final Integer value;
}

// 2.2 Monads example using Optional
class Monad {
    private double multiplyByTwo(double d) { return d * 2; }
    private double divideByTwo(double d) { return d / 2; }
    private double addThree(double d) { return d + 3; }
    private double substractOne(double d) { return d - 1; }
    public double apply(double d) {
        return Optional.of(d)
                        .flatMap(v -> Optional.of(multiplyByTwo(v)))
                        .flatMap(v -> Optional.of(multiplyByTwo(v)))
                        .flatMap(v -> Optional.of(divideByTwo(v)))
                        .flatMap(v -> Optional.of(addThree(v)))
                        .flatMap(v -> Optional.of(substractOne(v)))
                        .get();
    }
}

/**
 2.2 Monads
 
 Monad three laws:
 Left Identity:  When applying to a monad, it should yield the same outcome as applying 
                 the transformation to the held value.
 
 Right Identity: When sending a monad transformation (convert the value to a monad), the 
                 yield outcome must be the same as wrapping the value in a new monad.
 
 Associativy:    When chaining transformations, it should not matter how transformations are nested.
 */
class MonadLaws {

    public boolean leftIdentity() {
        Function<Integer, Optional<Integer>> m = v -> Optional.of(v + 1);
        return Optional.of(3).flatMap(m).equals(m.apply(3));
    }

    public boolean rightIdentity() {
        return Optional.of(3).flatMap(Optional::of).equals(Optional.of(3));
    }

    public boolean associativity() {
        Function<Integer, Optional<Integer>> m = v -> Optional.of(v + 1);
        Optional<Integer> left = Optional.of(3).flatMap(m).flatMap(Optional::of); // left side
        Optional<Integer> right = Optional.of(3).flatMap(v -> m.apply(v).flatMap(Optional::of)); // right side
        return left.equals(right);
    }
    // to simulate a Monad law fail
    // As observed, the left identity property from Monad was broken. 
    public boolean fail() {
        Function<Integer, Optional<Integer>> m = v -> Optional.of(v == null ? -1 : v + 1);
        return Optional.ofNullable((Integer) null).flatMap(m).equals(m.apply(null));
    }
}
