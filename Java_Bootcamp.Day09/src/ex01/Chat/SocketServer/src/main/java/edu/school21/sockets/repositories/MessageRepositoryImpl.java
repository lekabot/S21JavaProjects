package edu.school21.sockets.repositories;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import lombok.val;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MessageRepositoryImpl implements MessageRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;

    public MessageRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate, UserRepository userRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
    }

    @Override
    public void create(Message o) {
        String query = "INSERT INTO messages (author_id, text) VALUES (:author_id, :text)";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("author_id", o.getAuthor().getId());
        parameterSource.addValue("text", o.getText());

        jdbcTemplate.update(query, parameterSource);
    }

    @Override
    public Optional<Message> findById(Long id) {
        val query = "SELECT * FROM messages WHERE id = :id";
        val parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", id);

        List<Message> messages = jdbcTemplate.query(query, parameterSource, (rs, rowNum) -> {
            Message message = new Message();
            message.setId(rs.getLong("id"));
            message.setAuthor(userRepository.findById(rs.getLong("author_id")).orElse(null)); // Обработка отсутствующего пользователя
            message.setText(rs.getString("text"));
            message.setTime(rs.getTimestamp("time"));
            return message;
        });
        return messages.stream().findFirst();
    }

    @Override
    public List<Message> findAll() {
        String query = "SELECT * FROM messages";

        return jdbcTemplate.query(query, (rs, rowNum) -> {
            Message message = new Message();
            message.setId(rs.getLong("id"));
            message.setAuthor(userRepository.findById(rs.getLong("author_id")).orElse(null));
            message.setText(rs.getString("text"));
            message.setTime(rs.getTimestamp("time"));
            return message;
        });
    }

    @Override
    public void update(Message o) {
        val query = "UPDATE messages SET author_id = :author_id, text = :text, date = :date WHERE id = :id";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("author_id", o.getAuthor().getId());
        parameterSource.addValue("text", o.getText());
        parameterSource.addValue("id", o.getId());
        parameterSource.addValue("date", o.getTime());

        jdbcTemplate.update(query, parameterSource);
    }

    @Override
    public void delete(Long id) {
        val query = "DELETE FROM messages WHERE id = :id";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", id);

        jdbcTemplate.update(query, parameterSource);
    }

    @Override
    public User getAuthor(Message message) {
        return message.getAuthor();
    }
}
