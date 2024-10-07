package edu.school21.chat.app;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.cdimascio.dotenv.Dotenv;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class Program {
    public static void main() {
        Dotenv dotenv = Dotenv.load();
        String jdbcUrl = dotenv.get("JDBCURL");
        String user = dotenv.get("USER");
        String password = dotenv.get("PASSWORD");

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(user);
        config.setPassword(password);

        DataSource dataSource = new HikariDataSource(config);

        User author = new User(6L, "user", "user", new ArrayList<>(), new ArrayList<>());
        Chatroom room = new Chatroom(5L, "room", author, new ArrayList<>());
        Message message = new Message(null, author, room, "Test message!", LocalDateTime.now());
        MessagesRepository repository = new MessagesRepositoryJdbcImpl(dataSource);
        repository.save(message);
        System.out.println(message.getId());
    }
}
