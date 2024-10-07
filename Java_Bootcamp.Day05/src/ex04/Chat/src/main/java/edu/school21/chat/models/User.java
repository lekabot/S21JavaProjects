package edu.school21.chat.models;

import java.util.List;
import java.util.Objects;

public class User {
    private Long id;
    private String login;
    private String password;
    private List<Chatroom> createdRooms;
    private List<Chatroom> socializedRooms;

    public User(Long id, String login, String password, List<Chatroom> createdRooms, List<Chatroom> socializedRooms) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.createdRooms = createdRooms;
        this.socializedRooms = socializedRooms;
    }

    public User(Long id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return Objects.equals(this.id, user.id) &&
                Objects.equals(this.login, user.login) &&
                Objects.equals(this.password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.login, this.password);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setCreatedRooms(List<Chatroom> roomsFromArray) {
        this.createdRooms = roomsFromArray;
    }

    public void setSocializedRooms(List<Chatroom> roomsFromArray) {
        this.socializedRooms = roomsFromArray;
    }

    public String getCreatedRooms() {
        StringBuilder roomsString = new StringBuilder();
        if (createdRooms != null && !createdRooms.isEmpty()) {
            for (Chatroom room : createdRooms) {
                roomsString.append(room.getId()).append(": ").append(room.getName()).append(", ");
            }
            roomsString.setLength(roomsString.length() - 2);
        } else {
            roomsString.append("No created rooms");
        }
        return roomsString.toString();
    }

    public String getSocializedRooms() {
        StringBuilder roomsString = new StringBuilder();
        if (socializedRooms != null && !socializedRooms.isEmpty()) {
            for (Chatroom room : socializedRooms) {
                roomsString.append(room.getId()).append(": ").append(room.getName()).append(", ");
            }
            roomsString.setLength(roomsString.length() - 2);
        } else {
            roomsString.append("No socialized rooms");
        }
        return roomsString.toString();
    }
}
