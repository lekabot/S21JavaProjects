package edu.school21.socketClient.client;

import java.io.BufferedReader;

public class IncomingMessagesHandler implements Runnable {
    private BufferedReader reader;
    private volatile boolean running;

    public IncomingMessagesHandler(BufferedReader reader) {
        this.reader = reader;
        this.running = true;
    }

    @Override
    public void run() {
        try {
            String messageFromServer;
            while (running) {
                if ((messageFromServer = reader.readLine()) != null) {
                    System.out.println(messageFromServer);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            this.running = false;
            if (reader != null) {
                reader.close();
            }
        } catch (Exception e) {
            System.out.println("Error closing resources: " + e.getMessage());
        }
    }
}