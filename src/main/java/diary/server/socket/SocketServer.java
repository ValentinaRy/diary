package diary.server.socket;

import diary.server.Server;
import diary.server.storage.Storage;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

public class SocketServer extends Server {
    private final ExecutorService executor;
    private final int port;

    public SocketServer(@Nonnull Storage storage, ExecutorService executor, int port) {
        super(storage);
        this.executor = executor;
        this.port = port;
        System.out.println("Socket server created with port number " + port);
    }

    @Override
    public void start() {
        try (ServerSocket server = new ServerSocket(port)) {
            Socket client = server.accept();
            executor.execute(new ClientConnectionHandler(client));
        } catch (IOException e) {
            System.out.println("Error while accepting new connections: "+e.getMessage());
            e.printStackTrace();
        }
    }
}
