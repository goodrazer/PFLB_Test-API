package tests.api.users;

import api.adapters.users.UsersApiAdapter;
import api.models.users.PersonDto;
import io.qameta.allure.*;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.PropertyReader;
import java.util.ArrayList;
import java.util.List;

/**
 * API-тесты для модуля Users.
 * Покрывает CRUD-операции с пользователями через REST API.
 */
@Log4j2
@Epic("API Tests")
@Feature("Users API")
@Owner("Oleg P.")
@Link(value = "docs.google", name = "Чек-лист PFLB")
public class UsersApiTest {
    // Адаптер для работы с Users API
    private UsersApiAdapter usersApi;
    // Список ID созданных пользователей (для очистки после теста)
    private List<Long> createdUserIds;
    // Креды из config.properties
    private final String validEmail = PropertyReader.getProperty("email");
    private final String validPassword = PropertyReader.getProperty("password");

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        log.info("Инициализация User API-тестов");
        // Создаём адаптер
        usersApi = new UsersApiAdapter();
        // Авторизуемся (полный доступ)
        usersApi.loginAsJson(validEmail, validPassword);
        // Создаём список для отслеживания созданных пользователей
        createdUserIds = new ArrayList<>();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        log.info("Очистка после User API-тестов");
        // Удаляем всех созданных в тесте пользователей
        if (createdUserIds != null && !createdUserIds.isEmpty()) {
            for (Long userId : createdUserIds) {
                try {
                    log.info("Удаление тестового пользователя с ID: {}", userId);
                    usersApi.deleteUser(userId);
                } catch (Exception e) {
                    log.warn("Не удалось удалить пользователя {}: {}", userId, e.getMessage());
                }
            }
        }
    }

    // Доп методы для создания тестовых пользователей
    // + авто добавление ID в список для последующей очистки
    /**
     * Создаёт тестового пользователя с дефолтными значениями.
     * @return ID созданного пользователя
     */
    private long createTestUser() {
        //вызывает второй метод с дефолтными параметрами
        return createTestUser(
                "AQA_API_User",
                "Testov",
                35,
                "MALE",
                100000.00
        );
    }

    /**
     * Создаёт тестового пользователя с кастомными значениями.
     * @return ID созданного пользователя
     */
    private long createTestUser(String firstName, String secondName,
                                int age, String sex, double money) {
        // Создаём DTO пользователя
        PersonDto user = PersonDto.builder()
                .firstName(firstName)
                .secondName(secondName)
                .age(age)
                .sex(sex)
                .money(money)
                .build();
        // Вызываем API через адаптер (POST /user)
        Response response = usersApi.createUser(user);
        // Извлекаем ID из ответа
        long userId = response.jsonPath().getLong("id");
        // Сохраняем ID в список для последующей очистки
        createdUserIds.add(userId);
        return userId;
    }

    // Позитивные тесты
    @Test(testName = "API.02.01.Создание пользователя с валидными данными",
            description = "Проверка создания пользователя через POST /user с валидными данными",
            priority = 1,
            groups = {"Positive", "Smoke", "API"},
            enabled = true)
    @Description("Создание пользователя с валидными данными через POST /user")
    @Story("CRUD операции с пользователями")
    @Severity(SeverityLevel.CRITICAL)
    @TmsLink("API.02.01")
    @Issue("BugLink")
    public void testCreateUserWithValidData() {
        log.info("API.02.01 - Создание пользователя с валидными данными");
        SoftAssert softAssert = new SoftAssert();

        // Подготовка тестовых данных
        PersonDto user = PersonDto.builder()
                .firstName("Oleg")
                .secondName("P")
                .age(35)
                .sex("MALE")
                .money(50000.00)
                .build();
        // Выполнение запроса
        Response response = usersApi.createUser(user);
        // Проверки
        softAssert.assertEquals(response.statusCode(), 201, "Статус должен быть 201 Created");
        softAssert.assertNotNull(response.jsonPath().getLong("id"), "ID должен быть присвоен");
        softAssert.assertEquals(response.jsonPath().getString("firstName"), "Oleg", "Имя должно совпадать");
        softAssert.assertEquals(response.jsonPath().getString("secondName"), "P", "Фамилия должна совпадать");
        softAssert.assertEquals(response.jsonPath().getInt("age"), 35, "Возраст должен совпадать");
        softAssert.assertEquals(response.jsonPath().getString("sex"), "MALE", "Пол должен совпадать");
        // Добавляем ID в список для последующей очистки
        createdUserIds.add(response.jsonPath().getLong("id"));
        log.info("API.02.01 пройден. Создан пользователь с ID: {}", response.jsonPath().getLong("id"));

        softAssert.assertAll();
    }

    @Test(testName = "API.02.02.Получение пользователя по ID",
            description = "Проверка получения пользователя через GET /user/{userId}",
            priority = 2,
            groups = {"Positive", "API"},
            enabled = true)
    @Description("Получение пользователя по ID через GET /user/{userId}")
    @Story("CRUD операции с пользователями")
    @Severity(SeverityLevel.CRITICAL)
    @TmsLink("API.02.02")
    @Issue("BugLink")
    public void testGetUserById() {
        log.info("API.02.02 - Получение пользователя по ID");
        SoftAssert softAssert = new SoftAssert();

        // Создаём тестового пользователя
        long userId = createTestUser();
        // Получаем пользователя по ID
        Response response = usersApi.getUserById(userId);
        // Проверки
        softAssert.assertEquals(response.statusCode(), 200, "Статус должен быть 200 OK");
        softAssert.assertEquals(response.jsonPath().getLong("id"), userId, "ID должен совпадать");
        softAssert.assertNotNull(response.jsonPath().getString("firstName"), "Имя не должно быть null");
        log.info("API.02.02 пройден");

        softAssert.assertAll();
    }

    @Test(testName = "API.02.03.Получение списка всех пользователей",
            description = "Проверка получения списка пользователей через GET /users",
            priority = 3,
            groups = {"Positive", "API"},
            enabled = true)
    @Description("Получение списка всех пользователей через GET /users")
    @Story("CRUD операции с пользователями")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("API.02.03")
    @Issue("BugLink")
    public void testGetAllUsers() {
        log.info("API.02.03 - Получение списка всех пользователей");
        SoftAssert softAssert = new SoftAssert();

        // Создаём нескольких тестовых пользователей
        createTestUser("User1", "Test1", 25, "MALE", 10000);
        createTestUser("User2", "Test2", 28, "FEMALE", 20000);
        // Получаем список всех пользователей
        Response response = usersApi.getAllUsers();
        // Проверки
        softAssert.assertEquals(response.statusCode(), 200, "Статус должен быть 200 OK");
        int usersCount = response.jsonPath().getList("$").size();
        softAssert.assertTrue(usersCount >= 2, "В списке должно быть минимум 2 пользователя");
        log.info("API.02.03 пройден. Всего пользователей: {}", usersCount);

        softAssert.assertAll();
    }

    @Test(testName = "API.02.04.Получение информации о пользователе с имуществом",
            description = "Проверка получения информации о пользователе с имуществом через GET /user/{userId}/info",
            priority = 4,
            groups = {"Positive", "API"},
            enabled = true)
    @Description("Получение информации о пользователе с имуществом")
    @Story("CRUD операции с пользователями")
    @Severity(SeverityLevel.CRITICAL)
    @TmsLink("API.02.04")
    @Issue("BugLink")
    public void testGetUserInfoWithHouseAndCars() {
        log.info("API.02.04 - Получение информации о пользователе с имуществом");
        SoftAssert softAssert = new SoftAssert();

        // Создаём тестового пользователя
        long userId = createTestUser();
        // Получаем информацию о пользователе с имуществом
        Response response = usersApi.getUserInfo(userId);
        // Проверки
        softAssert.assertEquals(response.statusCode(), 200, "Статус должен быть 200 OK");
        softAssert.assertEquals(response.jsonPath().getLong("id"), userId, "ID должен совпадать");
        // Проверяем наличие полей house и cars в ответе
        // house может быть null (пользователь не заселен), но поле должно существовать
        softAssert.assertTrue(response.jsonPath().getMap("$").containsKey("house"),
                "Поле 'house' должно присутствовать в ответе");
        // cars должен быть массивом (может быть пустым)
        softAssert.assertNotNull(response.jsonPath().get("cars"),
                "Поле 'cars' должно присутствовать в ответе");
        softAssert.assertTrue(response.jsonPath().getList("cars").size() >= 0,
                "Поле 'cars' должно быть массивом");
        log.info("API.02.04 пройден");

        softAssert.assertAll();
    }

    @Test(testName = "API.02.05.Обновление данных пользователя",
            description = "Проверка обновления пользователя через PUT /user/{userId}",
            priority = 5,
            groups = {"Positive", "API"},
            enabled = true)
    @Description("Обновление данных пользователя через PUT /user/{userId}")
    @Story("CRUD операции с пользователями")
    @Severity(SeverityLevel.CRITICAL)
    @TmsLink("API.02.05")
    @Issue("BugLink")
    public void testUpdateUser() {
        log.info("API.02.05 - Обновление данных пользователя");
        SoftAssert softAssert = new SoftAssert();

        // Создаём тестового пользователя
        long userId = createTestUser();
        // Подготавливаем обновленные данные
        PersonDto updatedData = PersonDto.builder()
                .firstName("Hacker")
                .secondName("Man")
                .age(50)
                .sex("FEMALE")
                .money(75000.00)
                .build();
        // Выполняем обновление
        Response response = usersApi.updateUser(userId, updatedData);
        // Проверки
        softAssert.assertTrue(response.statusCode() == 202, "Статус должен быть 202 Accepted");
        softAssert.assertEquals(response.jsonPath().getString("firstName"), "Hacker", "Имя должно обновиться");
        softAssert.assertEquals(response.jsonPath().getString("secondName"), "Man", "Фамилия должна обновиться");
        softAssert.assertEquals(response.jsonPath().getInt("age"), 50, "Возраст должен обновиться");
        log.info("API.02.05 пройден");

        softAssert.assertAll();
    }

    @Test(testName = "API.02.06.Удаление пользователя",
            description = "Проверка удаления пользователя через DELETE /user/{userId}",
            priority = 6,
            groups = {"Positive", "API"},
            enabled = true)
    @Description("Удаление пользователя через DELETE /user/{userId}")
    @Story("CRUD операции с пользователями")
    @Severity(SeverityLevel.CRITICAL)
    @TmsLink("API.02.06")
    @Issue("BugLink")
    public void testDeleteUser() {
        log.info("API.02.06 - Удаление пользователя");
        SoftAssert softAssert = new SoftAssert();

        // Создаём отдельного пользователя для удаления (не добавляем в createdUserIds)
        PersonDto user = PersonDto.builder()
                .firstName("Delete")
                .secondName("Me")
                .age(40)
                .sex("MALE")
                .money(1000.00)
                .build();
        Response createResponse = usersApi.createUser(user);
        long userId = createResponse.jsonPath().getLong("id");
        // Удаляем пользователя
        Response deleteResponse = usersApi.deleteUser(userId);
        // Проверки
        softAssert.assertTrue(deleteResponse.statusCode() == 204,
                "Статус должен быть 204 No Content"
        );
        // Проверяем, что пользователь удалён
        Response getResponse = usersApi.getUserById(userId);
        softAssert.assertTrue(getResponse.statusCode() == 204,"Пользователь должен быть удалён");
        log.info("API.02.06 пройден");

        softAssert.assertAll();
    }

    //Негативные тесты
    @Test(testName = "API.02.07.Создание пользователя с невалидными данными",
            description = "Проверка создания пользователя с невалидными данными",
            priority = 7,
            groups = {"Negative", "API"},
            enabled = true)
    @Description("Создание пользователя с невалидными данными")
    @Story("Негативные сценарии Users API")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("API.02.07")
    @Issue("BugLink")
    public void testCreateUserWithInvalidData() {
        log.info("API.02.07 - Создание пользователя с невалидными данными");
        SoftAssert softAssert = new SoftAssert();

        // Пользователь с пустыми обязательными полями
        PersonDto invalidUser = PersonDto.builder()
                .firstName("")
                .secondName("")
                .age(-5)
                .sex("INVALID")
                .money(-1000.00)
                .build();
        // Выполняем запрос
        Response response = usersApi.createUser(invalidUser);
        // Проверки - ожидаем ошибку
        softAssert.assertTrue(
                response.statusCode() == 400,
                "Ожидался статус ошибки (400), но получен: " + response.statusCode()
        );
        log.info("API.02.07 пройден. Получен статус: {}", response.statusCode());

        softAssert.assertAll();
    }

    @Test(testName = "API.02.08.Получение несуществующего пользователя",
            description = "Проверка получения несуществующего пользователя",
            priority = 8,
            groups = {"Negative", "API"},
            enabled = true)
    @Description("Получение несуществующего пользователя")
    @Story("Негативные сценарии Users API")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("API.02.08")
    @Issue("BugLink")
    public void testGetNonExistentUser() {
        log.info("API.02.08 - Получение несуществующего пользователя");
        SoftAssert softAssert = new SoftAssert();

        // Пытаемся получить пользователя с несуществующим ID
        long nonExistentId = 999999;
        Response response = usersApi.getUserById(nonExistentId);
        // Проверки - ожидаем 204 (No Content) или 404 (Not Found)
        softAssert.assertTrue(response.statusCode() == 204,
                "Статус должен быть 204 No Content"
        );
        log.info("API.02.08 пройден. Статус: {}", response.statusCode());

        softAssert.assertAll();
    }

    @Test(testName = "API.02.09.Обновление несуществующего пользователя",
            description = "Проверка обновления несуществующего пользователя",
            priority = 9,
            groups = {"Negative", "API"},
            enabled = true)
    @Description("Обновление несуществующего пользователя")
    @Story("Негативные сценарии Users API")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("API.02.09")
    @Issue("BugLink")
    public void testUpdateNonExistentUser() {
        log.info("API.02.09 - Обновление несуществующего пользователя");
        SoftAssert softAssert = new SoftAssert();

        long nonExistentId = 999999;
        PersonDto updatedData = PersonDto.builder()
                .firstName("Nonexistent")
                .secondName("User")
                .age(25)
                .sex("MALE")
                .money(1000.00)
                .build();
        // Выполняем запрос
        Response response = usersApi.updateUser(nonExistentId, updatedData);
        // Проверки - ожидаем ошибку
        softAssert.assertTrue(
                response.statusCode() == 404,
                "Ожидался статус ошибки (404), но получен: " + response.statusCode()
        );
        log.info("API.02.09 пройден. Статус: {}", response.statusCode());

        softAssert.assertAll();
    }

    @Test(testName = "API.02.10.Удаление несуществующего пользователя",
            description = "Проверка удаления несуществующего пользователя",
            priority = 10,
            groups = {"Negative", "API"},
            enabled = true)
    @Description("Удаление несуществующего пользователя")
    @Story("Негативные сценарии Users API")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("API.02.10")
    @Issue("BugLink")
    public void testDeleteNonExistentUser() {
        log.info("API.02.10 - Удаление несуществующего пользователя");
        SoftAssert softAssert = new SoftAssert();

        long nonExistentId = 999999;
        Response response = usersApi.deleteUser(nonExistentId);
        // Проверки
        softAssert.assertTrue(response.statusCode() == 404, "Статус должен быть 404 Not Found");
        log.info("API.02.10 пройден. Статус: {}", response.statusCode());

        softAssert.assertAll();
    }

    @Test(testName = "API.02.11.Создание пользователя без обязательных полей",
            description = "Проверка создания пользователя без обязательных полей",
            priority = 11,
            groups = {"Negative", "API"},
            enabled = true)
    @Description("Создание пользователя без обязательных полей")
    @Story("Негативные сценарии Users API")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("API.02.11")
    @Issue("BugLink")
    public void testCreateUserWithoutRequiredFields() {
        log.info("API.02.11 - Создание пользователя без обязательных полей");
        SoftAssert softAssert = new SoftAssert();

        // Пустой объект
        PersonDto emptyUser = PersonDto.builder().build();
        Response response = usersApi.createUser(emptyUser);
        // Проверки - ожидаем ошибку
        softAssert.assertTrue(response.statusCode() == 400,
                "Ожидался статус ошибки (400), но получен: " + response.statusCode());
        log.info("API.02.11 пройден. Статус: {}", response.statusCode());

        softAssert.assertAll();
    }

    @Test(testName = "API.02.12.Создание пользователя с невалидным возрастом",
            description = "Проверка создания пользователя с невалидным возрастом",
            priority = 12,
            groups = {"Negative", "API"},
            enabled = true)
    @Description("Создание пользователя с невалидным возрастом")
    @Story("Негативные сценарии Users API")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("API.02.12")
    @Issue("BugLink")
    public void testCreateUserWithInvalidAge() {
        log.info("API.02.12 - Создание пользователя с невалидным возрастом");
        SoftAssert softAssert = new SoftAssert();

        // Отрицательный возраст
        log.info("Создание пользователя с отрицательным возрастом");
        PersonDto userWithNegativeAge = PersonDto.builder()
                .firstName("Test")
                .secondName("User")
                .age(-1)
                .sex("MALE")
                .money(1000.00)
                .build();
        Response response1 = usersApi.createUser(userWithNegativeAge);
        softAssert.assertTrue(response1.statusCode() == 400,
                "Ожидался статус 400 для отрицательного возраста, но получен: " + response1.statusCode());
        //Нулевой возраст
        log.info("Создание пользователя с нулевым возрастом");
        PersonDto userWithZeroAge = PersonDto.builder()
                .firstName("Test")
                .secondName("User")
                .age(0)
                .sex("MALE")
                .money(1000.00)
                .build();
        Response response2 = usersApi.createUser(userWithZeroAge);
        softAssert.assertTrue(
                response2.statusCode() == 400,
                "Ожидался статус 400 для нулевого возраста, но получен: " + response2.statusCode()
        );
        log.info("API.02.12 пройден");

        softAssert.assertAll();
    }

    @Test(testName = "API.02.13.Создание пользователя с отрицательным балансом",
            description = "Проверка создания пользователя с отрицательным балансом",
            priority = 13,
            groups = {"Negative", "API"},
            enabled = true)
    @Description("Создание пользователя с отрицательным балансом")
    @Story("Негативные сценарии Users API")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("API.02.13")
    @Issue("BugLink")
    public void testCreateUserWithNegativeBalance() {
        log.info("API.02.13 - Создание пользователя с отрицательным балансом");
        SoftAssert softAssert = new SoftAssert();

        PersonDto userWithNegativeBalance = PersonDto.builder()
                .firstName("Test")
                .secondName("User")
                .age(25)
                .sex("MALE")
                .money(-5000.00)
                .build();
        // Выполняем запрос
        Response response = usersApi.createUser(userWithNegativeBalance);
        // Проверки - ожидаем ошибку
        softAssert.assertTrue(
                response.statusCode() == 400,
                "Ожидался статус ошибки (400), но получен: " + response.statusCode()
        );
        log.info("API.02.13 пройден. Статус: {}", response.statusCode());

        softAssert.assertAll();
    }
}