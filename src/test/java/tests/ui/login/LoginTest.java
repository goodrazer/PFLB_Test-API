package tests.ui.login;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import tests.ui.base.BaseTest;
import static com.codeborne.selenide.Selenide.switchTo;
import static org.testng.Assert.*;

public class LoginTest extends BaseTest {

    @Test(testName = "АТ.01.01.Проверка валидации страницы 'Authorization' на наличие всех элементов",
            description = "Проверка валидации страницы 'Authorization' на наличие всех элементов",
            priority = 1,
            groups = {"Positive", "Regression"},
            enabled = true)
    @Description("Проверка валидации страницы 'Authorization' на наличие всех элементов.")
    @Epic("Epic01_Авторизация")
    @Feature("Проверка валидации страницы 'Authorization'")
    @Story("Проверка валидации страницы 'Authorization' на наличие всех элементов.")
    @Severity(SeverityLevel.CRITICAL)
    @Link(value = "docs.google", name = "Чек-лист PFLB")
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    @Flaky
    @Owner("Malevaniy Anton")
    public void checkingTheValidityOfTheAuthorizationPage() {
        loginPage.openPage();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(loginPage.isTitleVisible(), "Заголовок не отображается!");
        softAssert.assertTrue(loginPage.isEmailFieldVisible(), "Поле Email не отображается!");
        softAssert.assertTrue(loginPage.isPasswordFieldVisible(), "Поле Password не отображается!");
        softAssert.assertTrue(loginPage.isGoButtonVisible(), "Кнопка GO не отображается!");
        softAssert.assertTrue(loginPage.isLogoutButtonVisible(), "Кнопка LOGOUT не отображается!");
        softAssert.assertAll();
    }

    @Test(testName = "АТ.01.02.Авторизация с валидными кредами",
            description = "Проверка авторизации с валидными данными (валидный email + пароль) и" +
                    "отображения сообщения об успешном входе.",
            priority = 1,
            groups = {"Positive", "Smoke", "Regression"},
            enabled = true)
    @Description("Авторизация пользователя с валидными данными 'Email' и 'Password'.")
    @Epic("Epic01_Авторизация")
    @Feature("User authorization")
    @Story("Авторизация пользователя с валидными данными 'Email' и 'Password'")
    @Severity(SeverityLevel.BLOCKER)
    @Link(value = "docs.google", name = "Чек-лист PFLB")
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    @Flaky
    @Owner("Malevaniy Anton")
    public void positiveAuthorization() {
        loginPage.openPage()
                .positiveLogin(validEmail, validPassword);
        String alertText = switchTo().alert().getText();
        assertEquals(alertText, "Successful authorization");
        switchTo().alert().accept();
    }

    @Test(testName = "АТ.01.03.Проверка валидации поля 'Email' значением не являющимся адресом электронной почты." +
            "(Без домена @)",
            description = "Проверка валидации поля 'Email' значением не являющимся адресом электронной почты",
            priority = 3,
            groups = {"Negative", "Regression"},
            enabled = true)
    @Description("Проверка валидации поля 'Email' значением не являющимся адресом электронной почты.")
    @Epic("Epic01_Авторизация")
    @Feature("Проверка валидации поля 'Email'")
    @Story("Проверка валидации поля 'Email' значением не являющимся адресом электронной почты.")
    @Severity(SeverityLevel.CRITICAL)
    @Link(value = "docs.google", name = "Чек-лист PFLB")
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    @Flaky
    @Owner("Malevaniy Anton")
    public void checkForTheEmailField() {
        loginPage.openPage()
                .enterEmail("notemail.com")
                .enterPassword("12345");
        assertEquals(loginPage.getErrorMessageEmail(), "incorrect Email");
    }

    @Test(testName = "АТ.01.04.Проверка валидации поля 'Password' при вводе значений < 3 символов",
            description = "Проверка валидации поля 'Password' при вводе значений < 3 символов",
            priority = 3,
            groups = {"Negative", "Regression"},
            enabled = true)
    @Description("Проверка валидации поля 'Password' при вводе значений < 3 символов")
    @Epic("Epic01_Авторизация")
    @Feature("Проверка валидации поля 'Password'")
    @Story("Проверка валидации поля 'Password' при вводе значений < 3 символов")
    @Severity(SeverityLevel.CRITICAL)
    @Link(value = "docs.google", name = "Чек-лист PFLB")
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    @Flaky
    @Owner("Malevaniy Anton")
    public void checkValidationForPasswordFieldWithValueLessThanThree() {
        loginPage.openPage()
                .enterPassword("1!")
                .enterEmail("ayay@yandex.ru");
        assertEquals(loginPage.getErrorMessagePassword(),
                "password length must be more than 3 symbols and less than 8 symbols");
    }

