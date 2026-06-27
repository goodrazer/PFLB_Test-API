package api.adapters;

import api.models.*;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.List;
import static io.restassured.RestAssured.given;

public class CarAdapter extends adapters.BaseAdapter {

    @Step("Создание автомобиля с помощью POST /car")
    public static CreateCarRs createCar(CreateCarRq carRq) {
        return given()
                .spec(spec)
                .body(gson.toJson(carRq))
                .log().all()
                .when()
                .post("/car")
                .then()
                //.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/createCarSchema.json"))
                .log().all()
                .spec(ok201)
                .extract()
                .as(CreateCarRs.class);
    }

    @Step("Проверка авто с помощью Get /car/{carId}")
    public static GetCarRs getCar(Integer code) {
        return given()
                .spec(spec)
                .pathParam("code", code)
                .log().all()
                .when()
                .get("/car/{code}")
                .then()
                //.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/getCarSchema.json"))
                .log().all()
                .spec(ok200)
                .extract()
                .as(GetCarRs.class);
    }

    @Step("Удаление автомобиля с помощью DELETE /car/{carId}")
    public static Response deleteCar(Integer code) {
        return RestAssured.given()
                .spec(spec)
                .pathParam("code", code)
                .log().all()
                .when()
                .delete("/car/{code}");
    }

    @Step("Удаление автомобиля с помощью DELETE /car/{carId} негативный сценарий")
    public static void deleteNegativeCar(Integer code) {
        given()
                .spec(spec)
                .pathParam("code", code)
                .log().all()
                .when()
                .delete("/car/{code}")
                .then()
                .log().all()
                .spec(ok204);
    }

    @Step("Изменение автомобиля с помощью PUT /car/{carId}")
    public static UpdateCarRs updateCar(UpdateCarRq updateCarRq, int code) {
        return given()
                .spec(spec)
                .pathParam("code", code)
                .body(gson.toJson(updateCarRq))
                .log().all()
                .when()
                .put("/car/{code}")
                .then()
                //.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/createCarSchema.json"))
                .log().all()
                .spec(ok202)
                .extract()
                .as(UpdateCarRs.class);
    }

    public static Response getCarRawResponse(Integer carId) {
        return given().spec(adapters.BaseAdapter.spec)
                .when()
                .get("/car/" + carId);
        // ВАЖНО: Здесь НЕТ .then().statusCode(...), НЕТ .as(GetCarRs.class)
    }

    @Step("Проверка авто с помощью Get /cars")
    public static List<GetCarRs> getAllCar() {
        return given()
                .spec(spec)
                //.log().all()
                .when()
                .get("/cars")
                .then()
                //.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/getCarSchema.json"))
                .log().all()
                .spec(ok200)
                .extract()
                .as(new TypeRef<List<GetCarRs>>() {});
    }

    // НОВЫЙ МЕТОД для негативных тестов (возвращает Response, не проверяет статус)
    public static Response createCarRaw(CreateCarRq rq) {
        return (Response) RestAssured.given()
                .spec(spec)
                .contentType(ContentType.JSON)
                .body(rq)
                .when()
                .post("/car");
    }

    @Step("Негативная проверка авто с помощью Get /car/{carId}")
    public static Response getNegativeCar(Integer code) {
        return RestAssured.given()
                .spec(spec)
                .pathParam("code", code)
                .log().all()
                .when()
                .get("/car/{code}");
    }

    @Step("Изменение автомобиля с помощью PUT /car/{carId}")
    public static Response NegativeUpdateCar(int code, UpdateCarRq request) {
        return RestAssured.given()
                .spec(spec)
                .pathParam("code", code)
                .body(request)
                .log().all()
                .when()
                .put("/car/{code}");
    }


    @Step("Изменение автомобиля (сырой запрос) PUT /car/{carId}")
    public static Response updateCarRaw(UpdateCarRq updateCarRq, int code) {
        return given()
                .spec(spec)
                .pathParam("code", code)
                .body(gson.toJson(updateCarRq))
                .log().all()
                .when()
                .put("/car/{code}")
                .then()
                .log().all()
                .extract()
                .response();
    }

    @Step("Негативная проверка авто с помощью Get /car/{carId}")
    public static Response getUserCar(Integer code) {
        return RestAssured.given()
                .spec(spec)
                .pathParam("code", code)
                .log().all()
                .when()
                .get("/user/{code}/cars");
    }

    @Step("Проверка авто с помощью Get /cars")
    public static List<GetUserRs> getAllUser() {
        return given()
                .spec(spec)
                .when()
                .get("/users")
                .then()
                .log().all()
                .spec(ok200)
                .extract()
                .as(new TypeRef<List<GetUserRs>>() {});
    }
}
