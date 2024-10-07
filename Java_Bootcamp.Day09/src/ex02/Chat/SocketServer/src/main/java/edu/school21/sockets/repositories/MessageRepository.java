package edu.school21.sockets.repositories;

import edu.school21.sockets.models.Chatroom;
import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("SELECT m.author FROM Message m WHERE m.id = :messageId")
    Optional<User> getAuthor(Long messageId);

    @Query("SELECT m FROM Message m WHERE m.chatroom = :chatroom ORDER BY m.date DESC")
    List<Message> getLastMessagesInRoom(Chatroom chatroom);
}