    @Test(testName = "АТ.01.05.Проверка валидации поля 'Password' при вводе значений > 8 символов",
            description = "Проверка валидации поля 'Password' при вводе значений > 8 символов",
            priority = 3,
            groups = {"Negative", "Regression"},
            enabled = true)
    @Description("Проверка валидации поля 'Password' при вводе значений > 8 символов.")
    @Epic("Epic01_Авторизация")
    @Feature("Проверка валидации поля 'Password'")
    @Story("Проверка валидации поля 'Password' при вводе значений > 8 символов.")
    @Severity(SeverityLevel.CRITICAL)
    @Link(value = "docs.google", name = "Чек-лист PFLB")
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    @Flaky
    @Owner("Malevaniy Anton")
    public void checkThePasswordFieldIfTheValueIsGreaterThanEight() {
        loginPage.openPage()
                .enterPassword("12TY5!7O9")
                .enterEmail("ayay@yandex.ru");
        assertEquals(loginPage.getErrorMessagePassword(),
                "password length must be more than 3 symbols and less than 8 symbols");
    }

    @Test(testName = "АТ.01.06.Проверка доступности выполнений действий для авторизованного пользователя",
            description = "Проверка доступности выполнений действий для авторизованного пользователя " +
                    "(создать пользователя)",
            priority = 1,
            groups = {"Positive", "Regression", "Smoke"},
            enabled = true)
    @Description("Проверка доступности выполнений действий для авторизованного пользователя (создать пользователя)")
    @Epic("Epic01_Авторизация")
    @Feature("Проверка доступности выполнений действий для авторизованного пользователя")
    @Story("Проверка доступности выполнений действий для авторизованного пользователя (создать пользователя)")
    @Severity(SeverityLevel.BLOCKER)
    @Link(value = "docs.google", name = "Чек-лист PFLB")
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    @Flaky
    @Owner("Malevaniy Anton")
    public void checkingTheAvailabilityOfActionsForAnAuthorizedUser() {
        loginStep.successfulAuthorization(validEmail, validPassword);
        Selenide.sleep(5000);
        loginPage.clickUsersButton();
        Selenide.sleep(2000);
        loginPage.clickCreateNewButton();
        Selenide.sleep(2000);
        assertEquals(usersCreateNewPage.getTextElementIDWillBeGenerated(), "ID will be generated",
                "Ошибка!!! Создание пользователя недоступно для неавторизованного пользователя!");
    }

    @Test(testName = "АТ.01.07.Проверка недоступности выполнений действий для неавторизованного пользователя",
            description = "Проверка недоступности выполнений действий для неавторизованного пользователя" +
                    "(создать пользователя)",
            priority = 1,
            groups = {"Negative", "Regression", "Smoke"},
            enabled = true)
    @Description("Проверка недоступности выполнений действий для неавторизованного пользователя " +
            "(создать пользователя)")
    @Epic("Epic01_Авторизация")
    @Feature("Проверка недоступности выполнений действий для неавторизованного пользователя")
    @Story("Проверка недоступности выполнений действий для неавторизованного пользователя (создать пользователя)")
    @Severity(SeverityLevel.CRITICAL)
    @Link(value = "docs.google", name = "Чек-лист PFLB")
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    @Flaky
    @Owner("Malevaniy Anton")
    public void checkingWhetherActionsCanBePerformedByAnUnauthorizedUser() {
        loginPage.openPage()
                .enterEmail(validEmail)
                .enterPassword(validPassword)
                .clickLogoutButton();
        Selenide.sleep(3000);
        loginPage.clickUsersButton()
                .clickCreateNewButton();
        assertTrue(usersCreateNewPage.isVisibleElementIDWillBeGenerated(), "Ошибка!!! Операции доступны для неавторизованного пользователя");
    }

