package tests;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import static com.codeborne.selenide.Selenide.switchTo;

public class LoginTest extends BaseTest{

    @Test(testName = "Авторизация с валидными кредами",
            description = "Авторизация пользователя с валидными данными 'Email' и 'Password'",
            priority = 1,
            groups = {"Positive", "Smoke", "Regression"},
            enabled = true)
    @Description("Авторизация пользователя с валидными данными 'Email' и 'Password'")
    @Epic("Epic01_Авторизация")
    @Feature("User authorization")
    @Story("Авторизация пользователя с валидными данными 'Email' и 'Password'")
    @Severity(SeverityLevel.BLOCKER)
    @Link ("DocumentationLink")
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    @Flaky
    @Owner("Malevaniy Anton")
    public void positiveAuthorization() {
        loginPage.openPage()
                .positiveLogin(validEmail,validPassword);
        String alertText = switchTo().alert().getText();
        Assert.assertEquals(alertText, "Successful authorization");
        switchTo().alert().accept();
    }

    @Test(testName = "Проверка валидации поля 'Email' значением не являющимся адресом электронной почты.",
            description = "Проверка валидации поля 'Email' значением не являющимся адресом электронной почты.",
            priority = 3,
            groups = {"Negative", "Regression"},
            enabled = true)
    @Description("Проверка валидации поля 'Email' значением не являющимся адресом электронной почты.")
    @Epic("Epic01_Авторизация")
    @Feature("Проверка валидации поля 'Email'")
    @Story("Проверка валидации поля 'Email' значением не являющимся адресом электронной почты.")
    @Severity(SeverityLevel.CRITICAL)
    @Link("DocumentationLink")
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    @Flaky
    @Owner("Malevaniy Anton")
    public void checkForTheEmailField() {
        loginPage.openPage()
                .enterEmail("notemail.com")
                .enterPassword("12345");
        Assert.assertEquals(loginPage.getErrorMessageEmail(), "incorrect Email");
    }

    @Test(testName = "Проверка валидации поля 'Password' при вводе значений < 3 символов.",
            description = "Проверка валидации поля 'Password' при вводе значений < 3 символов.",
            priority = 3,
            groups = {"Negative", "Regression"},
            enabled = true)
    @Description("Проверка валидации поля 'Password' при вводе значений < 3 символов.")
    @Epic("Epic01_Авторизация")
    @Feature("Проверка валидации поля 'Password'")
    @Story("Проверка валидации поля 'Password' при вводе значений < 3 символов.")
    @Severity(SeverityLevel.CRITICAL)
    @Link("DocumentationLink")
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    @Flaky
    @Owner("Malevaniy Anton")
    public void checkValidationForPasswordFieldWithValueLessThanThree() {
        loginPage.openPage()
                .enterPassword("1!")
                .enterEmail("ayay@yandex.ru");
        Assert.assertEquals(loginPage.getErrorMessagePassword(),
                "password length must be more than 3 symbols and less than 8 symbols");
    }

    @Test(testName = "Проверка валидации поля 'Password' при вводе значений > 8 символов.",
            description = "Проверка валидации поля 'Password' при вводе значений > 8 символов.",
            priority = 3,
            groups = {"Negative", "Regression"},
            enabled = true)
    @Description("Проверка валидации поля 'Password' при вводе значений > 8 символов.")
    @Epic("Epic01_Авторизация")
    @Feature("Проверка валидации поля 'Password'")
    @Story("Проверка валидации поля 'Password' при вводе значений > 8 символов.")
    @Severity(SeverityLevel.CRITICAL)
    @Link("DocumentationLink")
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    @Flaky
    @Owner("Malevaniy Anton")
    public void checkThePasswordFieldIfTheValueIsGreaterThanEight() {
        loginPage.openPage()
                .enterPassword("12TY5!7O9")
                .enterEmail("ayay@yandex.ru");
        Assert.assertEquals(loginPage.getErrorMessagePassword(),
                "password length must be more than 3 symbols and less than 8 symbols");
    }
}