package edu.school21.sockets.repositories;

import edu.school21.sockets.models.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User> {
    Optional<User> findByLogin(String login);
}
