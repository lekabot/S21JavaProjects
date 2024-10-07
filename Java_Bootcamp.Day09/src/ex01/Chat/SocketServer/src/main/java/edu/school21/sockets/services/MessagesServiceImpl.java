package edu.school21.sockets.services;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.MessageRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MessagesServiceImpl implements MessageServices {

    private MessageRepository messageRepository;

    public MessagesServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public void saveMessage(Message message) {
        messageRepository.create(message);
    }

    @Override
    public User getAuthor(Message message) {
        return messageRepository.getAuthor(message);
    }
}
