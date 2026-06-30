package tests.ui.users;

import io.qameta.allure.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import tests.ui.BaseTest;
import ui.dto.users.User;

@Epic("Users")
@Feature("Создание пользователя")
@Owner("Zvezdina Aleksandra")
public class CreateUserTest extends BaseTest {

    private static final String SUCCESS_STATUS =
            "Status: Successfully pushed, code: 201";

    private static final String INVALID_STATUS =
            "Status: Invalid request data";

    //Позитивные данные для проверки успешного создания пользователя.
    @DataProvider(name = "validUserData")
    public Object[][] validUserData() {
        return new Object[][]{
                {"Ирина", "Иринина", 34, "MALE", 1000},
                {"Petr", "Petrov", 34, "MALE", 1000},
                {"Anna", "Smirnova", 130, "FEMALE", 0.0},
                {"John", "Doe", 25, "MALE", 999999.99}
        };
    }
    //Негативные данные для проверки валидации полей формы создания пользователя.
    @DataProvider(name = "invalidUserData")
    public Object[][] invalidUserData() {
        return new Object[][]{
                {"", "Petrov", 34, "MALE", 123.0, INVALID_STATUS},
                {" ", "Petrov", 34, "MALE", 123.0, INVALID_STATUS},
                {"!@#$%", "Petrov", 34, "MALE", 123.0, INVALID_STATUS},

                {"Petr", "", 34, "MALE", 123.0, INVALID_STATUS},
                {"Petr", " ", 34, "MALE", 123.0, INVALID_STATUS},
                {"Petr", "!@#$%", 34, "MALE", 123.0, INVALID_STATUS},

                {"Petr", "Petrov", 0, "MALE", 123.0, INVALID_STATUS},
                {"Petr", "Petrov", 200, "MALE", 123.0, INVALID_STATUS},
                {"Petr", "Petrov", -5, "MALE", 123.0, INVALID_STATUS},

                {"Petr", "Petrov", 34, null, 123.0, INVALID_STATUS},

                {"Petr", "Petrov", 34, "MALE", -1.0, INVALID_STATUS},
                {"Petr", "Petrov", 34, "MALE", -0.01, INVALID_STATUS}
        };
    }
    //Метод для создания объекта User.
    private User buildUser(String firstName, String lastName, int age, String sex, double money) {
        return User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .age(age)
                .sex(sex)
                .money(money)
                .build();
    }
    /*
     Позитивный тест:
     Проверяет успешное создание пользователя и его появление в системе.
     Шаги:
     1. Авторизация пользователя
     2. Открытие формы создания пользователя
     3. Заполнение формы
     4. Отправка запроса на создание
     5. Проверка статуса ответа
     6. Проверка генерации ID
     7. Проверка наличия пользователя в списке
     */
    @Test(
            testName = "АТ.02/3.1. Создание пользователя с валидными данными",
            description = "Проверка успешного создания пользователя и его отображения в системе",
            groups = {"Positive", "Regression", "Smoke"},
            dataProvider = "validUserData"
    )
    @Description("Позитивный сценарий создания пользователя")
    @Story("Создание пользователя с валидными данными")
    @Severity(SeverityLevel.BLOCKER)
    public void createValidUserTest(
            String firstName,
            String lastName,
            int age,
            String sex,
            double money
    ) {
        // Шаг 1: Авторизация в системе
        loginStep.successfulAuthorization(validEmail, validPassword);
        // Шаг 2: Переход на страницу создания пользователя
        usersCreateNewPage.openPage();
        // Шаг 3: Подготовка тестовых данных пользователя
        User user = buildUser(firstName, lastName, age, sex, money);
        // Шаг 4: Создание пользователя через UI
        usersCreateNewPage
                .addNewUser(user)
                .clickButtonPushToApi();
        SoftAssert softAssert = new SoftAssert();
        // Шаг 5: Проверка статуса ответа системы
        String actualStatus =
                usersCreateNewPage.getTheTextOfTheConservationStatusOfANewProperty();
        softAssert.assertEquals(
                actualStatus,
                SUCCESS_STATUS,
                "Статус создания пользователя некорректный"
        );
        // Шаг 6: Проверка генерации ID пользователя
        softAssert.assertTrue(
                usersCreateNewPage.isNewUserIdGenerated(),
                "ID пользователя не был сгенерирован"
        );
        String actualIdText = usersCreateNewPage.getUserIdText();
        String userId = actualIdText.replaceAll("\\D+", "");
        // Шаг 7: Проверка наличия пользователя в списке всех пользователей
        usersReadAllPage.openPage();
        softAssert.assertTrue(
                usersReadAllPage.isUserPresentById(userId),
                "Пользователь не найден в системе"
        );
        softAssert.assertAll();
    }
    /*
     Негативный тест:
     Проверяет, что система корректно обрабатывает невалидные данные
     и не создаёт пользователя при ошибках валидации.
     */
    @Test(
            testName = "АТ.02/3.2. Валидация полей при создании пользователя",
            description = "Проверка обработки невалидных данных при создании пользователя",
            groups = {"Negative", "Validation"},
            dataProvider = "invalidUserData"
    )
    public void testInvalidUserData(
            String firstName,
            String lastName,
            int age,
            String sex,
            double money,
            String expectedStatus
    ) {
        // Шаг 1: Авторизация в системе
        loginStep.successfulAuthorization(validEmail, validPassword);
        // Шаг 2: Открытие формы создания пользователя
        usersCreateNewPage.openPage();
        // Шаг 3: Сохранение текущего состояния (ID до операции)
        String oldId = usersCreateNewPage.getUserIdTextSafely();
        // Шаг 4: Попытка создания пользователя с невалидными данными
        User user = buildUser(firstName, lastName, age, sex, money);
        usersCreateNewPage
                .addNewUser(user)
                .clickButtonPushToApi();
        SoftAssert softAssert = new SoftAssert();
        // Шаг 5: Проверка статуса ошибки
        String actualStatus =
                usersCreateNewPage.getTheTextOfTheConservationStatusOfANewProperty();
        // Шаг 6: Проверка, что пользователь не был создан
        String newId = usersCreateNewPage.getUserIdTextSafely();
        softAssert.assertEquals(
                actualStatus,
                expectedStatus,
                "Статус не соответствует ожидаемому"
        );
        softAssert.assertEquals(
                newId,
                oldId,
                "Пользователь не должен создаваться при ошибке"
        );
        softAssert.assertAll();
    }
}