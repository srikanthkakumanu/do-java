package functional;

import java.util.function.UnaryOperator;

public class FirstClassFunctions {
    public static void main(String[] args) {
        // First-class Functions - Java Lambdas
        // First-class function assigned to a variable
        UnaryOperator<Integer> quadraticFn = x -> x * x;
        quadraticFn.apply(4);

        // First-class function as Method Argument
        FirstClassFunctions fc = new FirstClassFunctions();
        System.out.println(fc.apply(45, quadraticFn));

        // First-class function as return value
        System.out.println(fc.multiplyWith(2).apply(10));
        // or
        UnaryOperator<Integer> multiplyWithFive = fc.multiplyWith(5);
        System.out.println(multiplyWithFive.apply(2));
    }

    /**
     Passing functions as method argument
     @return
     */
    Integer apply(Integer input, UnaryOperator<Integer> operation) {
        return operation.apply(input);
    }

    /**
     First-class function as return value
     @param multiplier
     @return
     */
    public UnaryOperator<Integer> multiplyWith(Integer multiplier) {
        return x -> multiplier * x;
    }


}
