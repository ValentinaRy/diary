package diary.client;

import java.io.IOException;
import java.net.Socket;

public class ClientConnection {
    private final int serverPort;
    private Socket socket;


    public ClientConnection(int serverPort) {
        this.serverPort = serverPort;
        System.out.println("Created client connector with server on port " + serverPort);
    }

    public boolean connect() {
        try {
            socket = new Socket("localhost", serverPort);
            return true;
        } catch (IOException e) {
            System.out.println("Couldn't connect to server due to error: "+e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public void disconnect() {
        try {
            socket.close();
            System.out.println("Connection to server was closed");
        } catch (IOException e) {
            System.out.println("Error during closing connection to server: "+e.getMessage());
            e.printStackTrace();
        }
    }
}
