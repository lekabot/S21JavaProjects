package edu.school21.sockets.server;

import edu.school21.sockets.exceptions.AlreadyRegisteredException;
import edu.school21.sockets.exceptions.AlreadySignedInException;
import edu.school21.sockets.exceptions.NotAuthorizedException;
import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import edu.school21.sockets.services.MessageServices;
import edu.school21.sockets.services.UsersServices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ClientHandler implements Runnable {
    private Socket socket;
    private Server server;
    private PrintWriter writer;
    private BufferedReader reader;
    private UsersServices usersService;
    private MessageServices messagesService;
    private User currentUser;

    public ClientHandler(Socket socket, Server server, UsersServices usersService,
                         MessageServices messagesService) {
        this.socket = socket;
        this.server = server;
        this.usersService = usersService;
        this.messagesService = messagesService;
    }

    @Override
    public void run() {
        try {
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println("Hello from server!");
            String message;
            do {
                message = reader.readLine();
                if (message.equalsIgnoreCase("signUp")) {
                    try {
                        writer.println(this.signUp() ? "Successful!" : "Failed!");
                    } catch (AlreadyRegisteredException e) {
                        writer.println("Failed: " + e.getMessage());
                    }
                } else if (message.equalsIgnoreCase("signIn")) {
                    try {
                        writer.println(this.signIn() ? "Start messaging"
                                : "Failed");
                    } catch (Exception e) {
                        writer.println("Failed: " + e.getMessage());
                    }
                } else if (message.equalsIgnoreCase("exit")) {
                    try {
                        this.logout();
                    } catch (Exception e) {
                        writer.println("Failed: " + e.getMessage());
                    }
                } else {
                    if (currentUser == null) {
                        writer.println("You must sign in before sending " +
                                "messages");
                    } else {
                        Message newMessage = new Message(currentUser,
                                message,
                                Timestamp.valueOf(LocalDateTime.now()));
                        messagesService.saveMessage(newMessage);
                        server.broadcastMessage(newMessage);
                    }
                }
            } while (!message.equals("exit"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.server.removeClient(this);
            if (!socket.isClosed()) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean signUp() throws IOException {
        this.writer.println("Enter login:");
        String login = reader.readLine();
        writer.println("Enter password:");
        String password = reader.readLine();
        return this.usersService.signUp(login, password);
    }

    private boolean signIn() throws IOException {
        if (currentUser != null) {
            throw new AlreadySignedInException("You are already signed in");
        }
        this.writer.println("Enter login:");
        String login = reader.readLine();
        writer.println("Enter password:");
        String password = reader.readLine();
        this.currentUser = this.usersService.signIn(login, password);
        return (currentUser != null);
    }

    private void logout() throws IOException {
        if (currentUser == null) {
            throw new NotAuthorizedException("No user to logout");
        }
        this.usersService.logout(currentUser);
        this.server.removeClient(this);
        this.currentUser = null;
        System.out.println("Client disconnected: " + this.socket);
        this.socket.close();
    }

    public void sendMessage(Message message) {
        writer.println(this.messagesService.getAuthor(message).getLogin() +
                ": " + message.getText());
    }

}
