package diary.server.socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientConnectionHandler implements Runnable {
    private static final Logger log = LogManager.getLogger(ClientConnectionHandler.class);
    private final Socket client;

    public ClientConnectionHandler(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try (DataOutputStream clientOutput = new DataOutputStream(client.getOutputStream());
             DataInputStream clientInput = new DataInputStream(client.getInputStream())) {

            while (!client.isClosed()) {
                String inMessage = clientInput.readUTF();
                log.debug("Got client message: {}", inMessage);
            }
        } catch (IOException e) {
            log.error("Error while communicating with client", e);
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                log.error("Error while closing client socket", e);
            }
        }
    }
}
