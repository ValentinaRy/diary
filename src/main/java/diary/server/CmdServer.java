package diary.server;

import diary.server.storage.Storage;

import javax.annotation.Nonnull;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import static com.google.common.base.Preconditions.checkState;

public class CmdServer extends Server {
    public CmdServer(Storage storage) {
        super(storage);
    }

    public void start() {
        if (!storage.initLoad(users, diaryPerUserMap)) {
            System.out.println("Error: couldn't initialize server storage. Exiting program");
            return;
        }
        printHelpInfo();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        reader.lines().forEach(this::processSingleCommandSafe);
    }

    private void printHelpInfo() {
        System.out.println("List of all available commands");
        CreateCommandProcessor.printHelpInfo();
        PrintCommandProcessor.printHelpInfo();
        System.out.println("flush");
        System.out.print(">");
    }

    private void processSingleCommandSafe(String line) {
        try {
            processSingleCommand(line);
        } catch (IllegalStateException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        System.out.print(">");
    }

    private void processSingleCommand(@Nonnull String line) {
        String[] args = line.split(" ");
        checkState(args.length > 0, "No arguments were passed");
        switch (args[0]) {
            case "create":
                CreateCommandProcessor.processCreateCommand(args);
                break;
            case "print":
                PrintCommandProcessor.processPrintCommand(args);
                break;
            case "flush":
                storage.flush(users, diaryPerUserMap);
                break;
            default:
                throw new IllegalStateException("No such command: " + args[0]);
        }
    }
}
