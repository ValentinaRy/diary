package diary.server.storage;

import diary.Diary;
import diary.User;

import javax.annotation.Nonnull;
import java.util.Map;

public class FileStorage extends  Storage {
    @Nonnull private final String userFile;
    @Nonnull private String diaryFile;

    public FileStorage(@Nonnull String userFile, @Nonnull String diaryFile) {
        this.userFile = userFile;
        this.diaryFile = diaryFile;
    }

    @Override
    public void initLoad(Map<String, User> users, Map<User, Diary> diaryPerUserMap) {
        loadUsers(users);
        loadDiaries(diaryPerUserMap);
    }

    private void loadUsers(Map<String, User> users) {
        // TODO: implement method
    }

    private void loadDiaries(Map<User, Diary> diaryPerUserMap) {
        // TODO: implement method
    }
}
