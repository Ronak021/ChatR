import java.io.*;
import java.net.*;

public class ChatClient {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private boolean isRunning = true;

    public void connectToServer(String host, int port) {
        try {
            socket = new Socket(host, port);
            System.out.println("Connected to the server!");

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            startCommunication();

        } catch (IOException e) {
            System.err.println("Error connecting to server: " + e.getMessage());
        } finally {
            closeConnections();
        }
    }

    private void startCommunication() {
        Thread receiveThread = new Thread(this::receiveMessages);
        receiveThread.start();

        sendMessages();
    }

    private void receiveMessages() {
        try {
            String serverMessage;
            while ((serverMessage = in.readLine()) != null) {
                System.out.println("Server: " + serverMessage);
            }
        } catch (IOException e) {
            System.out.println("Server disconnected. Terminating...");
            isRunning = false;
        } finally {
            closeConnections();
            System.exit(0);
        }
    }

    private void sendMessages() {
        try (BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in))) {
            String clientMessage;
            while (isRunning && (clientMessage = consoleInput.readLine()) != null) {
                out.println(clientMessage);
            }
        } catch (IOException e) {
            System.err.println("Error reading input: " + e.getMessage());
        }
    }

    private void closeConnections() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            System.err.println("Error closing connections: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        ChatClient client = new ChatClient();
        client.connectToServer("localhost", 12345);
    }
}
