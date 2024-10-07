package edu.school21.sockets.services;

import edu.school21.sockets.models.Chatroom;
import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.MessageRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessagesServiceImpl implements MessagesService {

    private MessageRepository messageRepository;

    @Autowired
    public MessagesServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }
    @Override
    public synchronized void saveMessage(Message message) {
        messageRepository.save(message);
    }

    @Override
    public User getAuthor(Message message) {
        return messageRepository.getAuthor(message.getId()).get();
    }

    @Override
    public List<Message> getLastMessagesInRoom(Chatroom chatroom) {
        return messageRepository.getLastMessagesInRoom(chatroom);
    }
}
