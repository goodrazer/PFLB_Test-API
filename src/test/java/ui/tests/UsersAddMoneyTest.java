package ui.tests;

import io.qameta.allure.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ui.dto.User;

import static org.testng.Assert.assertEquals;

@Epic("Users")
@Feature("Пополнение баланса")
@Owner("Zvezdina Aleksandra")
public class UsersAddMoneyTest extends BaseTest {

    private static final String SUCCESS_CREATE_STATUS =
            "Status: Successfully pushed, code: 201";
    private static final String SUCCESS_ADD_MONEY_STATUS =
            "Status: Successfully pushed, code: 200";
    private static final String INCORRECT_INPUT_STATUS =
            "Status: Incorrect input data";
    private static final String USER_NOT_FOUND_STATUS =
            "Status: AxiosError: Request failed with status code 404";

    @DataProvider(name = "validMoneyData")
    public Object[][] validMoneyData() {
        return new Object[][]{
                {0.01},
                {100.0},
                {999999.99}
        };
    }
    @DataProvider(name = "invalidAddMoneyData")
    public Object[][] invalidAddMoneyData() {
        return new Object[][]{
                {"", "100", INCORRECT_INPUT_STATUS},
                {"999999", "100", USER_NOT_FOUND_STATUS},
                {"1", "-10", INCORRECT_INPUT_STATUS},
                {"1", "abc", INCORRECT_INPUT_STATUS}
        };
    }
    private String createTestUser() {
        usersCreateNewPage.openPage();
        User user = User.builder()
                .firstName("Test")
                .lastName("User")
                .age(30)
                .sex("MALE")
                .money(100.0)
                .build();
        usersCreateNewPage
                .addNewUser(user)
                .clickButtonPushToApi();
        assertEquals(
                usersCreateNewPage.getTheTextOfTheConservationStatusOfANewProperty(),
                SUCCESS_CREATE_STATUS,
                "Пользователь не был создан"
        );

        return usersCreateNewPage.getUserIdText()
                .replaceAll("\\D+", "");
    }

    @Test(
            testName = "Успешное пополнение баланса",
            description = "Проверка корректного увеличения баланса пользователя",
            groups = {"Positive", "Regression", "Smoke"},
            dataProvider = "validMoneyData"
    )
    @Severity(SeverityLevel.BLOCKER)
    public void testAddMoneySuccess(double amount) {

        loginStep.successfulAuthorization(validEmail, validPassword);
        String userId = createTestUser();
        usersReadAllPage.openPage();
        boolean userExistsBefore = usersReadAllPage.isUserPresentById(userId);
        usersAddMoneyPage.openPage();
        String oldStatus = usersAddMoneyPage.getStatusMessage();
        usersAddMoneyPage
                .fillForm(userId, String.valueOf(amount))
                .clickPushToApi();
        String actualStatus = usersAddMoneyPage.waitForStatusChange(oldStatus);
        String balanceText = usersAddMoneyPage.getMoneyDisplayText();
        double actualBalance = Double.parseDouble(
                balanceText.replaceAll("[^\\d.]", "")
        );
        usersReadAllPage.openPage();
        boolean userExistsAfter = usersReadAllPage.isUserPresentById(userId);
        double initialBalance = 100.0;
        double expectedBalance = initialBalance + amount;
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(
                actualStatus,
                SUCCESS_ADD_MONEY_STATUS,
                "Статус пополнения неверный"
        );
        softAssert.assertEquals(
                actualBalance,
                expectedBalance,
                0.001,
                "Баланс в UI не совпадает"
        );
        softAssert.assertTrue(
                userExistsBefore,
                "Пользователь не найден в таблице ДО пополнения"
        );
        softAssert.assertTrue(
                userExistsAfter,
                "Пользователь не найден в таблице ПОСЛЕ пополнения"
        );
        softAssert.assertAll();
    }

    @Test(
            testName = "Негативные сценарии пополнения баланса",
            description = "Проверка обработки ошибок при пополнении баланса",
            groups = {"Negative", "Validation"},
            dataProvider = "invalidAddMoneyData"
    )
    @Severity(SeverityLevel.CRITICAL)
    public void testAddMoneyInvalid(
            String userId,
            String money,
            String expectedStatus
    ) {
        loginStep.successfulAuthorization(validEmail, validPassword);
        usersAddMoneyPage.openPage();
        String oldStatus = usersAddMoneyPage.getStatusMessage();
        usersAddMoneyPage
                .fillForm(userId, money)
                .clickPushToApi();
        String actualStatus = usersAddMoneyPage.waitForStatusChange(oldStatus);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(
                actualStatus,
                expectedStatus,
                "Ошибка не соответствует ожиданиям (userId=" + userId + ", money=" + money + ")"
        );
        softAssert.assertAll();
    }
}