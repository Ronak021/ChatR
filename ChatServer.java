import java.io.*;
import java.net.*;

public class ChatServer {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private boolean isRunning = true;

    public void startServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started. Waiting for a client...");

            clientSocket = serverSocket.accept();
            System.out.println("Client connected!");

            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            startCommunication();

        } catch (IOException e) {
            System.err.println("Error starting the server: " + e.getMessage());
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
            String clientMessage;
            while ((clientMessage = in.readLine()) != null) {
                System.out.println("Client: " + clientMessage);
            }
        } catch (IOException e) {
            System.out.println("Client disconnected. Terminating...");
            isRunning = false;
        } finally {
            closeConnections();
            System.exit(0);
        }
    }

    private void sendMessages() {
        try (BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in))) {
            String serverMessage;
            while (isRunning && (serverMessage = consoleInput.readLine()) != null) {
                out.println(serverMessage);
            }
        } catch (IOException e) {
            System.err.println("Error reading input: " + e.getMessage());
        }
    }

    private void closeConnections() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (clientSocket != null) clientSocket.close();
            if (serverSocket != null) serverSocket.close();
        } catch (IOException e) {
            System.err.println("Error closing connections: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        ChatServer server = new ChatServer();
        server.startServer(12345);
    }
}
