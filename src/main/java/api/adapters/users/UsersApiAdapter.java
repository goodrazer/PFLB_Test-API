package api.adapters.users;

import api.models.login.LoginResponse;
import api.models.users.PersonDto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.log4j.Log4j2;
import utils.PropertyReader;

/**
 * Адаптер для работы с API модуля Users.
 */
@Log4j2
public class UsersApiAdapter {

    private static final String BASE_URL = "http://82.142.167.37:4879";
    // Эндпоинты
    private static final String LOGIN_ENDPOINT = "/login";
    private static final String USER_ENDPOINT = "/user";
    private static final String USERS_ENDPOINT = "/users";
    private static final String USER_INFO_ENDPOINT = "/user/{userId}/info";
    private static final String USER_MONEY_ENDPOINT = "/user/{userId}/money/{amount}";
    // Gson для сериализации/десериализации JSON
    private final Gson gson;
    // JWT токен для авторизации
    private String jwtToken;

    public UsersApiAdapter() {
        // настройка Gson (игнорирует null-поля при сериализации)
        this.gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        // настройка RestAssured
        RestAssured.baseURI = BASE_URL;
    }

    // Авторизация
    /**
     * Авторизация через JSON (POST /login).
     * Получает JWT токен и сохраняет его для последующих запросов.
     */
    public String loginAsJson(String email, String password) {
        log.info("Авторизация через JSON");

        String requestBody = String.format(
                "{\"username\":\"%s\",\"password\":\"%s\"}",
                email, password
        );

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(LOGIN_ENDPOINT)
                .then()
                .statusCode(202)
                .extract()
                .response();

        LoginResponse loginResponse = gson.fromJson(
                response.asString(),
                LoginResponse.class
        );

        this.jwtToken = loginResponse.getAccessToken();
        log.info("Успешная авторизация (получен токен)");
        return this.jwtToken;
    }

    /**
     * Авторизация администратора через JSON.
     * Креды берём из config.properties через PropertyReader.
     */
    public String loginAsUser() {
        String email = PropertyReader.getProperty("email");
        String password = PropertyReader.getProperty("password");
        return loginAsJson(email, password);
    }

    // Базовые методы
    /**
     * Создаёт базовую спецификацию запроса с авторизацией.
     * Если токен не установлен, используется анонимный запрос.
     */
    private RequestSpecification givenAuthenticated() {
        RequestSpecification spec = RestAssured.given()
                .contentType(ContentType.JSON);

        if (jwtToken != null && !jwtToken.isEmpty()) {
            spec.header("Authorization", "Bearer " + jwtToken);
        }

        return spec;
    }

    // CRUD операции с пользователями
    /**
     * API.02.01 - Создание пользователя (POST /user).
     * @param user DTO пользователя для создания
     * @return Ответ сервера
     */
    public Response createUser(PersonDto user) {
        log.info("Создание пользователя: {} {}", user.getFirstName(), user.getSecondName());

        return givenAuthenticated()
                .body(gson.toJson(user))
                .when()
                .post(USER_ENDPOINT)
                .then()
                .extract()
                .response();
    }

    /**
     * API.02.02 - Получение пользователя по ID (GET /user/{userId}).
     * @param userId ID пользователя
     * @return Ответ сервера
     */
    public Response getUserById(long userId) {
        log.info("Получение пользователя по ID: {}", userId);

        return givenAuthenticated()
                .when()
                .get(USER_ENDPOINT + "/" + userId)
                .then()
                .extract()
                .response();
    }

    /**
     * API.02.03 - Получение списка всех пользователей (GET /users).
     * @return Ответ сервера
     */
    public Response getAllUsers() {
        log.info("Получение списка всех пользователей");

        return givenAuthenticated()
                .when()
                .get(USERS_ENDPOINT)
                .then()
                .extract()
                .response();
    }

    /**
     * API.02.04 - Получение информации о пользователе с имуществом (GET /user/{userId}/info).
     * @param userId ID пользователя
     * @return Ответ сервера
     */
    public Response getUserInfo(long userId) {
        log.info("Получение информации о пользователе с имуществом: {}", userId);

        return givenAuthenticated()
                .when()
                .get(USER_INFO_ENDPOINT, userId)
                .then()
                .extract()
                .response();
    }

    /**
     * API.02.05 - Обновление данных пользователя (PUT /user/{userId}).
     * @param userId      ID пользователя
     * @param updatedUser Обновленные данные
     * @return Ответ сервера
     */
    public Response updateUser(long userId, PersonDto updatedUser) {
        log.info("Обновление пользователя с ID: {}", userId);

        return givenAuthenticated()
                .body(gson.toJson(updatedUser))
                .when()
                .put(USER_ENDPOINT + "/" + userId)
                .then()
                .extract()
                .response();
    }

    /**
     * API.02.06 - Удаление пользователя (DELETE /user/{userId}).
     * @param userId ID пользователя
     * @return Ответ сервера
     */
    public Response deleteUser(long userId) {
        log.info("Удаление пользователя с ID: {}", userId);

        return givenAuthenticated()
                .when()
                .delete(USER_ENDPOINT + "/" + userId)
                .then()
                .extract()
                .response();
    }

    // Финансовые операции
    /**
     * Начисление денег пользователю (POST /user/{userId}/money/{amount}).
     * @param userId ID пользователя
     * @param amount Сумма для начисления
     * @return Ответ сервера
     */
    public Response addMoney(long userId, double amount) {
        log.info("Начисление {} денег пользователю с ID: {}", amount, userId);

        return givenAuthenticated()
                .when()
                .post(USER_MONEY_ENDPOINT, userId, amount)
                .then()
                .extract()
                .response();
    }

    // Getters
    public String getJwtToken() {return jwtToken;}
}