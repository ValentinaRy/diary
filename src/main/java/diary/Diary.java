package diary;

import diary.entry.Entry;
import diary.entry.EntryType;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class Diary {
    private static final String EOL = System.getProperty("line.separator");
    @Nonnull private final User owner;
    @Nonnull private final Map<String, EntryList> entries;
    @Nonnull private final Map<String, EntryType> structure;

    public Diary(@Nonnull User owner) {
        this.owner = owner;
        entries = new HashMap<>();
        structure = new HashMap<>();
    }

    public String getOwnerLogin() {
        return owner.getLogin();
    }

    public boolean canAccess(User user) {
        return owner.equals(user);
    }

    public void addEntry(String name, Entry entry) {
        if (structure.containsKey(name)) {
            if (entry.getEntryType() != structure.get(name)) {
                throw new IllegalStateException("You cannot add entries with different types but the same name: " + name);
            }
        } else {
            structure.put(name, entry.getEntryType());
            entries.put(name, new EntryList());
        }
        entries.get(name).add(entry);
    }

    @Nonnull
    public Map<String, EntryList> getEntries() {
        return entries;
    }

    @Nonnull
    public String printDiary() {
        StringBuilder builder = new StringBuilder();
        builder.append("Diary of ").append(owner.getName()).append(EOL);
        entries.forEach((name, list) -> appendEntry(builder, name, list));
        return builder.toString();
    }

    @Nonnull
    public String printByEntryName(String entryName) {
        if (entries.containsKey(entryName)) {
            StringBuilder builder = new StringBuilder();
            appendEntry(builder, entryName, entries.get(entryName));
            return builder.toString();
        } else {
            return "No entry found in diary with name " + entryName;
        }
    }

    private void appendEntry(StringBuilder builder, String name, EntryList list) {
        builder.append(name).append(":").append(EOL);
        list.getEntries().forEach(entry -> builder.append("    ").append(entry.printEntry()).append(";").append(EOL));
    }
}
