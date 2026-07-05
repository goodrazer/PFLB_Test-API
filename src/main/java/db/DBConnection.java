package db;

import lombok.extern.slf4j.Slf4j;
import utils.PropertyReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
public class DBConnection {

    private final String URL;
    private final String USER;
    private final String PASSWORD;

    public DBConnection() {
        URL = PropertyReader.getProperty("db.url");
        USER = PropertyReader.getProperty("db.user");
        PASSWORD = PropertyReader.getProperty("db.password");

        if (URL == null || USER == null || PASSWORD == null) {
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

            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

            log.info("Соединение с БД успешно установлено.");
            return connection;

        } catch (SQLException e) {
            log.error("Не удалось установить соединение с БД. URL: {}", URL, e);
            throw new RuntimeException("Соединение с БД не выполнено", e);
        }
    }
}