    @Test(testName = "АТ.01.08.Проверка отображения сообщений об ошибке при пустых значениях 'Email' и 'Password'",
            description = "Проверка отображения сообщений об ошибке при пустых значениях 'Email' и 'Password'",
            priority = 2,
            groups = {"Negative", "Regression"},
            enabled = true)
    @Description("Проверка отображения сообщений об ошибке при пустых значениях 'Email' и 'Password'")
    @Epic("Epic01_Авторизация")
    @Feature("Проверка отображения сообщений об ошибке на форме 'Authorization'")
    @Story("Проверка отображения сообщений об ошибке при пустых значениях 'Email' и 'Password'")
    @Severity(SeverityLevel.NORMAL)
    @Link(value = "docs.google", name = "Чек-лист PFLB")
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    @Flaky
    @Owner("Malevaniy Anton")
    public void checkingTheDisplayOfErrorMessagesOnTheAuthorizationPage() {
        SoftAssert softAssert = new SoftAssert();
        loginPage.openPage()
                .clickTabKeyEmailField();
        softAssert.assertEquals(loginPage.getTextMessageEmailCannotBeEmpty(),
                "email cannot be empty",
                "Ошибка!!! Сообщение об ошибке 'email cannot be empty' не отображено!");
        loginPage.clickTabKeyPasswordField();
        softAssert.assertEquals(
                loginPage.getTextMessagePasswordCannotBeEmpty(),
                "password cannot be empty",
                "Ошибка!!! Сообщение об ошибке 'password cannot be empty' не отображено!"
        );
        softAssert.assertAll();
    }

    @Test(testName = "АТ.01.09.Проверка авторизации с пустым значением 'Email' и валидным 'Password'",
            description = "Проверка авторизации с пустым значением 'Email' и валидным 'Password'",
            priority = 2,
            groups = {"Negative", "Regression"},
            enabled = true)
    @Description("Проверка авторизации с пустым значением 'Email' и валидным 'Password'")
    @Epic("Epic01_Авторизация")
    @Feature("Проверка авторизации с пустым значением")
    @Story("Проверка авторизации с пустым значением 'Email' и валидным 'Password'")
    @Severity(SeverityLevel.NORMAL)
    @Link(value = "docs.google", name = "Чек-лист PFLB")
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    @Flaky
    @Owner("Malevaniy Anton")
    public void checkingAuthorizationWithAnEmptyEmailValueAndAValidPassword() {
        loginPage.openPage()
                .clickTabKeyEmailField()
                .enterPassword(validPassword);
        Assert.assertEquals(loginPage.getTextMessageEmailCannotBeEmpty(),
                "email cannot be empty",
                "Ошибка!!! Сообщение об ошибке 'email cannot be empty' не отображено!");
    }

    @Test(testName = "АТ.01.10.Проверка авторизации с пустым значением 'Password' и валидным 'Email'",
            description = "Проверка авторизации с пустым значением 'Password' и валидным 'Email'",
            priority = 2,
            groups = {"Negative", "Regression"},
            enabled = true)
    @Description("Проверка авторизации с пустым значением 'Password' и валидным 'Email'")
    @Epic("Epic01_Авторизация")
    @Feature("Проверка авторизации с пустым значением")
    @Story("Проверка авторизации с пустым значением 'Password' и валидным 'Email'")
    @Severity(SeverityLevel.NORMAL)
    @Link(value = "docs.google", name = "Чек-лист PFLB")
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    @Flaky
    @Owner("Malevaniy Anton")
    public void checkingAuthorizationWithAnEmptyPasswordValueAndAValidEmail() {
        loginPage.openPage()
                .enterEmail(validEmail)
                .clickTabKeyPasswordField();
        Assert.assertEquals(loginPage.getTextMessagePasswordCannotBeEmpty(),
                "password cannot be empty",
                "Ошибка!!! Сообщение об ошибке 'password cannot be empty' не отображено!");
    }

