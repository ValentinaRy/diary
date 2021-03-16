package diary;

import diary.entry.Entry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Diary {
    private final User owner;
    private final Map<String, List<Entry>> entries;

    public Diary(User owner) {
        this.owner = owner;
        entries = new HashMap<>();
    }

    public boolean canAccess(User user) {
        return owner.equals(user);
    }
}
