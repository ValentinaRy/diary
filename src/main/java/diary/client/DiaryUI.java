package diary.client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static com.google.common.base.Preconditions.checkState;

public class DiaryUI extends Application {
    private ClientConnection clientConnection;

    @Override
    public void start(Stage stage) {
        initConnection();
        Label l = new Label("Hello, cool staff will be here soon!");
        Scene scene = new Scene(new StackPane(l), 640, 480);
        stage.setScene(scene);
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
