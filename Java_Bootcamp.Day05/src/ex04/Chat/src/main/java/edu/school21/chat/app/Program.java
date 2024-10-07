package edu.school21.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.UsersRepository;
import edu.school21.chat.repositories.UsersRepositoryJdbcImpl;
import io.github.cdimascio.dotenv.Dotenv;

import javax.sql.DataSource;
import java.util.List;

public class Program {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        String jdbcUrl = dotenv.get("JDBCURL");
        String user = dotenv.get("USER");
        String password = dotenv.get("PASSWORD");

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(user);
        config.setPassword(password);

        DataSource dataSource = new HikariDataSource(config);

        UsersRepository usersRepository = new UsersRepositoryJdbcImpl(dataSource);

        int page = 2;
        int size = 2;

        List<User> users = usersRepository.findAll(page, size);

        for (User userObj : users) {
            System.out.println(userObj);
            System.out.println("Created Rooms: " + userObj.getCreatedRooms());
            System.out.println("Socialized Rooms: " + userObj.getSocializedRooms());
        }
    }
}
