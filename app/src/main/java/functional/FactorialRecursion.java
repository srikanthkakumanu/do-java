package functional;

public class FactorialRecursion {
    public static void main(String[] args) {
        System.out.println("Head Recursion - Final Result: " + factorialHead(4L));
        System.out.println("Tail Recursion - Final Result: " + factorialTail(4L, 1L));
    }

    /**
     * This method is for head recursion
     * @param n
     * @return
     */
    static long factorialHead (long n) {
        System.out.println("Head Recursion -> input is: " + n);
        // In Head Recursion, base condition must come before the recursive call
        if (n == 1L)
            return 1L;

        var nextN = n - 1L;
        // recursive call
        return n * factorialHead(nextN);
    }

    static long factorialTail(long n, long accumulator) {
        System.out.println("Tail Recursion -> input is: " + n + ", accumulator is : " + accumulator);
        // the base condition does not change compared to head recursion
        if (n == 1L)
            return 1L;

        var nextN = n - 1L;
        var nextAccumulator = n * accumulator;

        // Instead of returning an expression dependent on the next recursive call,
        // both factorialTail parameters are evaluated beforehand.
        // The method only returns the recursive call itself.
        // The accumulator requires an initial value. It reflects the base condition.
        return factorialTail(nextN, nextAccumulator);
    }
}
