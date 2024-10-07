package edu.school21.sockets.services;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;

import java.util.Optional;

public interface MessageServices {
    void saveMessage(Message message);
    User getAuthor(Message message);
}
