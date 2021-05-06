package diary.server.socket;

import diary.server.Server;
import diary.server.storage.Storage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

public class SocketServer extends Server {
    private static final Logger log = LogManager.getLogger(SocketServer.class);
    private final ExecutorService executor;
    private final int port;

    public SocketServer(@Nonnull Storage storage, ExecutorService executor, int port) {
        super(storage);
        this.executor = executor;
        this.port = port;
        log.info("Socket server created with port number {}", port);
    }

    @Override
    public void start() {
        try (ServerSocket server = new ServerSocket(port)) {
            Socket client = server.accept();
            executor.execute(new ClientConnectionHandler(client));
        } catch (IOException e) {
            log.error("Error while accepting new connections", e);
        }
    }
}
