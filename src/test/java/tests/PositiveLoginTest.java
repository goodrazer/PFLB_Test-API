package tests;

import io.qameta.allure.*;
import org.testng.annotations.Test;
import static com.codeborne.selenide.Selenide.switchTo;
import static org.testng.Assert.assertEquals;

public class PositiveLoginTest extends BaseTest{

    @Test(testName = "Positive authorization",
            description = "Авторизация пользователя с валидными данными 'Email' и 'Password'",
            priority = 1,
            groups = "Positive",
            enabled = true)
    @Description("Авторизация пользователя с валидными данными 'Email' и 'Password'")
    @Epic("01.Authorization")
    @Feature("User authorization")
    @Story("Авторизация пользователя с валидными данными 'Email' и 'Password'")
    @Severity(SeverityLevel.CRITICAL)
    @Link ("DocumentationLink")
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    @Flaky
    @Owner("Malevaniy Anton")
    public void positiveAuthorization() {
        loginPage.openPage()
                .isPageOpened()
                .positiveLogin(validEmail,validPassword);
        String alertText = switchTo().alert().getText();
        assertEquals(alertText, "Successful authorization");
        switchTo().alert().accept();
    }
}