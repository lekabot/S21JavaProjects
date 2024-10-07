package edu.school21.socketClient.client;

import java.io.*;
import java.net.Socket;

public class Client {
    private String hostname;
    private int port;

    public Client(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public void startClient() {
        try (Socket socket = new Socket(hostname, port)) {
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            IncomingMessagesHandler messagesHandler =
                    new IncomingMessagesHandler(reader);
            Thread handlerThread = new Thread(messagesHandler);
            handlerThread.start();
            String messageToServer;
            do {
                messageToServer = consoleReader.readLine();
                writer.println(messageToServer);
            } while (!messageToServer.equals("exit"));
            messagesHandler.stop();
            handlerThread.join();
        } catch (Exception e) {
            System.out.println("Clients error: " + e.getMessage());
        }
    }
}
