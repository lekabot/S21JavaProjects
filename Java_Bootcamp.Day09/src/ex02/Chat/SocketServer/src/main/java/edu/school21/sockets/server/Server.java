package edu.school21.sockets.server;

import edu.school21.sockets.models.Chatroom;
import edu.school21.sockets.services.ChatroomsService;
import edu.school21.sockets.services.MessagesService;
import edu.school21.sockets.services.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class Server {
    private int port;
    private UsersService usersService;
    private MessagesService messagesService;
    private ChatroomsService chatroomsService;
    private List<Chatroom> chatrooms;


    @Autowired
    public Server(@Value("${server.port}") int port,
                  UsersService usersService, MessagesService messagesService,
                  ChatroomsService chatroomsService) {
        this.port = port;
        this.usersService = usersService;
        this.messagesService = messagesService;
        this.chatroomsService = chatroomsService;
        this.chatrooms = scanExistingChatrooms();
    }

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected: " + socket);
                ClientHandler clientHandler = new ClientHandler(socket, this);
                new Thread(clientHandler).start();
            }
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public UsersService getUsersService() {
        return usersService;
    }

    public MessagesService getMessagesService() {
        return messagesService;
    }

    public ChatroomsService getChatroomsService() {
        return chatroomsService;
    }

    public List<Chatroom> scanExistingChatrooms() {
        return chatroomsService.getExistingChatrooms();
    }

    public List<Chatroom> getExistingChatrooms() {
        return chatrooms;
    }

    public Optional<Chatroom> getChatroom(Long id) {
        return chatrooms.stream().filter(room -> room.getId() == id).findAny();
    }

    public synchronized void addChatroom(Chatroom chatroom) {
        chatrooms.add(chatroom);
    }
}
