package diary.entry;

public class DoubleEntry implements Entry {
    private final double value;

    public DoubleEntry(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
