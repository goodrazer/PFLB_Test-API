package api.adapters.login;

import api.adapters.BaseAdapter;
import api.models.login.LoginRequest;
import api.models.login.LoginResponse;
import io.qameta.allure.Param;
import io.qameta.allure.Step;
import io.qameta.allure.model.Parameter;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import static io.restassured.RestAssured.given;

@Slf4j
public class LoginAdapter extends BaseAdapter {

    @Step("Отправка API-запроса на авторизацию")
    public Response authorizationApi(
            @Param(mode = Parameter.Mode.MASKED) String email,
            @Param(mode = Parameter.Mode.MASKED) String password) {
        log.info("Sending an API request for authorization");
        LoginRequest loginRequest = LoginRequest.builder()
                .username(email)
                .password(password)
                .build();
        return given()
                .spec(baseRequestSpec)
                .body(loginRequest)
                .when()
                .post("/login");
    }

    @Step("Получение токена из ответа API-запроса на авторизацию")
    public String obtainingATokenAPI(Response response) {
        log.info("Obtaining a token from the response of an API authorization request");
        return response
                .then()
                .spec(ok202)
                .extract()
                .body()
                .as(LoginResponse.class, ObjectMapperType.GSON)
                .getAccessToken();
    }

    @Step("Отправка API-запроса на авторизацию c пустым телом запроса")
    public Response sendingAnAPIAuthorizationRequestWithAnEmptyRequestBody() {
        log.info("Sending an API authorization request with an empty request body");
        return given()
                .spec(baseRequestSpec)
                .body("")
                .when()
                .post("/login");
    }
}