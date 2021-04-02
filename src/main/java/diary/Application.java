package diary;

import diary.server.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        run(context);
        context.close();
    }

    public static void run(ApplicationContext context) {
        Server server = context.getBean(Server.class);
        server.start();
    }
}
