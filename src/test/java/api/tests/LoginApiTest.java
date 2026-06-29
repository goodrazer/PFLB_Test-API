package api.tests;

import api.adapters.BaseAdapter;
import api.adapters.LoginAdapter;
import io.qameta.allure.*;
import org.testng.annotations.Test;

public class LoginApiTest extends BaseAdapter {

    private final LoginAdapter loginAdapter = new LoginAdapter();

    @Test(testName = "API.01.01 Авторизация пользователя с валидными данными (JSON)",
            description = "Авторизация пользователя с валидными данными (JSON) POST/login",
            priority = 1,
            groups = {"Positive", "Regression", "Smoke"},
            enabled = true)
    @Description("Авторизация пользователя с валидными данными (JSON) тестирование API")
    @Epic("Epic01_Авторизация")
    @Feature("Авторизация пользователя")
    @Story("Авторизация пользователя с валидными данными (JSON)")
    @Severity(SeverityLevel.BLOCKER)
    @Link(value = "docs.google", name = "Чек-лист PFLB")
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    @Flaky
    @Owner("Malevaniy Anton")
    public void checkingApiUserAuthorizationWithValidData() {
        loginAdapter.authorizationWithValidCredentialsApi(validEmail, validPassword)
                .then()
                .log().all()
                .spec(BaseAdapter.checkStatusCode(202));
    }
}