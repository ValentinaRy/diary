package diary;

import javax.annotation.Nonnull;

public class User {
    @Nonnull private final String login;
    @Nonnull private final String password;
    @Nonnull private final String name;
    @Nonnull private final String about;

    public User(@Nonnull String login, @Nonnull String password,
                @Nonnull String name, @Nonnull String about) {
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

    @Nonnull
    public String getAbout() {
        return about;
    }
}
