package functional;

import java.util.function.IntSupplier;

public class LazyEvals {
    public static void main(String[] args) {

        // Java's standard/default strict evaluation
        try {
            var result = standardAdd(5, (1 / 0));
            System.out.println(result);
        } catch (ArithmeticException e) {
            System.out.println(e.getMessage()); }

        // lazy evaluation using suppliers aka lambda expressions
        var result = add(() -> 5, () -> 1 / 0);
        System.out.println(result);

        logicalOps(false, false);
    }

    /**
     * By default, Java does the strict evaluation.
     * The below method throws an exception even though
     * we don't use the y parameter argument in the code.
     * Therefore, lambda expressions are perfect candidates
     * for lazy evaluation.
     *
     * The declaration of the IntSupplier instances, or their inline equivalents,
     * is a strict statement and is evaluated immediately. The actual lambda body,
     * however, does not evaluate until it’s explicitly called with getAsInt,
     * preventing the ArithmeticException.
     */
    static int standardAdd(int x, int y) {
        return x + x;
    }
    /**
     * Lazy evaluation using Suppliers
     */
    static int add(IntSupplier x, IntSupplier y) {
        int actualX = x.getAsInt();
        return actualX + actualX;
    }

    /**
     * Logical short-circuit operators that support lazy evaluation
     * from left to right.
     * @param left
     * @param right
     */
    static void logicalOps(boolean left, boolean right) {
        if (left && right)
            System.out.println("Both left and right are true");

        if (left || right) {
            if(left)
                System.out.println("left is true");
            else
                System.out.println("right is true");
        }
    }
}
