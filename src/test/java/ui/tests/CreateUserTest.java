package ui.tests;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ui.dto.User;

public class CreateUserTest extends BaseTest {
    @Test(
            testName = "АТ.02/3.1. Создание пользователя с валидными данными (кириллица)",
            description = "Система должна успешно создавать пользователя при заполнении всех обязательных полей валидными данными на кириллице",
            priority = 1,
            groups = {"Positive", "Regression", "Smoke"}
    )
    @Description("Позитивный сценарий создания пользователя с полным набором валидных данных (кириллица)")
    @Epic("Users")
    @Feature("Создание пользователя")
    @Story("Проверка создания пользователя с заполнением всех обязательных полей валидными кредами (кириллица)")
    @Severity(SeverityLevel.BLOCKER)
    @Owner("Zvezdina Aleksandra")
    public void createValidUserCyrillicTest() {
        loginStep.successfulAuthorization(validEmail, validPassword);
        usersCreateNewPage.openPage();

        User user = User.builder()
                .firstName("Петров")
                .lastName("Петр")
                .age(34)
                .sex("MALE")
                .money(123)
                .build();

        usersCreateNewPage.addNewUser(user).clickButtonPushToApi();

        SoftAssert softAssert = new SoftAssert();

        String actualStatus = usersCreateNewPage.getTheTextOfTheConservationStatusOfANewProperty();
        softAssert.assertEquals(actualStatus, "Status: Successfully pushed, code: 201",
                "Статус не совпадает!");

        softAssert.assertTrue(usersCreateNewPage.isVisibleNewUserID(),
                "ID пользователя не отображён!");

        String actualIdText = usersCreateNewPage.getUserIdText();
        softAssert.assertTrue(actualIdText.matches("New user ID: \\d+"),
                "ID имеет неверный формат: " + actualIdText);

        String userId = actualIdText.replaceAll("\\D+", "");
        usersReadAllPage.openPage();
        softAssert.assertTrue(usersReadAllPage.isUserPresentById(userId),
                "Пользователь с ID " + userId + " не найден в списке!");

        softAssert.assertAll();
    }

    @Test(
            testName = "АТ.02/3.12. Создание пользователя с валидными данными (латиница)",
            description = "Система должна успешно создавать пользователя при заполнении всех обязательных полей валидными данными на латинице",
            priority = 1,
            groups = {"Positive", "Regression", "Smoke"}
    )
    @Description("Позитивный сценарий создания пользователя с полным набором валидных данных (латиница)")
    @Epic("Users")
    @Feature("Создание пользователя")
    @Story("Проверка создания пользователя с заполнением всех обязательных полей валидными кредами (латиница)")
    @Severity(SeverityLevel.BLOCKER)
    @Owner("Zvezdina Aleksandra")
    public void createValidUserLatinTest() {
        loginStep.successfulAuthorization(validEmail, validPassword);
        usersCreateNewPage.openPage();

        User user = User.builder()
                .firstName("Petrov")
                .lastName("Petr")
                .age(34)
                .sex("MALE")
                .money(123)
                .build();

        usersCreateNewPage.addNewUser(user).clickButtonPushToApi();

        SoftAssert softAssert = new SoftAssert();

        String actualStatus = usersCreateNewPage.getTheTextOfTheConservationStatusOfANewProperty();
        softAssert.assertEquals(actualStatus, "Status: Successfully pushed, code: 201",
                "Статус не совпадает!");

        softAssert.assertTrue(usersCreateNewPage.isVisibleNewUserID(),
                "ID пользователя не отображён!");

        String actualIdText = usersCreateNewPage.getUserIdText();
        softAssert.assertTrue(actualIdText.matches("New user ID: \\d+"),
                "ID имеет неверный формат: " + actualIdText);

        String userId = actualIdText.replaceAll("\\D+", "");
        usersReadAllPage.openPage();
        softAssert.assertTrue(usersReadAllPage.isUserPresentById(userId),
                "Пользователь с ID " + userId + " не найден в списке!");

        softAssert.assertAll();
    }

    @DataProvider(name = "validationData")
    public Object[][] validationData() {
        return new Object[][]{
                // ---- First Name ----
                {"", "Petr", 34, "MALE", 123.0, "Status: Invalid request data"},
                {" ", "Petr", 34, "MALE", 123.0, "Status: not pushed"},
                {"!@#$", "Petr", 34, "MALE", 123.0, "Status: not pushed"},
                // ---- Last Name ----
                {"Petrov", "", 34, "MALE", 123.0, "Status: Invalid request data"},
                {"Petrov", " ", 34, "MALE", 123.0, "Status: not pushed"},
                {"Petrov", "!@#$", 34, "MALE", 123.0, "Status: not pushed"},
                // ---- Age ----
                {"Petrov", "Petr", 0, "MALE", 123.0, "Status: Invalid request data"},
                {"Petrov", "Petr", 200, "MALE", 123.0, "Status: not pushed"},
                {"Petrov", "Petr", -1, "MALE", 123.0, "Status: Invalid request data"},
                // ---- Sex (не выбрано) ----
                {"Petrov", "Petr", 34, null, 123.0, "Status: Invalid request data"},
                // ---- Money (отрицательные) ----
                {"Petrov", "Petr", 34, "MALE", -1.0, "Status: Successfully pushed, code: 201"},
                {"Petrov", "Petr", 34, "MALE", -0.01, "Status: Successfully pushed, code: 201"},
                // ---- Граничные значения ----
                {"Petrov", "Petr", 130, "MALE", 123.0, "Status: Successfully pushed, code: 201"},
                {"Petrov", "Petr", 34, "MALE", 0.0, "Status: Successfully pushed, code: 201"},
                {"Petrov", "Petr", 34, "MALE", 999999.99, "Status: Successfully pushed, code: 201"},
        };
    }

    @Test(
            dataProvider = "validationData",
            testName = "Валидация полей при создании пользователя",
            description = "Проверка, что система возвращает корректный статус при невалидных и граничных данных",
            priority = 2,
            groups = {"Negative", "Validation"}
    )
    @Description("Проверка валидации полей при создании пользователя")
    @Epic("Users")
    @Feature("Создание пользователя")
    @Story("Валидация полей")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Zvezdina Aleksandra")
    public void testUserValidation(String firstName, String lastName, int age, String sex, double money, String expectedStatus) {
        loginStep.successfulAuthorization(validEmail, validPassword);
        usersCreateNewPage.openPage();

        User user = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .age(age)
                .sex(sex)
                .money(money)
                .build();

        usersCreateNewPage.addNewUser(user).clickButtonPushToApi();

        // Для негативных кейсов используем getStatusText() (без ожидания успеха)
        // Для позитивных граничных – тоже подойдёт, но можно использовать и getTheText...
        String actualStatus = usersCreateNewPage.getStatusText();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(actualStatus, expectedStatus,
                "Статус не соответствует ожидаемому для данных: " + user);
        softAssert.assertAll();
    }
}