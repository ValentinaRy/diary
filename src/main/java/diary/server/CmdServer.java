package diary.server;

import diary.*;
import diary.server.storage.FileStorage;
import diary.server.storage.Storage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

import static com.google.common.base.Preconditions.checkState;

public class CmdServer extends Server {
    private static final Storage storage = new FileStorage("users.txt", "diaries.txt");
    private static final Map<String, User> users = new HashMap<>();
    private static final Map<User, Diary> diaryPerUserMap = new HashMap<>();

    public static void main(String[] args) {
        storage.initLoad(users, diaryPerUserMap);
        printHelpInfo();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        reader.lines().forEach(CmdServer::processSingleCommandSafe);
    }

    private static void printHelpInfo() {
        System.out.println("List of all available commands");
        CreateCommandProcessor.printHelpInfo();
        PrintCommandProcessor.printHelpInfo();
        System.out.print(">");
    }

    private static void processSingleCommandSafe(String line) {
        try {
            processSingleCommand(line);
        } catch (IllegalStateException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private static void processSingleCommand(@Nonnull String line) {
        String[] args = line.split(" ");
        checkState(args.length > 0, "No arguments were passed");
        switch (args[0]) {
            case "create":
                CreateCommandProcessor.processCreateCommand(args);
                break;
            case "print":
                PrintCommandProcessor.processPrintCommand(args);
                break;
            default:
                throw new IllegalStateException("No such command: " + args[0]);
        }
        System.out.print(">");
    }

    static boolean addUser(@Nonnull User user) {
        return users.putIfAbsent(user.getLogin(), user) == null;
    }

    @Nonnull
    static Map<String, User> getUsers() {
        return users;
    }

    static boolean createDiary(@Nonnull User user) {
        return diaryPerUserMap.putIfAbsent(user, new Diary(user)) == null;
    }

    @Nullable
    static Diary getDiary(@Nonnull User user) {
        return diaryPerUserMap.get(user);
    }
}
