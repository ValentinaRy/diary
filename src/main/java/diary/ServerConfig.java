package diary;

import diary.server.CmdServer;
import diary.server.Server;
import diary.server.storage.FileStorage;
import diary.server.storage.Storage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerConfig {
    @Bean
    public Storage storage(String userFile, String diaryFile) {
        return new FileStorage(userFile, diaryFile);
    }

    @Bean
    public Server server(Storage storage) {
        return new CmdServer(storage);
    }
}
