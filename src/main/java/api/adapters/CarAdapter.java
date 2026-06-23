package api.adapters;

import api.models.*;
import io.qameta.allure.Step;
import io.restassured.module.jsv.JsonSchemaValidator;

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
                .spec(ok200)
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
    public static void deleteCar(Integer code) {
        given()
                .spec(spec)
                .pathParam("code", code)
                .log().all()
                .when()
                .delete("/car/{code}")
                .then()
                .log().all()
                .spec(ok200);
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
}
