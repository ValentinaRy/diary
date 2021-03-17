package diary;

import diary.entry.Entry;

import javax.annotation.Nonnull;
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
}
