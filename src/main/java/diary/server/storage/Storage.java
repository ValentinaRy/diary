package diary.server.storage;

import diary.Diary;
import diary.User;

import java.util.Map;

public abstract class Storage {
    public abstract boolean initLoad(Map<String, User> users, Map<User, Diary> diaryPerUserMap);

    public abstract void flush(Map<String, User> users, Map<User, Diary> diaryPerUserMap);
}
