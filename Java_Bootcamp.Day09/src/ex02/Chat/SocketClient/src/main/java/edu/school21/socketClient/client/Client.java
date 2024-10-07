package edu.school21.socketClient.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private final String hostname;
    private final int port;
    private boolean running;
    private Socket socket;
    private PrintWriter serverWriter;
    private BufferedReader serverReader;
    private BufferedReader consoleReader;

    public Client(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
        running = true;
    }

    public void startClient() {
        try {
            socket = new Socket();
            serverWriter = new PrintWriter(socket.getOutputStream(), true);
            serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            consoleReader = new BufferedReader(new InputStreamReader(System.in));

            IncomingMessagesHandler messagesHandler = new IncomingMessagesHandler(serverReader, this);
            Thread handlerThread = new Thread(messagesHandler);
            handlerThread.start();
            String messageToServer;
            while (running) {
                if (consoleReader.ready()) {
                    messageToServer = consoleReader.readLine();
                    serverWriter.println(messageToServer);
                }
                Thread.sleep(100);
            }
            handlerThread.join();
        } catch (Exception e) {
            System.out.println(STR."Client error \{e.getMessage()}");
        }
    }

    public void stop() {
        try {
            running = false;
            if (consoleReader != null) {
                consoleReader.close();
            }
            if (serverWriter != null) {
                serverWriter.close();
            }
            if (serverReader != null) {
                serverReader.close();
            }
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
