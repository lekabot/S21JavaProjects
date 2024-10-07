package edu.school21.chat.app;

import edu.school21.chat.models.Message;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.cdimascio.dotenv.Dotenv;

import javax.sql.DataSource;
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
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a message ID");
        System.out.print("-> ");
        Long messageId = scanner.nextLong();

        Optional<Message> message = messagesRepository.findById(messageId);

        if (message.isPresent()) {
            System.out.println("Message : " + message.get());
        } else {
            System.out.println("Message not found");
        }
    }
}
