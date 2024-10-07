package edu.school21.sockets.services;

import edu.school21.sockets.models.Chatroom;

import java.util.List;

public interface ChatroomsService {
    boolean isExists(String name);
    void createChatroom(Chatroom chatroom);
    List<Chatroom> getExistingChatrooms();
    Chatroom getChatroomById(Long id);
}
