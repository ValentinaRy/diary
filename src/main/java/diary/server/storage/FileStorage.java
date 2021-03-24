package diary.server.storage;

import diary.Diary;
import diary.User;
import diary.entry.DoubleEntry;
import diary.entry.IntegerEntry;
import diary.entry.StringEntry;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.annotation.Nonnull;
import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.Map;

import static com.google.common.base.Preconditions.checkState;

public class FileStorage extends  Storage {
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String ABOUT = "about";
    private static final String OWNER = "owner";
    public static final String ENTRIES = "entries";
    public static final String TYPE = "type";
    public static final String TIMESTAMP = "timestamp";
    public static final String VALUE = "value";
    public static final String ENTRY_NAME = "name";
    @Nonnull private final String userFile;
    @Nonnull private String diaryFile;

    public FileStorage(@Nonnull String userFile, @Nonnull String diaryFile) {
        this.userFile = userFile;
        this.diaryFile = diaryFile;
    }

    @Override
    public boolean initLoad(Map<String, User> users, Map<User, Diary> diaryPerUserMap) {
        boolean loadUsersError = loadUsers(users);
        boolean loadDiariesError = loadDiaries(diaryPerUserMap, users);
        return loadUsersError || loadDiariesError;
    }

    private boolean loadUsers(Map<String, User> users) {
        try (FileReader fileReader = new FileReader(userFile)) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                try {
                    User user = parseUser(line);
                    users.putIfAbsent(user.getLogin(), user);
                } catch (JSONException ex) {
                    System.out.println("Couldn't parse user from line, skipping it: " + line + ". Error: " + ex.getMessage());
                }
            }
            return false;
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            return true;
        }
    }

    @Nonnull
    private User parseUser(String line) {
        JSONObject userObj = new JSONObject(line);

        String login = userObj.getString(LOGIN);
        String password = userObj.getString(PASSWORD);
        String name = userObj.getString(NAME);
        String about = userObj.has(ABOUT) ? userObj.getString(ABOUT) : null;
        return new User(login, password, name, about);
    }

    private boolean loadDiaries(Map<User, Diary> diaryPerUserMap, Map<String, User> users) {
        try (FileReader fileReader = new FileReader(diaryFile)) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                try {
                    Diary diary = parseDiary(line, users);
                    diaryPerUserMap.put(users.get(diary.getOwnerLogin()), diary);
                } catch (JSONException ex) {
                    System.out.println("Couldn't parse user from line, skipping it: " + line + ". Error: " + ex.getMessage());
                }
            }
            return false;
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            return true;
        }
    }

    private Diary parseDiary(String line, Map<String, User> users) {
        JSONObject diaryObj = new JSONObject(line);

        String login = diaryObj.getString(OWNER);
        User owner = users.get(login);
        checkState(owner != null, "No owner in users with login " + login);
        Diary diary = new Diary(owner);
        if (diaryObj.has(ENTRIES)) {
            JSONArray entries = diaryObj.getJSONArray(ENTRIES);
            for (Object entryObj : entries) {
                JSONObject entry = (JSONObject) entryObj;
                String type = entry.getString(TYPE);
                LocalDateTime timestamp = LocalDateTime.parse(entry.getString(TIMESTAMP));
                String entryName = entry.getString(ENTRY_NAME);
                switch (type) {
                    case "integer":
                        diary.addEntry(entryName, new IntegerEntry(timestamp, entry.getInt(VALUE)));
                        break;
                    case "double":
                        diary.addEntry(entryName, new DoubleEntry(timestamp, entry.getDouble(VALUE)));
                        break;
                    case "string":
                        diary.addEntry(entryName, new StringEntry(timestamp, entry.getString(VALUE)));
                        break;
                    default:
                        throw new IllegalStateException("No such entry type: " + type);
                }
            }
        }
        return diary;
    }
}
