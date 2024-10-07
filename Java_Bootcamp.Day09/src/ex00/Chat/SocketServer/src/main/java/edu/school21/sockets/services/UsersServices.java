package edu.school21.sockets.services;

import org.springframework.stereotype.Component;

@Component
public interface UsersServices {
    String signUp(String login, String password);
}
