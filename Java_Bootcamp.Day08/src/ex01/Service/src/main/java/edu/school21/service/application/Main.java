package edu.school21.service.application;

import edu.school21.service.models.User;
import edu.school21.service.repositories.UsersRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");

        User user1 = new User(1, "Boba");
        User user2 = new User(2, "Moba");

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
    }
}