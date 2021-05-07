package diary.client;

import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static com.google.common.base.Preconditions.checkState;

public class DiaryUI extends Application {
    private static final Logger log = LogManager.getLogger(DiaryUI.class);
    private ClientConnection clientConnection;

    @Override
    public void start(Stage stage) {
        initConnection();
        stage.setScene(new StartScene(clientConnection));
        stage.show();
        stage.setOnCloseRequest(windowEvent -> closeConnection());
    }

    private void initConnection() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ClientConfig.class);
        clientConnection = context.getBean(ClientConnection.class);
        checkState(clientConnection.connect(), "Connection to server was unsuccessful");
    }

    private void closeConnection() {
        clientConnection.disconnect();
    }

    public static void main(String[] args) {
        launch();
    }
}
