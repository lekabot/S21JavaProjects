package edu.school21.chat.models;

import java.util.List;
import java.util.Objects;

public class Chatroom {
    private Long id;
    private String name;
    private User owner;
    private List<Message> messages;

    public Chatroom(Long id, String name, User owner, List<Message> messages) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.messages = messages;
    }

    public Chatroom(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Chatroom chatroom = (Chatroom) obj;
        return Objects.equals(id, chatroom.id) &&
                Objects.equals(name, chatroom.name) &&
                Objects.equals(owner, chatroom.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, owner);
    }

    @Override
    public String toString() {
        return "Chatroom{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", owner=" + owner +
                '}';
    }

    public Long getId() {
        return id;
    }
}
