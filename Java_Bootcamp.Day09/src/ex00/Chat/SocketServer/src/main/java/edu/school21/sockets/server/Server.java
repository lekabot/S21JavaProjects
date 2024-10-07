package edu.school21.sockets.server;

import edu.school21.sockets.services.UsersServices;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@Component
public class Server {
    private int port;
    private UsersServices usersServices;

    public Server(@Value("${server.port}") int port, UsersServices usersServices) {
        this.port = port;
        this.usersServices = usersServices;
    }

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println(STR."Server is listening on port \{port}");
            val socket = serverSocket.accept();
            System.out.println("New client connected");
            handleClient(socket);
        } catch (IOException e) {
            System.out.println(STR."Server exception: \{e.getMessage()}");
            e.printStackTrace();
        }
    }

    public void handleClient(Socket socket) throws IOException {
        try (BufferedReader reader =
                     new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)
        ) {
            writer.println("Hello server");
            String command = reader.readLine();
            if (command.equals("signUp")) {
                writer.println("Enter login:");
                String login = reader.readLine();
                writer.println("Enter password");
                String password = reader.readLine();
                writer.println(usersServices.signUp(login, password));
            } else {
                writer.println("Unknown command");
            }
        } catch (IOException e) {
            System.out.println("Client handler exception: " + e.getMessage());
            e.printStackTrace();
        } finally {
            socket.close();
        }
    }
}
