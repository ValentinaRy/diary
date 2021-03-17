package diary;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

import static com.google.common.base.Preconditions.checkState;

public class CmdServer extends Server {
    private static Map<User, Diary> diaryPerUserMap;

    public static void main(String args[]) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        reader.lines().forEach(CmdServer::processSingleCommand);
    }

    private static void processSingleCommand(String line) {
        String[] args = line.split(" ");
        checkState(args.length > 0, "No arguments were passed");
        switch (args[0]) {

        }
    }
}
