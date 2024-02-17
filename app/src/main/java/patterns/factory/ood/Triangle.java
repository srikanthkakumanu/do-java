package patterns.factory.ood;

public record Triangle(Color color) implements Shape {

    @Override
    public int corners() {
        return 3;
    }

    @Override
    public ShapeType type() {
        return ShapeType.TRIANGLE;
    }
}
