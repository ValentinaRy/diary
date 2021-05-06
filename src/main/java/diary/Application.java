package diary;

import diary.server.Server;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ServerConfig.class);
        run(context);
        context.close();
    }

    public static void run(ApplicationContext context) {
        Server server = context.getBean(Server.class);
        server.start();
    }
}
