package diary.server.socket;

import diary.server.Server;
import diary.server.storage.Storage;

import javax.annotation.Nonnull;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer extends Server {
    private final int port;

    public SocketServer(@Nonnull Storage storage, int port) {
        super(storage);
        this.port = port;
        System.out.println("Socket server created with port number " + port);
    }

    @Override
    public void start() {
        try (ServerSocket server = new ServerSocket(port)) {
            Socket client = server.accept();
            DataOutputStream clientOutput = new DataOutputStream(client.getOutputStream());
            DataInputStream clientInput = new DataInputStream(client.getInputStream());
            while (!client.isClosed()) {
                String inMessage = clientInput.readUTF();
                clientOutput.writeUTF("server response");
                clientOutput.flush();
            }
            clientOutput.close();
            clientInput.close();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
