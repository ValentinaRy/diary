package diary;

import static com.google.common.base.Preconditions.checkState;
import org.apache.commons.lang.ArrayUtils;

import java.util.Arrays;

public class CreateCommandProcessor {
    public static void printHelpInfo() {
        System.out.println("Constraints: <login> and <password> should be one word without spaces");
        System.out.println("create user <login> <password> <name> [-about <about>]");
        System.out.println("create diary <login> <password>");
        System.out.println("create entry <login> <password> <type> <value>");
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

    private static void createDiaryCommand(String[] args) {
        // TODO: implement method
    }

    private static void createEntryCommand(String[] args) {
        // TODO: implement method
    }
}
