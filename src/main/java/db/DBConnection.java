package db;

import lombok.extern.slf4j.Slf4j;
import utils.PropertyReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
public class DBConnection {

    private final String URL;
    private final String USER_DB;
    private final String PASSWORD_DB;

    public DBConnection() {
        URL = PropertyReader.getProperty("dbUrl");
        USER_DB = System.getProperty("UserDb", PropertyReader.getProperty("dbUser"));
        PASSWORD_DB = System.getProperty("PasswordDb", PropertyReader.getProperty("dbPassword"));

        if (URL == null || USER_DB == null || PASSWORD_DB == null) {
            throw new RuntimeException(
                    "Не удалось загрузить настройки БД из config.properties. " +
                            "Проверьте наличие ключей: db.url, db.user, db.password"
            );
        }
    }

    /**
     * Создает и возвращает новое соединение с БД.
     * Закрывать соединение должен вызывающий код
     * (через try-with-resources).
     */
    public Connection connect() {
        try {
            log.info("Попытка подключения к БД: {}", URL);

            Connection connection = DriverManager.getConnection(URL, USER_DB, PASSWORD_DB);

            log.info("Соединение с БД успешно установлено.");
            return connection;

        } catch (SQLException e) {
            log.error("Не удалось установить соединение с БД. URL: {}", URL, e);
            throw new RuntimeException("Соединение с БД не выполнено", e);
        }
    }
}
