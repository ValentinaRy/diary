package diary;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

public class CmdServer extends Server {
    private static Map<User, Diary> diaryPerUserMap;

    public static void main(String args[]) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        reader.lines().forEach(CmdServer::processSingleCommand);
    }

    private static void processSingleCommand(String line) {
        // TODO: develop reading commands from command line
    }
}
