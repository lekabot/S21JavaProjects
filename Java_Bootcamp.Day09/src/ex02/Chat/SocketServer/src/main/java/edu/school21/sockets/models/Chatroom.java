package edu.school21.sockets.models;

import edu.school21.sockets.server.ClientHandler;
import lombok.*;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "chatrooms")
public class Chatroom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Transient
    private List<ClientHandler> clients = new ArrayList<>();


    public Chatroom(String name, User owner) {
        this.name = name;
        this.owner = owner;
    }

    public synchronized void addClient(ClientHandler client) {
        this.clients.add(client);
    }

    public synchronized void removeClient(ClientHandler client) {
        this.clients.remove(client);
    }

    public synchronized void broadcastMessage(Message message) {
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }

}
