package edu.school21.socketClient.client;

import lombok.val;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    private String hostname;
    private int port;

    public Client(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public void startClient() {
        try (Socket socket = new Socket(hostname, port)) {
            val outputStream = socket.getOutputStream();
            val inputStream = socket.getInputStream();
            val writer = new PrintWriter(outputStream, true);
            val reader = new BufferedReader(new InputStreamReader(inputStream));
            val consoleReader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println(reader.readLine());
            String response;
            String text;
            do {
                text = consoleReader.readLine();
                writer.println(text);
                response = reader.readLine();
                if (response != null) {
                    System.out.println(response);
                }
            } while (!response.equals("Successful!"));

        } catch (UnknownHostException e) {
            System.out.println(STR."Server is not found: \{e.getMessage()}");
        } catch (IOException e) {
            System.out.println(STR."I/O error: \{e.getMessage()}");
        }
    }
}
