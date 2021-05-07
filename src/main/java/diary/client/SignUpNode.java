package diary.client;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static javafx.geometry.Pos.CENTER;

public class SignUpNode extends VBox {
    private static final Logger log = LogManager.getLogger(SignUpNode.class);
    private final ClientConnection clientConnection;

    public SignUpNode(ClientConnection clientConnection) {
        this.clientConnection = clientConnection;
        fillNode();
    }

    private void fillNode() {
        Label loginLabel = new Label("Login");
        TextField loginInput = new TextField();

        Label passwordLabel = new Label("Password");
        PasswordField passwordInput = new PasswordField();

        Label confirmPasswordLabel = new Label("Confirm Password");
        PasswordField confirmPasswordInput = new PasswordField();

        Button signUpButton = new Button("Sign Up");
        signUpButton.setOnAction(evt -> signUpHandler(loginInput, passwordInput, confirmPasswordInput));

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

        getChildren().add(hbox);
        getChildren().add(signUpButton);
        setSpacing(10);
        setAlignment(CENTER);
    }

    private void signUpHandler(TextField loginInput, PasswordField passwordInput, PasswordField confirmPasswordInput) {
        String login = loginInput.getText();
        String password = passwordInput.getText();
        String confirmation = confirmPasswordInput.getText();
        log.debug("Inputted values: login {} password {}, confirm {}", login, password, confirmation);
        if (!password.equals(confirmation)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Password confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Inputted password and password confirmation are not equal!");
            alert.showAndWait();
        } else {
            String errorMsg = clientConnection.signUp(login, password);
            if (errorMsg != null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Sign Up");
                alert.setHeaderText("Couldn't sign up");
                alert.setContentText(errorMsg);
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sign Up");
                alert.setHeaderText(null);
                alert.setContentText("Sign Up successful!");
                alert.showAndWait();
            }
        }
    }
}
