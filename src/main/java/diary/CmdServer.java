package diary;

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
        System.out.println("create user <login> <password> <name> [-about <about>]");
        System.out.println("create diary <login> <password>");
        System.out.println("create entry <login> <password> <type> <value>");
        System.out.println("print logins");
        System.out.println("print user <login> <password>");
        System.out.println("print diary <login> <password> [<type>]");
    }

    private static void processSingleCommand(String line) {
        String[] args = line.split(" ");
        checkState(args.length > 0, "No arguments were passed");
        switch (args[0]) {
            case "create":
                processCreateCommand(args);
                break;
            default:
                throw new IllegalArgumentException("No such command: " + args[0]);
        }
    }

    private static void processCreateCommand(String[] args) {

    }
}
