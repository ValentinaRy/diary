package diary.server;

import diary.Diary;
import diary.User;
import diary.server.storage.Storage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public abstract class Server {
    @Nonnull protected final Storage storage;
    @Nonnull protected final Map<String, User> users;
    @Nonnull protected final Map<User, Diary> diaryPerUserMap;

    public Server(@Nonnull Storage storage) {
        this.storage = storage;
        this.users = new HashMap<>();
        this.diaryPerUserMap = new HashMap<>();
    }

    public boolean addUser(@Nonnull User user) {
        return users.putIfAbsent(user.getLogin(), user) == null;
    }

    @Nonnull
    public Map<String, User> getUsers() {
        return users;
    }

    public boolean createDiary(@Nonnull User user) {
        return diaryPerUserMap.putIfAbsent(user, new Diary(user)) == null;
    }

    @Nullable
    public Diary getDiary(@Nonnull User user) {
        return diaryPerUserMap.get(user);
    }

    public abstract void start();
}
