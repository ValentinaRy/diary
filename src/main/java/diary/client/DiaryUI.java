package diary.client;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static com.google.common.base.Preconditions.checkState;
import static javafx.geometry.Pos.CENTER;

public class DiaryUI extends Application {
    Logger log = LogManager.getLogger(DiaryUI.class);
    private ClientConnection clientConnection;

    @Override
    public void start(Stage stage) {
        initConnection();
        stage.setScene(createStartScene());
        stage.show();
        stage.setOnCloseRequest(windowEvent -> closeConnection());
    }

    private Scene createStartScene() {
        TabPane tabPane = new TabPane();

        Tab tabSignIn = new Tab("Sign In", createSignInNode());
        Tab tabSignUp = new Tab("Sign Up", createSignUpNode());

        tabPane.getTabs().add(tabSignIn);
        tabPane.getTabs().add(tabSignUp);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        return new Scene(tabPane, 640, 480);
    }

    private Node createSignInNode() {
        Label loginLabel = new Label("Login");
        TextField loginInput = new TextField();

        Label passwordLabel = new Label("Password");
        PasswordField passwordInput = new PasswordField();

        Button signInButton = new Button("Sign in");
        signInButton.setOnAction(evt -> signInHandler(evt, loginInput, passwordInput));

        GridPane gp = new GridPane();
        gp.add(loginLabel, 0, 0);
        gp.add(loginInput, 1, 0);
        gp.add(passwordLabel, 0, 1);
        gp.add(passwordInput, 1, 1);
        gp.setHgap(10);
        gp.setVgap(10);

        HBox hbox = new HBox(gp);
        hbox.setAlignment(CENTER);

        VBox vbox = new VBox(hbox, signInButton);
        vbox.setSpacing(10);
        vbox.setAlignment(CENTER);

        return vbox;
    }

    private Node createSignUpNode() {
        Label loginLabel = new Label("Login");
        TextField loginInput = new TextField();

        Label passwordLabel = new Label("Password");
        PasswordField passwordInput = new PasswordField();

        Label confirmPasswordLabel = new Label("Confirm Password");
        PasswordField confirmPasswordInput = new PasswordField();

        Button signUpButton = new Button("Sign Up");
        signUpButton.setOnAction(evt -> signUpHandler(evt, loginInput, passwordInput, confirmPasswordInput));

        GridPane gp = new GridPane();
        gp.add(loginLabel, 0, 0);
        gp.add(loginInput, 1, 0);
        gp.add(passwordLabel, 0, 1);
        gp.add(passwordInput, 1, 1);
        gp.add(confirmPasswordLabel, 0, 2);
        gp.add(confirmPasswordInput, 1, 2);
        gp.setHgap(10);
        gp.setVgap(10);

        HBox hbox = new HBox(gp);
        hbox.setAlignment(CENTER);

        VBox vbox = new VBox(hbox, signUpButton);
        vbox.setSpacing(10);
        vbox.setAlignment(CENTER);

        return vbox;
    }

    private void signInHandler(ActionEvent evt, TextField loginInput, PasswordField passwordInput) {
        log.debug("Inputted values: login {} password {}", loginInput.getText(), passwordInput.getText());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Your login and password");
        alert.setHeaderText(null);
        alert.setContentText("Login: "+loginInput.getText()+", Password: "+passwordInput.getText());
        alert.showAndWait();
    }

    private void signUpHandler(ActionEvent evt, TextField loginInput, PasswordField passwordInput, PasswordField confirmPasswordInput) {
        log.debug("Inputted values: login {} password {}, confirm {}", loginInput.getText(), passwordInput.getText(), confirmPasswordInput.getText());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Your login and password");
        alert.setHeaderText(null);
        alert.setContentText("Login: "+loginInput.getText()+", Password: "+passwordInput.getText()+", Confirmation: "+confirmPasswordInput.getText());
        alert.showAndWait();
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
