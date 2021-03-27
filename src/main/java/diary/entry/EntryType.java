package diary.entry;

enum EntryType {
    INTEGER("integer"),
    DOUBLE("double"),
    STRING("string");

    private final String string;
    private EntryType(String string) {
        this.string = string;
    }
}
