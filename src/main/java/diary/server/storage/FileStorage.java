package diary.server.storage;

import diary.Diary;
import diary.User;
import diary.entry.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.annotation.Nonnull;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
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
        System.out.println("File storage created with user file: "+userFile+", and diary file: "+diaryFile);
    }

    @Override
    public boolean initLoad(@Nonnull Map<String, User> users, @Nonnull Map<User, Diary> diaryPerUserMap) {
        boolean loadUsers = loadUsers(users);
        boolean loadDiaries = loadDiaries(diaryPerUserMap, users);
        return loadUsers && loadDiaries;
    }

    private boolean loadUsers(@Nonnull Map<String, User> users) {
        System.out.println("Starting to load users");
        try (FileReader fileReader = new FileReader(userFile)) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            int errorLines = 0;
            int correctEntries = 0;
            while ((line = bufferedReader.readLine()) != null) {
                try {
                    User user = parseUser(line);
                    users.putIfAbsent(user.getLogin(), user);
                    correctEntries++;
                } catch (JSONException ex) {
                    System.out.println("Couldn't parse user from line, skipping it: " + line + ". Error: " + ex.getMessage());
                    errorLines++;
                }
            }
            System.out.println("Finished reading users, correct entries: "+correctEntries+", error entries: "+errorLines);
            return errorLines == 0;
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            return false;
        }
    }

    @Nonnull
    private User parseUser(@Nonnull String line) {
        JSONObject userObj = new JSONObject(line);

        String login = userObj.getString(LOGIN);
        String password = userObj.getString(PASSWORD);
        String name = userObj.getString(NAME);
        String about = userObj.has(ABOUT) ? userObj.getString(ABOUT) : null;
        return new User(login, password, name, about);
    }

    private boolean loadDiaries(@Nonnull Map<User, Diary> diaryPerUserMap, @Nonnull Map<String, User> users) {
        System.out.println("Starting to load diaries");
        try (FileReader fileReader = new FileReader(diaryFile)) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            int errorLines = 0;
            int correctEntries = 0;
            while ((line = bufferedReader.readLine()) != null) {
                try {
                    Diary diary = parseDiary(line, users);
                    diaryPerUserMap.put(users.get(diary.getOwnerLogin()), diary);
                    correctEntries++;
                } catch (JSONException ex) {
                    System.out.println("Couldn't parse user from line, skipping it: " + line + ". Error: " + ex.getMessage());
                    errorLines++;
                }
            }
            System.out.println("Finished reading diaries, correct entries: "+correctEntries+", error entries: "+errorLines);
            return errorLines == 0;
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            return false;
        }
    }

    private Diary parseDiary(@Nonnull String line, @Nonnull Map<String, User> users) {
        JSONObject diaryObj = new JSONObject(line);

        String login = diaryObj.getString(OWNER);
        User owner = users.get(login);
        checkState(owner != null, "No owner in users with login " + login);
        Diary diary = new Diary(owner);
        if (diaryObj.has(ENTRIES)) {
            JSONArray entries = diaryObj.getJSONArray(ENTRIES);
            for (Object entryObj : entries) {
                JSONObject entry = (JSONObject) entryObj;
                EntryType type = EntryType.parse(entry.getString(TYPE));
                LocalDateTime timestamp = LocalDateTime.parse(entry.getString(TIMESTAMP));
                String entryName = entry.getString(ENTRY_NAME);
                switch (type) {
                    case INTEGER:
                        diary.addEntry(entryName, new IntegerEntry(timestamp, entry.getInt(VALUE)));
                        break;
                    case DOUBLE:
                        diary.addEntry(entryName, new DoubleEntry(timestamp, entry.getDouble(VALUE)));
                        break;
                    case STRING:
                        diary.addEntry(entryName, new StringEntry(timestamp, entry.getString(VALUE)));
                        break;
                    default:
                        throw new IllegalStateException("No such entry type: " + type);
                }
            }
        }
        return diary;
    }

    @Override
    public void flush(@Nonnull Map<String, User> users, @Nonnull Map<User, Diary> diaryPerUserMap) {
        flushUsers(users);
        flushDiaries(diaryPerUserMap);
    }

    private void flushUsers(@Nonnull Map<String, User> users) {
        try (FileWriter fileWriter = new FileWriter(userFile)) {
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (User user : users.values()) {
                bufferedWriter.write(userToJson(user) +"\n");
            }
            bufferedWriter.flush();
            System.out.println("Flushed users");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private String userToJson(User user) {
        JSONObject userObj = new JSONObject();
        userObj.put(LOGIN, user.getLogin());
        userObj.put(PASSWORD, user.getPassword());
        userObj.put(NAME, user.getName());
        if (user.getAbout() != null)
            userObj.put(ABOUT, user.getAbout());
        return userObj.toString();
    }

    private void flushDiaries(@Nonnull Map<User, Diary> diaryPerUserMap) {
        try (FileWriter fileWriter = new FileWriter(diaryFile)) {
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (Diary diary : diaryPerUserMap.values()) {
                bufferedWriter.write(diaryToJson(diary) + "\n");
            }
            bufferedWriter.flush();
            System.out.println("Flushed diaries");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private String diaryToJson(Diary diary) {
        JSONObject diaryObj = new JSONObject();
        diaryObj.put(OWNER, diary.getOwnerLogin());
        if (diary.getEntries().size() > 0) {
            JSONArray entries = new JSONArray();
            diary.getEntries().forEach((name, entryList) -> {
                for (Entry entry : entryList.getEntries()) {
                    JSONObject entryObj = new JSONObject();
                    entryObj.put(ENTRY_NAME, name);
                    if (entry instanceof IntegerEntry) {
                        entryObj.put(TYPE, EntryType.INTEGER.string());
                        entryObj.put(VALUE, ((IntegerEntry) entry).getValue());
                    } else if (entry instanceof DoubleEntry) {
                        entryObj.put(TYPE, EntryType.DOUBLE.string());
                        entryObj.put(VALUE, ((DoubleEntry) entry).getValue());
                    } else if (entry instanceof StringEntry) {
                        entryObj.put(TYPE, EntryType.STRING.string());
                        entryObj.put(VALUE, ((StringEntry) entry).getValue());
                    }
                    entryObj.put(TIMESTAMP, entry.getTimeStamp().toString());
                    entries.put(entryObj);
                }
            });
            diaryObj.put(ENTRIES, entries);
        }
        return diaryObj.toString();
    }
}
