package edu.school21.sockets.repositories;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;

public interface MessageRepository extends CrudRepository<Message> {
    User getAuthor(Message message);
}
