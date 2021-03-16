package diary;

public class User {
    private final String login;
    private final String password;
    private final String name;
    private final String about;

    public User(String login, String password, String name, String about) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.about = about;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getAbout() {
        return about;
    }
}
