package diary.entry;

public enum EntryType {
    INTEGER("integer"),
    DOUBLE("double"),
    STRING("string");

    private final String string;

    private EntryType(String string) {
        this.string = string;
    }

    public String string() {
        return string;
    }

    public EntryType parse(String string) {
        for (EntryType value : values()) {
            if (value.string().equals(string)) {
                return value;
            }
        }
        throw new IllegalStateException("No entry type: " + string);
    }
}
