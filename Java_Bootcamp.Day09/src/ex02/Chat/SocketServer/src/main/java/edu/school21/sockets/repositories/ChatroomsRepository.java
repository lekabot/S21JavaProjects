package edu.school21.sockets.repositories;

import edu.school21.sockets.models.Chatroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatroomsRepository extends JpaRepository<Chatroom, Long> {
    Optional<Chatroom> findByName(String name);
}