    @Test(testName = "АТ.01.11.Проверка аллерта после ввода невалидного 'Email' валидного 'Password' и нажатия 'GO'",
            description = "Проверка аллерта после ввода невалидного 'Email' валидного 'Password' и нажатия 'GO'",
            priority = 1,
            groups = {"Negative", "Regression"},
            enabled = true)
    @Description("Проверка аллерта после ввода невалидного 'Email' валидного 'Password' и нажатия 'GO'")
    @Epic("Epic01_Авторизация")
    @Feature("Проверка аллерта после ввода невалидного 'Email'")
    @Story("Проверка аллерта после ввода невалидного 'Email' валидного 'Password' и нажатия 'GO'")
    @Severity(SeverityLevel.CRITICAL)
    @Link(value = "docs.google", name = "Чек-лист PFLB")
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    @Flaky
    @Owner("Malevaniy Anton")
    public void emailValidationCheckWithoutDomainWithErrorInformationMessage() {
        loginPage.openPage()
                .enterEmail("invalidemail@yandexru")
                .enterPassword(validPassword)
                .clickGoButton();
        String alertText = switchTo().alert().getText();
        assertEquals(alertText, "Bad request");
    }

    @Test(testName = "АТ.01.12.Проверка аллерта после ввода валидного 'Email' невалидного 'Password' и нажатия 'GO'",
            description = "Проверка аллерта после ввода валидного 'Email' невалидного 'Password' и нажатия 'GO'",
            priority = 1,
            groups = {"Negative", "Regression"},
            enabled = true)
    @Description("Проверка аллерта после ввода валидного 'Email' невалидного 'Password' и нажатия 'GO'")
    @Epic("Epic01_Авторизация")
    @Feature("Проверка аллерта после ввода невалидного 'Email'")
    @Story("Проверка аллерта после ввода валидного 'Email' невалидного 'Password' и нажатия 'GO'")
    @Severity(SeverityLevel.CRITICAL)
    @Link(value = "docs.google", name = "Чек-лист PFLB")
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    @Flaky
    @Owner("Malevaniy Anton")
    public void checkingTheAlertAfterEnteringAValidEmailAndAnInvalidPasswordAndPressingGo() {
        loginPage.openPage()
                .enterEmail(validEmail)
                .enterPassword("123123")
                .clickGoButton();
        String alertText = switchTo().alert().getText();
        assertEquals(alertText, "Bad request");
    }

    @DataProvider(name = "sqlInjectionPayloads")
    public Object[][] getSqlPayloads() {
        return new Object[][]{
                {"' OR '1'='1"},
                {"admin' --"},
                {"1' OR 1=1 --"},
                {"' OR ''='"},
                {"1 UNION SELECT null, null --"},
                {"'; DROP TABLE users; --"}
        };
    }

    @Test(testName = "АТ.01.13.Безопасность. Проверка защиты от SQL-инъекций в поле Password",
            description = "Проверка того, что ввод SQL-пейлоада в поле пароля не ломает БД и выдает стандартный алерт",
            priority = 1,
            groups = {"Negative", "Security", "Regression"},
            dataProvider = "sqlInjectionPayloads",
            enabled = true)
    @Description("Проверка устойчивости формы авторизации к SQL-инъекциям через поле 'Password'")
    @Epic("Epic01_Авторизация")
    @Feature("Защита от инъекций")
    @Story("Проверка обработки вредоносного SQL-кода в поле ввода пароля")
    @Severity(SeverityLevel.CRITICAL)
    @Link(value = "docs.google", name = "Чек-лист PFLB")
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    @Owner("Malevaniy Anton")
    public void checkingPasswordSqlInjectionProtectionSoft(String sqlPayload) {
        loginPage.openPage()
                .enterEmail(validEmail)
                .enterPassword(sqlPayload)
                .clickGoButton();
        String alertText = switchTo().alert().getText();
        String lowerAlertText = alertText.toLowerCase();
        switchTo().alert().accept();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertFalse(lowerAlertText.contains("sql"),
                String.format("Пейлоад [%s] вызвал утечку ключевого слова 'SQL' в алерте: '%s'", sqlPayload, alertText));
        softAssert.assertFalse(lowerAlertText.contains("syntax"),
                String.format("Пейлоад [%s] вызвал синтаксическую ошибку БД в алерте: '%s'", sqlPayload, alertText));
        softAssert.assertFalse(lowerAlertText.contains("database") || lowerAlertText.contains("driver"),
                String.format("Пейлоад [%s] раскрыл системные логи СУБД: '%s'", sqlPayload, alertText));
        softAssert.assertEquals(alertText, "Incorrect input data",
                String.format("Для пейлоада [%s] ожидался алерт 'Incorrect input data', но пришел: '%s'",
                        sqlPayload, alertText));
        softAssert.assertAll();
    }
}
