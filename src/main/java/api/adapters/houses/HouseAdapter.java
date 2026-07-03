package api.adapters.houses;

import api.adapters.base.BaseAdapter;
import api.models.houses.HouseCreateNewRequest;
import api.models.houses.HouseCreateNewResponse;
import io.qameta.allure.Param;
import io.qameta.allure.Step;
import io.qameta.allure.model.Parameter;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import static io.restassured.RestAssured.given;

@Slf4j
public class HouseAdapter extends BaseAdapter {

    @Step("Создание дома с помощью POST /house с проверкой статус-кода")
    public HouseCreateNewResponse houseCreateNew(HouseCreateNewRequest requestBody,
                                                 @Param(mode = Parameter.Mode.MASKED) String token) {
        log.info("Executing the script to create a new house via POST /house with status 201 validation");
        Response response = houseCreateNewNotVerifyStatusCode(requestBody, token);
        response.then().spec(ok201);
        String responseBodyString = response.getBody().asString();
        if (responseBodyString.trim().isEmpty() || responseBodyString.equals("{}")) {
            return new HouseCreateNewResponse();
        }
        return gson.fromJson(responseBodyString, HouseCreateNewResponse.class);
    }
    @Step("Отправка запроса POST /house без проверки статус-кода")
    public Response houseCreateNewNotVerifyStatusCode(HouseCreateNewRequest requestBody,
                                                      @Param(mode = Parameter.Mode.MASKED) String token) {
        log.info("Sending a POST request to the /house endpoint without checking the status code");
        return given()
                .spec(baseRequestSpecNotMasked)
                .header("Authorization", "Bearer " + token)
                .body(gson.toJson(requestBody))
                .when()
                .post("/house");
    }
}
