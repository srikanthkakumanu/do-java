package patterns.factory.ood;

public record Pentagon(Color color) implements Shape {
    @Override
    public int corners() {
        return 5;
    }

    @Override
    public ShapeType type() {
        return ShapeType.PENTAGON;
    }
}
