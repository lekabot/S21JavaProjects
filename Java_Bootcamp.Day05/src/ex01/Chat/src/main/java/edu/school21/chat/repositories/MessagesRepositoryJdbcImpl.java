package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {
    private final DataSource dataSource;

    public MessagesRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Message> findById(Long id) {
        String query = "SELECT m.id, m.text, m.date, u.id as user_id, u.login, u.password,\n" +
                "    chr.id AS chat_id, chr.name\n" +
                "FROM chat.messages m\n" +
                "JOIN chat.users u ON m.author = u.id\n" +
                "JOIN chat.chatrooms chr ON m.room = chr.id\n" +
                "WHERE m.id = ?";

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User(resultSet.getLong("user_id"), resultSet.getString("login"), resultSet.getString("password"), null, null);
                    Chatroom chatroom = new Chatroom(resultSet.getLong("chat_id"), resultSet.getString("name"), null, null);
                    Message message = new Message(resultSet.getLong("id"), user, chatroom, resultSet.getString("text"), resultSet.getTimestamp("date").toLocalDateTime());

                    return Optional.of(message);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        return Optional.empty();
    }
}
