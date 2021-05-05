package diary.server.cmd;

import diary.Diary;
import diary.User;
import diary.server.Server;

import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkState;

public class PrintCommandProcessor {
    @Nonnull private final Server server;

    public PrintCommandProcessor(@Nonnull Server server) {
        this.server = server;
    }

    public void printHelpInfo() {
        System.out.println("print logins");
        System.out.println("print user <login> <password>");
        System.out.println("print diary <login> <password> [<entry_name>]");
    }

    public void processPrintCommand(String[] args) {
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
                throw new IllegalStateException("No such parameter in create command: " + args[1]);
        }
    }

    // print logins
    private void printLoginsCommand() {
        server.getUsers().values().forEach(user ->
                System.out.println(user.getLogin()));
    }

    // print user <login> <password>
    private void printUserCommand(String[] args) {
        checkState(args.length > 3, "Not enough arguments for printing user");
        User user = CommandProcessorUtils.getUserIfPermitted(server, args[2], args[3]);
        System.out.println("User information");
        System.out.println("Login: " + user.getLogin());
        System.out.println("Password: " + user.getPassword());
        System.out.println("Name: " + user.getName());
        System.out.println("About: " + user.getAbout());
    }

    // print diary <login> <password> [<entry_name>]
    private void printDiaryCommand(String[] args) {
        checkState(args.length > 3, "Not enough arguments for printing diary");
        User user = CommandProcessorUtils.getUserIfPermitted(server, args[2], args[3]);
        String entryName = args.length > 4 ? args[4] : null;
        Diary diary = CommandProcessorUtils.getDiaryIfPermitted(server, user);
        if (entryName == null) {
            System.out.println(diary.printDiary());
        } else {
            System.out.println(diary.printByEntryName(entryName));
        }
    }
}
