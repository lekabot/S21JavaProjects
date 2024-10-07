package edu.school21.sockets.server;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.services.MessageServices;
import edu.school21.sockets.services.UsersServices;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

@Component
public class Server {
    private int port;
    private Set<ClientHandler> clients;
    private UsersServices usersServices;
    private MessageServices messageServices;

    @Autowired
    public Server(@Value("${server.port}") int port, @Qualifier("usersServicesImpl") UsersServices usersService, MessageServices messagesService) {
        this.port = port;
        this.clients = new HashSet<>();
        this.usersServices = usersService;
        this.messageServices = messagesService;
    }

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(this.port)) {
            System.out.println("Server is listening on port " + port);
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected: " + socket);
                ClientHandler clientHandler = new ClientHandler(socket, this,
                        usersServices, messageServices);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public synchronized void removeClient(ClientHandler client) {
        clients.remove(client);
    }

    public synchronized void broadcastMessage(Message message) {
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }
}
