package api.adapters.login;

import api.adapters.BaseAdapter;
import api.models.login.LoginRequest;
import api.models.login.LoginResponse;
import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class AuthHelper {

    private String jwtToken;
    private final String baseUrl;
    private final Gson gson = BaseAdapter.gson; // Используем общий Gson

    public AuthHelper(String baseUrl) {
        this.baseUrl = baseUrl;
        RestAssured.baseURI = baseUrl;
    }

    public String loginAsJson(String username, String password) {
        log.info("Авторизация пользователя и получение токена");

        LoginRequest request = LoginRequest.builder()
                .username(username)
                .password(password)
                .build();
        String body = gson.toJson(request);

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/login")
                .then()
                .statusCode(202)
                .extract()
                .response();

        LoginResponse loginResponse = gson.fromJson(response.asString(), LoginResponse.class);
        this.jwtToken = loginResponse.getAccessToken();
        return this.jwtToken;
    }

    public RequestSpecification getAuthenticatedSpec() {
        log.info("Подставляем токен в spec");
        if (jwtToken == null || jwtToken.isEmpty()) {
            throw new IllegalStateException("Сначала необходимо выполнить loginAsJson()");
        }
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + jwtToken);
    }
}