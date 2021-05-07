package diary.client;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static javafx.geometry.Pos.CENTER;

public class SignInNode extends VBox {
    private static final Logger log = LogManager.getLogger(SignInNode.class);
    private final ClientConnection clientConnection;

    public SignInNode(ClientConnection clientConnection) {
        this.clientConnection = clientConnection;
        fillNode();
    }

    private void fillNode() {
        Label loginLabel = new Label("Login");
        TextField loginInput = new TextField();

        Label passwordLabel = new Label("Password");
        PasswordField passwordInput = new PasswordField();

        Button signInButton = new Button("Sign in");
        signInButton.setOnAction(evt -> signInHandler(loginInput, passwordInput));

        GridPane gp = new GridPane();
        gp.add(loginLabel, 0, 0);
        gp.add(loginInput, 1, 0);
        gp.add(passwordLabel, 0, 1);
        gp.add(passwordInput, 1, 1);
        gp.setHgap(10);
        gp.setVgap(10);

        HBox hbox = new HBox(gp);
        hbox.setAlignment(CENTER);

        getChildren().add(hbox);
        getChildren().add(signInButton);
        setSpacing(10);
        setAlignment(CENTER);
    }

    private void signInHandler(TextField loginInput, PasswordField passwordInput) {
        String login = loginInput.getText();
        String password = passwordInput.getText();
        log.debug("Inputted values: login {} password {}", login, password);
        String errorMsg = clientConnection.signIn(login, password);
        if (errorMsg != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Sign In");
            alert.setHeaderText("Couldn't sign in");
            alert.setContentText(errorMsg);
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sign In");
            alert.setHeaderText(null);
            alert.setContentText("Sign In successful!");
            alert.showAndWait();
        }
    }
}
