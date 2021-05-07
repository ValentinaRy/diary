package diary.client;

import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class StartScene extends Scene {
    public StartScene(ClientConnection clientConnection) {
        super(createTabPane(clientConnection), 640, 480);
    }

    private static TabPane createTabPane(ClientConnection clientConnection) {
        TabPane tabPane = new TabPane();

        Tab tabSignIn = new Tab("Sign In", new SignInNode(clientConnection));
        Tab tabSignUp = new Tab("Sign Up", new SignUpNode(clientConnection));

        tabPane.getTabs().add(tabSignIn);
        tabPane.getTabs().add(tabSignUp);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        return tabPane;
    }
}
