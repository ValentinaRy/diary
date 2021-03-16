package diary.entry;

public class StringEntry implements Entry {
    private final String value;

    public StringEntry(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
