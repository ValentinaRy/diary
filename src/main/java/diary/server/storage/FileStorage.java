package diary.server.storage;

import diary.Diary;
import diary.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.annotation.Nonnull;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;

public class FileStorage extends  Storage {
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String ABOUT = "about";
    @Nonnull private final String userFile;
    @Nonnull private String diaryFile;

    public FileStorage(@Nonnull String userFile, @Nonnull String diaryFile) {
        this.userFile = userFile;
        this.diaryFile = diaryFile;
    }

    @Override
    public boolean initLoad(Map<String, User> users, Map<User, Diary> diaryPerUserMap) {
        boolean loadUsersError = loadUsers(users);
        boolean loadDiariesError = loadDiaries(diaryPerUserMap);
        return loadUsersError || loadDiariesError;
    }

    private boolean loadUsers(Map<String, User> users) {
        try (FileReader fileReader = new FileReader(userFile)) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                User user = parseUser(line);
                // TODO: add to storage
            }
            return false;
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            return true;
        }
    }

    @Nonnull
    private User parseUser(String line) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject userObj = (JSONObject) parser.parse(line);

        String login = (String) userObj.get(LOGIN);
        String password = (String) userObj.get(PASSWORD);
        String name = (String) userObj.get(NAME);
        String about = (String) userObj.get(ABOUT);
        return new User(login, password, name, about);
    }

    private boolean loadDiaries(Map<User, Diary> diaryPerUserMap) {
        // TODO: implement method
        return false;
    }
}
