package db;

import lombok.extern.slf4j.Slf4j;
import ui.dto.users.User;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
public class DBHelper {

    private final DBConnection dbConnection = new DBConnection();
    /**
     * Проверяет, существует ли пользователь с указанным ID.
     */
    public boolean isUserExists(int userId) {
        String query = "SELECT id FROM person WHERE id = " + userId;

        try (Connection connection = dbConnection.connect();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query)) {

            return rs.next();

        } catch (SQLException e) {
            log.error("Ошибка при проверке существования пользователя с ID {}", userId, e);
            throw new RuntimeException("Ошибка при выполнении запроса к БД", e);
        }
    }
    /**
     * Возвращает пользователя из БД по ID.
     */
    public User getUserById(int userId) {
        String query = "SELECT id, age, first_name, money, second_name, sex " +
                "FROM person WHERE id = " + userId;

        try (Connection connection = dbConnection.connect();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query)) {

            if (rs.next()) {
                return User.builder()
                        .firstName(rs.getString("first_name"))
                        .lastName(rs.getString("second_name"))
                        .age(rs.getInt("age"))
                        .sex(rs.getString("sex"))
                        .money(rs.getDouble("money"))
                        .build();
            }

            return null;

        } catch (SQLException e) {
            log.error("Ошибка при получении пользователя с ID {}", userId, e);
            throw new RuntimeException("Ошибка при выполнении запроса к БД", e);
        }
    }
}
