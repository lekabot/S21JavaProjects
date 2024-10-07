package edu.school21.service.application;

import edu.school21.service.config.Config;
import edu.school21.service.models.User;
import edu.school21.service.repositories.UsersRepository;
import edu.school21.service.services.UsersServiceImpl;
import lombok.val;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        val context = new AnnotationConfigApplicationContext(Config.class);

        val user1 = new User(1L, "Boba", "123");
        val user2 = new User(2L, "Moba", "456");

        UsersRepository usersRepository = context.getBean("usersRepositoryJdbc", UsersRepository.class);
        usersRepository.save(user1);
        System.out.println(usersRepository.findAll());
        System.out.println(usersRepository.findById(1L).get());
        user1.setEmail("ajsfkasfkas");
        usersRepository.update(user1);
        System.out.println(usersRepository.findByEmail("ajsfkasfkas").get());
        usersRepository.delete(1L);

        System.out.println();

        usersRepository = context.getBean("usersRepositoryJdbcTemplate", UsersRepository.class);
        usersRepository.save(user2);
        System.out.println(usersRepository.findAll());
        System.out.println(usersRepository.findById(2L).get());
        user2.setEmail("ABOBOV");
        usersRepository.update(user2);
        System.out.println(usersRepository.findByEmail("ABOBOV").get());
        usersRepository.delete(2L);

        val user3 = new User(3L, "Coba", "789");
        val user4 = new User(4L, "Goba", "101112");
        usersRepository.save(user3);
        usersRepository.save(user4);

        System.out.println();

        val userSer = new UsersServiceImpl(usersRepository);
        userSer.signUp("Doba");
        System.out.println(usersRepository.findAll());

        usersRepository.delete(3L);
        usersRepository.delete(4L);
        usersRepository.delete(5L);
    }
}