package diary;

import diary.entry.Entry;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class EntryList {
    private final List<Entry> entries = new ArrayList<>();

    public void add(@Nonnull Entry entry) {
        entries.add(entry);
    }

    public List<Entry> getEntries() {
        return entries;
    }
}
