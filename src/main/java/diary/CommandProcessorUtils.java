package diary;

import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkState;

public class CommandProcessorUtils {
    @Nonnull
    public static User getUserIfPermitted(@Nonnull String login, @Nonnull String password) {
        User user = CmdServer.getUsers().get(login);
        checkState(user != null && user.getPassword().equals(password), "Wrong login or password");
        return user;
    }

    @Nonnull
    public static Diary getDiaryIfPermitted(@Nonnull User user) {
        Diary diary = CmdServer.getDiary(user);
        checkState(diary != null && diary.canAccess(user), "Error: no diary for user or user cannot access it");
        return diary;
    }
}
