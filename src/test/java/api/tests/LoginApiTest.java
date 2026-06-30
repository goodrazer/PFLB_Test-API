package api.tests;

import api.adapters.BaseAdapter;
import api.adapters.LoginAdapter;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginApiTest extends BaseAdapter {

    private final LoginAdapter loginAdapter = new LoginAdapter();

    @Test(testName = "API.01.01. Авторизация пользователя с валидными данными (JSON)",
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
    public void checkingApiUserAuthorizationWithValidDataAPI() {
        Response response = loginAdapter.authorizationWithValidCredentialsApi(validEmail, validPassword);
        Assert.assertEquals(response.getStatusCode(), 202,
                "Ошибка!!! Ожидался статус-код: 202, но сервер вернул: " + response.getStatusCode());
    }

    @Test(testName = "API.01.02. Проверка получения access_token в ответе POST/login",
            description = "Проверка получения access_token в ответе POST/login",
            priority = 1,
            groups = {"Positive", "Regression", "Smoke"},
            enabled = true)
    @Description("Проверка получения access_token в ответе POST/login")
    @Epic("Epic01_Авторизация")
    @Feature("Проверка получения access_token")
    @Story("Проверка получения access_token в ответе POST/login")
    @Severity(SeverityLevel.BLOCKER)
    @Link(value = "docs.google", name = "Чек-лист PFLB")
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    @Flaky
    @Owner("Malevaniy Anton")
    public void checkingWhetherAnAccessTokenInTheLoginResponseAPI() {
        Response loginResponse = loginAdapter.authorizationWithValidCredentialsApi(validEmail, validPassword);
        String accessToken = loginAdapter.obtainingATokenAPI(loginResponse);
        Assert.assertFalse(accessToken.trim().isEmpty(), "Ошибка!!! Полученный токен пустой!");
    }
}