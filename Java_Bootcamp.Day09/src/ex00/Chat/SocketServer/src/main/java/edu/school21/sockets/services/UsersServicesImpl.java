package edu.school21.sockets.services;

import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.UserRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UsersServicesImpl implements UsersServices {
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UsersServicesImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String signUp(String login, String password) {
        if (userRepository.findByLogin(login).isPresent()) {
            throw new RuntimeException("This user is exist");
        } else {
            val encodedPassword = passwordEncoder.encode(password);
            userRepository.create(new User(login, encodedPassword));
        }
        return "Successful!";
    }
}
