package edu.school21.service.service;

import edu.school21.service.config.TestApplicationConfig;
import edu.school21.service.repositories.UsersRepository;
import edu.school21.service.services.UsersService;
import edu.school21.service.services.UsersServiceImpl;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Component
public class UsersServiceImplTest {
    AnnotationConfigApplicationContext context;
    UsersRepository usersRepository;
    UsersService usersService;
    @Test
    void signUpTest_userNotSigned_success() {
        this.context =
                new AnnotationConfigApplicationContext(TestApplicationConfig.class);
        this.usersRepository = context.getBean(
                "usersRepositoryJdbc", UsersRepository.class);
        this.usersService = context.getBean(
                "usersServiceImpl", UsersServiceImpl.class);

        String result = this.usersService.signUp("test@example.com");
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }
}