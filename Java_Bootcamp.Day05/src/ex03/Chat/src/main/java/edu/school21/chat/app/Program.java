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

        MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(dataSource);
        Optional<Message> messageOptional = messagesRepository.findById(2L);
        if (messageOptional.isPresent()) {
            Message message = messageOptional.get();
            message.setText(null);
            message.setDateTime(null);
            messagesRepository.update(message);
            System.out.println(message);
        }
    }
}
