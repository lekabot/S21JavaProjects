package edu.school21.sockets.services;

import edu.school21.sockets.exceptions.NotRegisteredException;
import edu.school21.sockets.exceptions.WrongPasswordException;
import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.UserRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UsersServicesImpl implements UsersServices {
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UsersServicesImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean signUp(String login, String password) {
        if (userRepository.findByLogin(login).isPresent()) {
            throw new RuntimeException("This user is exist");
        } else {
            val encodedPassword = passwordEncoder.encode(password);
            userRepository.create(new User(login, encodedPassword, false));
        }
        return true;
    }

    @Override
    public User signIn(String login, String password) {
        Optional<User> user = userRepository.findByLogin(login);
        if (user.isEmpty()) {
            throw new NotRegisteredException("No such user, pls sign up");
        }
        if (user.map(user1 -> passwordEncoder.matches(password, user1.getPassword())).orElse(false)) {
            user.get().setAuthorized(true);
            userRepository.update(user.get());
            return user.get();
        } else {
            throw new WrongPasswordException("Wrong password!");
        }
    }

    @Override
    public void logout(User user) {
        user.setAuthorized(false);
        userRepository.update(user);
    }
}
