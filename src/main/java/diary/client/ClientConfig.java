package diary.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:diary.properties")
public class ClientConfig {
    @Bean
    public ClientConnection clientConnection(@Value("${socket.server.port}") int port) {
        return new ClientConnection(port);
    }
}
