package diary.server.cmd;

import diary.server.Server;
import diary.server.storage.Storage;

import javax.annotation.Nonnull;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import static com.google.common.base.Preconditions.checkState;

public class CmdServer extends Server {
    @Nonnull private final CreateCommandProcessor createCommandProcessor;
    @Nonnull private final PrintCommandProcessor printCommandProcessor;

    public CmdServer(Storage storage) {
        super(storage);
        createCommandProcessor = new CreateCommandProcessor(this);
        printCommandProcessor = new PrintCommandProcessor(this);
    }

    public void start() {
        printHelpInfo();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        reader.lines().forEach(this::processSingleCommandSafe);
    }

    private void printHelpInfo() {
        System.out.println("List of all available commands");
        createCommandProcessor.printHelpInfo();
        printCommandProcessor.printHelpInfo();
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
                createCommandProcessor.processCreateCommand(args);
                break;
            case "print":
                printCommandProcessor.processPrintCommand(args);
                break;
            case "flush":
                storage.flush(users, diaryPerUserMap);
                break;
            default:
                throw new IllegalStateException("No such command: " + args[0]);
        }
    }
}
