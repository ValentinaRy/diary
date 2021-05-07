package diary.client;

import diary.request.RequestBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientConnection {
    private static final Logger log = LogManager.getLogger(ClientConnection.class);
    private final int serverPort;
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;


    public ClientConnection(int serverPort) {
        this.serverPort = serverPort;
        log.info("Created client connector with server on port " + serverPort);
    }

    public boolean connect() {
        try {
            socket = new Socket("localhost", serverPort);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            return true;
        } catch (IOException e) {
            log.error("Couldn't connect to server due to error", e);
            return false;
        }
    }

    public void disconnect() {
        try {
            out.close();
            in.close();
            socket.close();
            log.info("Connection to server was closed");
        } catch (IOException e) {
            log.error("Error during closing connection to server", e);
        }
    }

    @Nullable
    public String signIn(String login, String password) {
        String request = RequestBuilder.signIn(login, password);
        return sendAndGetResponse(request);
    }

    @Nullable
    public String signUp(String login, String password) {
        String request = RequestBuilder.signUp(login, password);
        return sendAndGetResponse(request);
    }

    @Nullable
    private String sendAndGetResponse(String request) {
        log.debug("Sending request to server: {}", request);
        try {
            out.writeUTF(request);
            out.flush();
            String response = in.readUTF();
            if (response.equals("OK")) {
                return null;
            } else {
                return response;
            }
        } catch (IOException e) {
            log.error("Error sending sing in request to server", e);
            return e.getMessage();
        }
    }
}
