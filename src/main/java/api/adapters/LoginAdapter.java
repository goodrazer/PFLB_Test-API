package api.adapters;

import api.models.LoginRequest;
import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.LogMaskFilter;
import static io.restassured.RestAssured.given;

public class LoginAdapter extends BaseAdapter {

    private final Gson gson = new Gson();

    public Response authorizationWithValidCredentialsApi(String email, String password) {
        LoginRequest loginRequest = LoginRequest.builder()
                .username(email)
                .password(password)
                .build();
        return given()
                .filter(new LogMaskFilter())
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .body(gson.toJson(loginRequest))
                .when()
                .post("/login");
    }
}