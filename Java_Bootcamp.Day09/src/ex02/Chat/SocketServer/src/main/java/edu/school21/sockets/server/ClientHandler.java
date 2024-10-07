package edu.school21.sockets.server;

import edu.school21.sockets.exceptions.*;
import edu.school21.sockets.models.Chatroom;
import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ClientHandler implements Runnable {
    private Socket socket;
    private Server server;
    private PrintWriter writer;
    private BufferedReader reader;
    private User currentUser;
    private Chatroom currentRoom;
    private boolean running;

    public ClientHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
        this.running = true;
    }

    @Override
    public void run() {
        try {
            initializeIOStreams();
            while (running) {
                if (currentUser == null) {
                    handleUserAuthentication();
                } else if (currentRoom == null) {
                    handleRoomSelection();
                } else {
                    handleMessaging();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeSocket();
        }
    }

    private void initializeIOStreams() throws IOException {
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream(), true);
        writer.println("Hello from server!");
    }

    private void handleUserAuthentication() throws IOException {
        writer.println("Please choose an option:");
        writer.println("1. Sign Up\n2. Sign In\n3. Exit");
        String message = reader.readLine();

        switch (message) {
            case "1":
                signUpUser();
                break;
            case "2":
                signInUser();
                break;
            case "3":
                logout();
                break;
            default:
                writer.println("Invalid option");
                break;
        }
    }

    private void signUpUser() throws IOException {
        try {
            writer.println(signUp() ? "Successful!" : "Failed!");
        } catch (AlreadyRegisteredException e) {
            writer.println("Failed: " + e.getMessage());
        }
    }

    private void signInUser() throws IOException {
        try {
            if (signIn()) {
                showRoomsMenu();
            }
        } catch (Exception e) {
            writer.println("Failed: " + e.getMessage());
        }
    }

    private void handleRoomSelection() throws IOException {
        String message = reader.readLine();
        switch (message) {
            case "1":
                createChatroom();
                writer.println("Successful!");
                showRoomsMenu();
                break;
            case "2":
                chooseChatroomAndPrintMessages();
                break;
            case "3":
                logout();
                break;
            default:
                writer.println("Invalid option");
                break;
        }
    }

    private void chooseChatroomAndPrintMessages() throws IOException {
        try {
            chooseChatroom(showExistingChatrooms());
        } catch (NoSuchRoomException e) {
            writer.println(e.getMessage());
        }
        printLastMessages(currentRoom);
    }

    private void handleMessaging() throws IOException {
        String message = reader.readLine();
        if (message.equalsIgnoreCase("exit")) {
            writer.println("You have left the chat");
            logout();
            return;
        }
        Message newMessage = new Message(currentUser, message, Timestamp.valueOf(LocalDateTime.now()), currentRoom);
        saveAndBroadcastMessage(newMessage);
    }

    private void saveAndBroadcastMessage(Message newMessage) {
        server.getMessagesService().saveMessage(newMessage);
        currentRoom.broadcastMessage(newMessage);
    }

    private void closeSocket() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean signUp() throws IOException {
        this.writer.println("Enter login:");
        String login = reader.readLine();
        writer.println("Enter password:");
        String password = reader.readLine();
        return this.server.getUsersService().signUp(login, password);
    }

    private boolean signIn() throws IOException {
        if (currentUser != null) {
            throw new AlreadySignedInException("You are already signed in");
        }
        this.writer.println("Enter login:");
        String login = reader.readLine();
        this.writer.println("Enter password:");
        String password = reader.readLine();
        this.currentUser = this.server.getUsersService().signIn(login, password);
        return (currentUser != null);
    }

    private void logout() throws IOException {
        this.writer.println("/exit");
        this.running = false;
        if (this.currentRoom != null) {
            this.currentRoom.removeClient(this);
        }
        if (this.currentUser != null) {
            this.server.getUsersService().logout(currentUser);
            this.currentUser = null;
        }
        System.out.println("Client disconnected: " + this.socket);
        this.writer.close();
        this.reader.close();
    }

    private void showRoomsMenu() {
        this.writer.println("Please choose an option:");
        this.writer.println("1. Create Room");
        this.writer.println("2. Choose Room");
        this.writer.println("3. Exit");
    }

    private void createChatroom() throws IOException {
        this.writer.println("Please, enter a name for a new room:");
        String roomName = reader.readLine();
        if (this.server.getChatroomsService().isExists(roomName)) {
            throw new AlreadyExistsException("A room with the same name " +
                    "already exists");
        }
        Chatroom chatroom = new Chatroom(roomName, currentUser);
        this.server.getChatroomsService().createChatroom(chatroom);
        this.server.addChatroom(chatroom);
    }

    private int showExistingChatrooms() {
        List<Chatroom> rooms = this.server.getExistingChatrooms();
        if (rooms.isEmpty()) {
            writer.println("No rooms to join right now. Please, create a new room.");
        } else {
            for (Chatroom room : rooms) {
                writer.println(room.getId() + ". " + room.getName());
            }
        }
        writer.println((rooms.size() + 1) + ". Exit");
        return rooms.size() + 1;
    }

    private void chooseChatroom(int exitPos) throws IOException {
        String roomPos = reader.readLine();
        if (roomPos.equals(String.valueOf(exitPos))) {
            this.logout();
        } else {
            Optional<Chatroom> optionalChatroom =
                    this.server.getChatroom(Long.parseLong(roomPos));
            if (optionalChatroom.isEmpty()) {
                throw new NoSuchRoomException("Chatroom doesn't exist");
            }
            this.currentRoom = optionalChatroom.get();
            this.currentRoom.addClient(this);
        }
    }

    private void printLastMessages(Chatroom chatroom) {
        if (chatroom != null) {
            List<Message> lastMessages = this.server.getMessagesService()
                    .getLastMessagesInRoom(chatroom);
            for (Message message : lastMessages) {
                writer.println(message.getAuthor().getLogin() + ": " + message.getText());
            }
        }
    }

    public void sendMessage(Message message) {
        writer.println(this.server.getMessagesService().getAuthor(message).getLogin() +
                ": " + message.getText());
    }
}