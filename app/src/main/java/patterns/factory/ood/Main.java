package patterns.factory.ood;

/**
 *  Factory design pattern purpose is to create an instance of an object
 *  without exposing the implementation details of how to create such
 *  objects by using a factory instead.
 */
public class Main {
    public static void main(String[] args) {
        var triangle = ShapeFactory.newShape(ShapeType.PENTAGON, Color.GREEN);
        System.out.println(triangle);
    }
}
