package patterns.factory.fp;

import java.util.function.Function;

/**
 *  Factory design pattern purpose is to create an instance of an object
 *  without exposing the implementation details of how to create such
 *  objects by using a factory instead.
 */
public class Main {
    public static void main(String[] args) {
        var triangle = ShapeType.TRIANGLE.newInstance(Color.GREEN);
        System.out.println(triangle);

        // Though it is true that a factory looks redundant,
        // still it provides a functional way to interact with
        // the factory further, like functional composition to
        // log the creation of a shape.
        Function<Shape, Shape> cornerPrint = shape -> {
            System.out.println("Shape created with " + shape.corners() + " corners.");
            return shape;
        };

        /*
         * By fusing the factory with the enum, the decision-making process—what
         * factory method to call—gets replaced by binding the factory methods
         * directly with ShapeType counterparts.
         *
         * The Java compiler now forces you to implement the factory on any addition
         * to the enum.
         *
         * This approach reduces the required boilerplate with added compile-time
         * safety for future extensions.
         */
        ShapeType.PENTAGON.factory
                .andThen(cornerPrint)
                .apply(Color.RED);

    }
}
