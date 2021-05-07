package diary.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.Socket;

public class ClientConnection {
    private static final Logger log = LogManager.getLogger(ClientConnection.class);
    private final int serverPort;
    private Socket socket;


    public ClientConnection(int serverPort) {
        this.serverPort = serverPort;
        log.info("Created client connector with server on port " + serverPort);
    }

    public boolean connect() {
        try {
            socket = new Socket("localhost", serverPort);
            return true;
        } catch (IOException e) {
            log.error("Couldn't connect to server due to error", e);
            return false;
        }
    }

    public void disconnect() {
        try {
            socket.close();
            log.info("Connection to server was closed");
        } catch (IOException e) {
            log.error("Error during closing connection to server", e);
        }
    }

    public String signIn(String login, String password) {
        return null;
    }

    public String signUp(String login, String password) {
        return null;
    }
}
