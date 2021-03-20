package diary;

import static com.google.common.base.Preconditions.checkState;

public class PrintCommandProcessor {
    public static void printHelpInfo() {
        System.out.println("print logins");
        System.out.println("print user <login> <password>");
        System.out.println("print diary <login> <password> [<type>]");
    }

    public static void processPrintCommand(String[] args) {
        checkState(args.length > 1, "No arguments for print command were passed");
        switch (args[1]) {
            case "logins":
                printLoginsCommand();
                break;
            case "user":
                printUserCommand(args);
                break;
            case "diary":
                printDiaryCommand(args);
                break;
            default:
                throw new IllegalArgumentException("No such parameter in create command: " + args[1]);
        }
    }

    private static void printLoginsCommand() {
        CmdServer.getUsers().values().forEach(user ->
                System.out.println(user.getLogin()));
    }

    private static void printUserCommand(String[] args) {
        checkState(args.length > 3, "Not enough arguments for printing user");
        String login = args[2];
        String password = args[3];
        User user = CmdServer.getUsers().get(login);
        checkState(user != null && user.getPassword().equals(password), "Wrong login or password");
        System.out.println("User information");
        System.out.println("Login: " + user.getLogin());
        System.out.println("Password: " + user.getPassword());
        System.out.println("Name: " + user.getName());
        System.out.println("About: " + user.getAbout());
    }

    private static void printDiaryCommand(String[] args) {
        // TODO: implement method
    }
}
