package diary;

import javax.annotation.Nonnull;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkState;

public class CmdServer extends Server {
    private static Set<User> users = new HashSet<>();
    private static Map<User, Diary> diaryPerUserMap = new HashMap<>();

    public static void main(String args[]) {
        printHelpInfo();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        reader.lines().forEach(CmdServer::processSingleCommand);
    }

    private static void printHelpInfo() {
        System.out.println("List of all available commands");
        CreateCommandProcessor.printHelpInfo();
        PrintCommandProcessor.printHelpInfo();
    }

    private static void processSingleCommand(String line) {
        String[] args = line.split(" ");
        checkState(args.length > 0, "No arguments were passed");
        switch (args[0]) {
            case "create":
                CreateCommandProcessor.processCreateCommand(args);
                break;
            case "print":
                PrintCommandProcessor.processPrintCommand(args);
            default:
                throw new IllegalArgumentException("No such command: " + args[0]);
        }
    }

    static boolean addUser(@Nonnull User user) {
        return users.add(user);
    }

}
