package tests.api.login;

import api.adapters.BaseAdapter;
import api.adapters.login.LoginAdapter;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginApiTest extends BaseAdapter {

    private final LoginAdapter loginAdapter = new LoginAdapter();

    @Test(testName = "API.01.01. Авторизация пользователя с валидными данными (JSON)",
            description = "Авторизация пользователя с валидными данными (JSON) POST/login",
            priority = 1,
            groups = {"Positive", "Regression", "Smoke"})
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
        Response loginResponse = loginAdapter.authorizationApi(validEmail, validPassword);
        Assert.assertEquals(loginResponse.getStatusCode(), 202,
                "Ошибка!!! Ожидался статус-код: 202, но сервер вернул: " + loginResponse.getStatusCode());
    }

    @Test(testName = "API.01.02. Проверка получения access_token в ответе POST/login",
            description = "Проверка получения access_token в ответе POST/login",
            priority = 1,
            groups = {"Positive", "Regression", "Smoke"})
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
        Response loginResponse = loginAdapter.authorizationApi(validEmail, validPassword);
        String accessToken = loginAdapter.obtainingATokenAPI(loginResponse);
        Assert.assertFalse(accessToken.trim().isEmpty(), "Ошибка!!! Полученный токен пустой!");
    }

    @Test(testName = "API.01.03. Авторизация с невалидным email",
            description = "Авторизация с невалидным email",
            priority = 2,
            groups = {"Negative", "Regression"})
    @Description("Авторизация с невалидным email")
    @Epic("Epic01_Авторизация")
    @Feature("Авторизация")
    @Story("Авторизация с невалидным email")
    @Severity(SeverityLevel.BLOCKER)
    @Link(value = "docs.google", name = "Чек-лист PFLB")
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    @Flaky
    @Owner("Malevaniy Anton")
    public void checkingAuthorizationWithInvalidEmailAPI() {
        Response loginResponse = loginAdapter.authorizationApi("Invalid@Email", validPassword);
        Assert.assertEquals(loginResponse.getStatusCode(), 403,
                "Ошибка!!! Ожидался статус-код: 403, но сервер вернул: " + loginResponse.getStatusCode());
    }

    @Test(testName = "API.01.04. Авторизация с невалидным Password",
            description = "Авторизация с невалидным Password",
            priority = 2,
            groups = {"Negative", "Regression"})
    @Description("Авторизация с невалидным Password")
    @Epic("Epic01_Авторизация")
    @Feature("Авторизация")
    @Story("Авторизация с невалидным Password")
    @Severity(SeverityLevel.BLOCKER)
    @Link(value = "docs.google", name = "Чек-лист PFLB")
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    @Flaky
    @Owner("Malevaniy Anton")
    public void checkingAuthorizationWithInvalidPasswordAPI() {
        Response loginResponse = loginAdapter.authorizationApi(validEmail, "51843943");
        Assert.assertEquals(loginResponse.getStatusCode(), 403,
                "Ошибка!!! Ожидался статус-код: 403, но сервер вернул: " + loginResponse.getStatusCode());
    }

    @Test(testName = "API.01.05. Авторизация с пустым телом запроса",
            description = "Авторизация с пустым телом запроса",
            priority = 2,
            groups = {"Negative", "Regression"})
    @Description("Авторизация с пустым телом запроса")
    @Epic("Epic01_Авторизация")
    @Feature("Авторизация")
    @Story("Авторизация с пустым телом запроса")
    @Severity(SeverityLevel.BLOCKER)
    @Link(value = "docs.google", name = "Чек-лист PFLB")
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    @Flaky
    @Owner("Malevaniy Anton")
    public void checkingAuthorizationRequestWithAnEmptyRequestBodyAPI() {
        Response loginResponse = loginAdapter.sendingAnAPIAuthorizationRequestWithAnEmptyRequestBody();
        Assert.assertEquals(loginResponse.getStatusCode(), 403,
                "Ошибка!!! Ожидался статус-код: 403, но сервер вернул: " + loginResponse.getStatusCode());
    }
}