package diary;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public class User {
    @Nonnull private final String login;
    @Nonnull private final String password;
    @Nonnull private final String name;
    @Nullable private final String about;

    public User(@Nonnull String login, @Nonnull String password,
                @Nonnull String name, @Nullable String about) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.about = about;
    }

    @Nonnull
    public String getLogin() {
        return login;
    }

    @Nonnull
    public String getPassword() {
        return password;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    @Nullable
    public String getAbout() {
        return about;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return login.equals(user.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login);
    }
}
