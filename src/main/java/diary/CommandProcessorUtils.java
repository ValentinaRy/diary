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
}
