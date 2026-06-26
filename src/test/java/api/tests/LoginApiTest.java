package api.tests;

import static api.adapters.BaseAdapter.BASE_URL;
import static io.restassured.RestAssured.*;
import api.models.LoginRequest;
import io.qameta.allure.*;
import org.apache.hc.core5.http.ContentType;
import org.testng.annotations.Test;
import ui.tests.BaseTest;

public class LoginApiTest extends BaseTest {

    Gson gson = new Gson();

    LoginRequest loginRequest = LoginRequest.builder()
            .username(validEmail)
            .password(validPassword)
            .build();

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
       given()
               .contentType(ContentType.APPLICATION_JSON)
               .baseUri(BASE_URL)
               .body(gson.toJson(loginRequest))
               .log().all()
       .when()
               .post("/login")
       .then()
               .log().all()
               .statusCode(202)

    }
}
