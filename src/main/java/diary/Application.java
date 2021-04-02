package diary;

import diary.server.CmdServer;
import diary.server.Server;
import diary.server.storage.FileStorage;
import diary.server.storage.Storage;

public class Application {
    public static void main(String[] args) {
        new Application().start();
    }

    private void start() {
        Storage fileStorage = new FileStorage("storage/users.txt", "storage/diaries.txt");
        Server server = new CmdServer(fileStorage);
        server.start();
    }
}
