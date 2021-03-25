package diary.server.storage;

import diary.Diary;
import diary.User;

import java.util.Map;

public abstract class Storage {
    // returns true if load is successful
    public abstract boolean initLoad(Map<String, User> users, Map<User, Diary> diaryPerUserMap);

    public abstract void flush(Map<String, User> users, Map<User, Diary> diaryPerUserMap);
}
