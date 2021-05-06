package diary.server.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientConnectionHandler implements Runnable {
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
                System.out.println("Got client message: "+inMessage);
            }
        } catch (IOException e) {
            System.out.println("Error while communicating with client: "+e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                System.out.println("Error while closing client socket: "+e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
