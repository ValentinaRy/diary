package diary.entry;

public class IntegerEntry implements Entry {
    private final int value;

    public IntegerEntry(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
