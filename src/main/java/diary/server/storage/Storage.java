package diary.server.storage;

import diary.Diary;
import diary.User;

import javax.annotation.Nonnull;
import java.util.Map;

public abstract class Storage {
    // returns true if load is successful
    public abstract boolean initLoad(@Nonnull Map<String, User> users, @Nonnull Map<User, Diary> diaryPerUserMap);

    public abstract void flush(@Nonnull Map<String, User> users, @Nonnull Map<User, Diary> diaryPerUserMap);
}
