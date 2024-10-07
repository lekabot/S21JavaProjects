package edu.school21.service.services;

import edu.school21.service.models.User;
import edu.school21.service.repositories.UsersRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class UsersServiceImpl implements UsersService  {

    private UsersRepository usersRepository;

    public UsersServiceImpl(@Qualifier("usersRepositoryJdbc") UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public String signUp(String email) {
        String password = null;
        if (usersRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("User already exist");
        } else {
            val id = usersRepository.findAll().getLast().getId() + 1L;
            password = genPassword();
            usersRepository.save(new User(id, email, password));
        }
        return password;
    }

    private String genPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+<>?";
        val random = new SecureRandom();
        val password = new StringBuilder();

        for (int i = 0; i < 12; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }
        return password.toString();
    }
}
