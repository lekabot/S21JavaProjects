package edu.school21.sockets.services;

import edu.school21.sockets.exceptions.NoSuchRoomException;
import edu.school21.sockets.models.Chatroom;
import edu.school21.sockets.repositories.ChatroomsRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatroomsServiceImpl implements ChatroomsService {

    private ChatroomsRepository chatroomsRepository;

    @Autowired
    public ChatroomsServiceImpl(ChatroomsRepository chatroomsRepository) {
        this.chatroomsRepository = chatroomsRepository;
    }

    @Override
    public boolean isExists(String name) {
        return chatroomsRepository.findByName(name).isPresent();
    }

    @Override
    public synchronized void createChatroom(Chatroom chatroom) {
        chatroomsRepository.save(chatroom);
    }

    @Override
    public List<Chatroom> getExistingChatrooms() {
        return chatroomsRepository.findAll();
    }

    @Override
    public Chatroom getChatroomById(Long id) {
        Optional<Chatroom> byId = chatroomsRepository.findById(id);
        if (byId.isEmpty()) {
            throw new NoSuchRoomException("No such room");
        }
        return byId.get();
    }
}
