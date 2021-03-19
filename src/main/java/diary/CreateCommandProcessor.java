package diary;

import static com.google.common.base.Preconditions.checkState;

public class CreateCommandProcessor {
    public static void printHelpInfo() {
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
        // TODO: implement method
    }

    private static void createDiaryCommand(String[] args) {
        // TODO: implement method
    }

    private static void createEntryCommand(String[] args) {
        // TODO: implement method
    }
}
