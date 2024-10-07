package edu.school21.sockets.services;

import edu.school21.sockets.models.User;
import org.springframework.stereotype.Component;

@Component
public interface UsersServices {
    boolean signUp(String login, String password);
    User signIn(String login, String password);
    void logout(User user);
}
