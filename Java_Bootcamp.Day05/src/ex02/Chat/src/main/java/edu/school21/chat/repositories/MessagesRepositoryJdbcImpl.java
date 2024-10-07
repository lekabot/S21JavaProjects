package edu.school21.chat.repositories;

import edu.school21.chat.exceptions.NotSavedSubEntityException;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import javax.xml.transform.Result;
import java.sql.*;
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

    @Override
    public void save(Message message) {
        String query = "INSERT INTO chat.messages (author, room, text, date) VALUES (?, ?, ?, ?) RETURNING id;";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            validateUserAndChat(message);

            preparedStatement.setLong(1, message.getAuthor().getId());
            preparedStatement.setLong(2, message.getRoom().getId());
            preparedStatement.setString(3, message.getText());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(message.getDateTime()));

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                message.setId(resultSet.getLong(1));
            } else {
                throw new NotSavedSubEntityException("Failed to save message: no ID returned.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error while saving message: " + e.getMessage(), e);
        }
    }

    private void validateUserAndChat(Message message) throws SQLException {
        if (findUser(message.getAuthor().getId()) == null) {
            throw new NotSavedSubEntityException("User with id = " + message.getAuthor().getId() + " does not exist");
        }
        if (findChat(message.getRoom().getId()) == null) {
            throw new NotSavedSubEntityException("Chat with id = " + message.getRoom().getId() + " does not exist");
        }
    }

    private User findUser(Long id) throws SQLException {
        String query = "SELECT * FROM chat.users WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, id);
            ResultSet result = preparedStatement.executeQuery();

            if (!result.next()) {
                return null;
            }

            return new User(result.getLong("id"), result.getString("login"), result.getString("password"));
        }
    }

    private Chatroom findChat(Long id) throws SQLException {
        String findChatQuery = "SELECT * FROM chat.chatrooms WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(findChatQuery)) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }

            return new Chatroom(resultSet.getLong("id"), resultSet.getString("name"));
        }
    }
}
