package edu.school21.chat.repositories;

import edu.school21.chat.exceptions.NotSavedSubEntityException;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import javax.xml.transform.Result;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        String query = STR."INSERT INTO chat.messages (author, room, text, date) VALUES (\{message.getAuthor().getId()}, \{message.getRoom().getId()}, \{message.getText()}, \{message.getDateTime()}') RETURNING id;";

        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();

            if (findUser(message.getAuthor().getId()) == null) {
                throw new NotSavedSubEntityException(STR."User with id = \{message.getAuthor().getId()} does not exist");
            }
            if (findChat(message.getRoom().getId()) == null) {
                throw new NotSavedSubEntityException(STR."Chat with id = \{message.getRoom().getId()} does not exist");
            }

            ResultSet resultSet = statement.executeQuery(query);
            if (!resultSet.next()) {
                throw new NotSavedSubEntityException("Initial error!");
            }

            message.setId(resultSet.getLong(1));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

//    @Override
//    public void update(Message message) {
//        LocalDateTime localDateTime = message.getDateTime();
//        String dateTimeQueryPart = STR."date = '\{localDateTime}' ";
//        if (dateTimeQueryPart == null) {
//            dateTimeQueryPart = dateTimeQueryPart.replace("'", "");
//        }
//
//        String newText = message.getText();
//        String textQueryPart = STR."text = '\{newText}', ";
//        if (newText == null) {
//            textQueryPart = "text = '', ";
//        }
//
//        String updateMessageQuery = STR."UPDATE chat.message SET  author = \{message.getAuthor().getId()}, room = \{message.getRoom().getId()}, \{textQueryPart}, \{dateTimeQueryPart} WHERE id = \{message.getId()};";
//
//        try (Connection connection = dataSource.getConnection()) {
//            Statement statement = connection.createStatement();
//            if (findUser(message.getAuthor().getId()) == null) {
//                throw new NotSavedSubEntityException("User with id = " + message.getAuthor().getId() + " does not exist");
//            }
//            if (findChat(message.getRoom().getId()) == null) {
//                throw new NotSavedSubEntityException("Chat with id = " + message.getRoom().getId() + " does not exist");
//            }
//            statement.execute(updateMessageQuery);
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//            System.exit(-1);
//        }
//    }

    @Override
    public void update(Message message) {
        LocalDateTime localDateTime = message.getDateTime();
        String newText = message.getText();

        Timestamp timestamp = localDateTime != null ? Timestamp.valueOf(localDateTime) : null;

        if (newText == null) {
            newText = "";
        }

        String updateMessageQuery = "UPDATE chat.messages SET author = ?, room = ?, text = ?, date = ? WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateMessageQuery)) {

            if (findUser(message.getAuthor().getId()) == null) {
                throw new NotSavedSubEntityException("User with id = " + message.getAuthor().getId() + " does not exist");
            }
            if (findChat(message.getRoom().getId()) == null) {
                throw new NotSavedSubEntityException("Chat with id = " + message.getRoom().getId() + " does not exist");
            }

            preparedStatement.setLong(1, message.getAuthor().getId());
            preparedStatement.setLong(2, message.getRoom().getId());
            preparedStatement.setString(3, newText);
            preparedStatement.setTimestamp(4, timestamp);
            preparedStatement.setLong(5, message.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }


    private User findUser(Long id) throws SQLException {
        String query = STR."SELECT * FROM chat.users WHERE id = \{id}";

        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            if (!result.next()) {
                return null;
            }
            return new User(id, result.getString(2), result.getString(3));
        }
    }

    private Chatroom findChat(Long id) throws SQLException {
        String findChatQuery = STR."SELECT * FROM chat.chatrooms WHERE id = \{id}";

        try (Connection connection = dataSource.getConnection();
             Statement st = connection.createStatement()) {
            ResultSet resultSet = st.executeQuery(findChatQuery);
            if (!resultSet.next()) {
                return null;
            }
            return new Chatroom(id, resultSet.getString(2));
        }
    }
}
