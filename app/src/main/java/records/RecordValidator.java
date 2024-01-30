package records;

public record RecordValidator (int x, int y) {
    public RecordValidator {
        if (x < y)
            throw new IllegalArgumentException("x must be equal or greater than y");
    }
}
