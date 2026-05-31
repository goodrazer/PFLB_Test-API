package tests;

import io.qameta.allure.*;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.switchTo;
import static org.testng.Assert.assertEquals;

public class PositiveLoginTest extends BaseTest{

    @Test(testName = "Positive authorization",
            description = "User authorization by entering valid credentials",
            priority = 1,
            groups = "Positive",
            enabled = true)
    @Description("User authorization by entering valid credentials")
    @Epic("01.Authorization")
    @Feature("User authorization")
    @Story("User authorization by entering valid credentials")
    @Severity(SeverityLevel.CRITICAL)
    @Link ("DocumentationLink")
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    @Flaky
    @Owner("Malevaniy Anton")
    public void positiveAuthorization() {
        loginPage.openPage()
                .isPageOpened()
                .positiveLogin(validUser,validPassword);
        String alertText = switchTo().alert().getText();
        assertEquals(alertText, "Successful authorization");
        switchTo().alert().accept();
    }
}
