package diary;

import static com.google.common.base.Preconditions.checkState;

import diary.entry.DoubleEntry;
import diary.entry.Entry;
import diary.entry.IntegerEntry;
import diary.entry.StringEntry;
import org.apache.commons.lang.ArrayUtils;

import java.time.LocalDateTime;
import java.util.Arrays;

public class CreateCommandProcessor {
    public static void printHelpInfo() {
        System.out.println("Constraints: <login> and <password> should be one word without spaces");
        System.out.println("create user <login> <password> <name> [-about <about>]");
        System.out.println("create diary <login> <password>");
        System.out.println("create entry <login> <password> <entry_name> <type> <value> [-timeStamp <timeStamp>]");
        System.out.println("Entry types: integer, double, string");
    }

    static void processCreateCommand(String[] args) {
        checkState(args.length > 1, "No arguments for create command were passed");
        switch (args[1]) {
            case "user":
                createUserCommand(args);
                break;
            case "diary":
                createDiaryCommand(args);
                break;
            case "entry":
                createEntryCommand(args);
            default:
                throw new IllegalArgumentException("No such parameter in create command: " + args[1]);
        }
    }

    // create user <login> <password> <name> [-about <about>]
    private static void createUserCommand(String[] args) {
        checkState(args.length > 4, "Not enough arguments for user creation");
        String login = args[2];
        String password = args[3];
        String name;
        String about;
        int idx = ArrayUtils.indexOf(args, "-about");
        if (idx < 0) { // no "about" section
            name = String.join(" ", Arrays.copyOfRange(args, 4, args.length));
            about = null;
        } else {
            name = String.join(" ", Arrays.copyOfRange(args, 4, idx));
            about = String.join(" ", Arrays.copyOfRange(args, idx + 1, args.length));
        }
        User user = new User(login, password, name, about);
        if (CmdServer.addUser(user)) {
            System.out.println("User was added: " + user.getLogin());
        } else {
            System.out.println("Error: user with this login already exists");
        }
    }

    // create diary <login> <password>
    private static void createDiaryCommand(String[] args) {
        checkState(args.length > 4, "Not enough arguments for diary creation");
        User user = CommandProcessorUtils.getUserIfPermitted(args[2], args[3]);
        if (CmdServer.createDiary(user)) {
            System.out.println("Diary was created for " + user.getLogin());
        } else {
            System.out.println("Error: diary already exists");
        }
    }

    // create entry <login> <password> <entry_name> <type> <value> [-timeStamp <timeStamp>]
    private static void createEntryCommand(String[] args) {
        checkState(args.length > 6, "Not enough arguments for entry creation");
        User user = CommandProcessorUtils.getUserIfPermitted(args[2], args[3]);
        String entry_name = args[4];
        String type = args[5];
        int idx = ArrayUtils.indexOf(args, "-timeStamp");
        checkState(idx == -1 || idx > 6 && idx + 1 < args.length);
        LocalDateTime timeStamp = idx < 0 ? LocalDateTime.now() : LocalDateTime.parse(args[idx + 1]);
        Entry entry;
        switch (type) {
            case "integer":
                entry = new IntegerEntry(timeStamp, Integer.parseInt(args[6]));
                break;
            case "double":
                entry = new DoubleEntry(timeStamp, Double.parseDouble(args[6]));
                break;
            case "string":
                String value;
                if (idx < 0) { // no "timeStamp" section
                    value = String.join(" ", Arrays.copyOfRange(args, 6, args.length));
                } else {
                    value = String.join(" ", Arrays.copyOfRange(args, 6, idx));
                }
                entry = new StringEntry(timeStamp, value);
                break;
            default:
                throw new IllegalStateException("Error: not supported entry type " + type);
        }
        Diary diary = CmdServer.getDiary(user);
        checkState(diary != null && diary.canAccess(user), "Error: no diary or cannot access");
        diary.addEntry(entry_name, entry);
        System.out.println("Entry was added: " + entry.printEntry());
    }
}
