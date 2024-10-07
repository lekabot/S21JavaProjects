package edu.school21.sockets.repositories;

import edu.school21.sockets.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UsersRepositoryImpl implements UserRepository {

    private NamedParameterJdbcTemplate jdbcTemplate;

    public UsersRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public void create(User o) {
        String query = "INSERT INTO users (login, password) VALUES (:login, :password)";
        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("login", o.getLogin());
        params.addValue("password", o.getPassword());

        jdbcTemplate.update(query, params);
    }

    @Override
    public Optional<User> findById(Long id) {
        String query = "SELECT * FROM users WHERE id = :id";
        MapSqlParameterSource parameters = new MapSqlParameterSource();

        parameters.addValue("id", id);

        return getUserFromQueryList(query, parameters);
    }

    @Override
    public List<User> findAll() {
        String query = "SELECT * FROM users";

        return jdbcTemplate.query(query, (rs, rowNum) -> {
           User user = new User();
           user.setId(rs.getLong("id"));
           user.setLogin(rs.getString("login"));
           user.setPassword(rs.getString("password"));
           return user;
        });
    }

    @Override
    public void update(User o) {
        String query = "UPDATE users SET login = :login, password = :password WHERE id = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("login", o.getLogin());
        parameters.addValue("password", o.getPassword());
        parameters.addValue("id", o.getId());

        jdbcTemplate.update(query, parameters);
    }

    @Override
    public void delete(Long id) {
        String query = "DELETE FROM users WHERE id = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();

        parameters.addValue("id", id);

        jdbcTemplate.update(query, parameters);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        String query = "SELECT * FROM users WHERE login = :login";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("login", login);

        return getUserFromQueryList(query, parameterSource);
    }

    private Optional<User> getUserFromQueryList(String query, MapSqlParameterSource parameterSource) {
        List<User> users = jdbcTemplate.query(query, parameterSource, (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setLogin(rs.getString("login"));
            user.setPassword(rs.getString("password"));
            return user;
        });
        return users.stream().findFirst();
    }
}
