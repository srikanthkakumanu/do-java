package records;

/**
 * Wither methods follow the name scheme with[componentName]([Type] value).
 * They’re similar to setters but return a new instance instead of modifying
 * the current one.
 */
public class WitherMethods {
    public static void main (String[] args) {
        var point = new Point(23, 42);
        System.out.println(point);
        var newPoint = point.withX(5);
        System.out.println(newPoint);
    }

    record Point (int x, int y) {
        public Point withX (int newX) { return new Point(newX, y()); }
        public Point withY (int newY) { return new Point(x(), newY); }
    }
}
