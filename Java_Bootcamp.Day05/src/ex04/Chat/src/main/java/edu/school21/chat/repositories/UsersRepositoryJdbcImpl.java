package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsersRepositoryJdbcImpl implements UsersRepository {

    private final DataSource dataSource;

    public UsersRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    @Override
    public List<User> findAll(int page, int size) {
        List<User> users = new ArrayList<>();
        String sql = "WITH users_paging_cte AS ( " +
                     "SELECT u.id, u.login, u.password, " +
                     "array_agg(DISTINCT (CONCAT_WS('-', c1.id, c1.name))) AS created_rooms, " +
                     "array_agg(DISTINCT (CONCAT_WS('-', c2.id, c2.name))) AS entered_rooms, " +
                     "ROW_NUMBER() OVER (ORDER BY u.id) AS row " +
                     "FROM chat.users u " +
                     "LEFT JOIN chat.chatrooms c1 ON u.id = c1.owner_id " +
                     "LEFT JOIN chat.user_chatroom cu ON u.id = cu.user_id " +
                     "LEFT JOIN chat.chatrooms c2 ON c2.id = cu.chatroom_id " +
                     "GROUP BY u.id) " +
                     "SELECT * FROM users_paging_cte " +
                     "WHERE row BETWEEN ? AND ?;";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, (page * size) + 1); // Начало диапазона
            ps.setInt(2, (page + 1) * size); // Конец диапазона

            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    User user = new User(
                            resultSet.getLong("id"),
                            resultSet.getString("login"),
                            resultSet.getString("password"));

                    Array createdRoomsArray = resultSet.getArray("created_rooms");
                    Array enteredRoomsArray = resultSet.getArray("entered_rooms");

                    user.setCreatedRooms(getRoomsFromArray(createdRoomsArray));
                    user.setSocializedRooms(getRoomsFromArray(enteredRoomsArray));

                    users.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

private List<Chatroom> getRoomsFromArray(Array roomsArray) throws SQLException {
    List<Chatroom> roomsList = new ArrayList<>();
    if (roomsArray != null) {
        // Получаем массив из базы данных
        Object array = roomsArray.getArray(); // Получаем массив как Object
        if (array instanceof String[]) { // Проверяем, является ли он массивом строк
            String[] rooms = (String[]) array; // Приводим к массиву строк

            for (String s : rooms) {
                if (!s.isEmpty()) {
                    String[] splitted = s.split("-");
                    Long roomId = Long.parseLong(splitted[0]);
                    String roomName = splitted[1];
                    roomsList.add(new Chatroom(roomId, roomName));
                }
            }
        } else {
            throw new SQLException("Expected an array of strings, but got: " + array.getClass().getName());
        }
    }
    return roomsList;
}
}
