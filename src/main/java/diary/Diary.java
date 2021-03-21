package diary;

import diary.entry.Entry;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Diary {
    private static final String EOL = System.getProperty("line.separator");
    @Nonnull private final User owner;
    @Nonnull private final Map<String, List<Entry>> entries;

    public Diary(@Nonnull User owner) {
        this.owner = owner;
        entries = new HashMap<>();
    }

    public boolean canAccess(User user) {
        return owner.equals(user);
    }

    public void addEntry(String name, Entry entry) {
        entries.putIfAbsent(name, new ArrayList<>());
        entries.get(name).add(entry);
    }

    @Nonnull
    public String printDiary() {
        StringBuilder builder = new StringBuilder();
        builder.append("Diary of ").append(owner.getName()).append(EOL);
        entries.forEach((name, list) -> appendEntry(builder, name, list));
        return builder.toString();
    }

    @Nonnull String printByEntryName(String entryName) {
        if (entries.containsKey(entryName)) {
            StringBuilder builder = new StringBuilder();
            appendEntry(builder, entryName, entries.get(entryName));
            return builder.toString();
        } else {
            return "No entry found in diary with name " + entryName;
        }
    }

    private void appendEntry(StringBuilder builder, String name, List<Entry> list) {
        builder.append(name).append(":").append(EOL);
        list.forEach(entry -> builder.append("    ").append(entry.printEntry()).append(";").append(EOL));
    }
}
