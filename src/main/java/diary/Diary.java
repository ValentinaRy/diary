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
        entries.forEach((name, list) -> {
            builder.append("Entries ").append(name).append(":");
            list.forEach(entry -> builder.append(entry.printEntry()).append(";"));
            builder.append(EOL);
        });
        return builder.toString();
    }
}
