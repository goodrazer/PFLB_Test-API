package api.adapters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import lombok.extern.log4j.Log4j2;
import utils.LogMaskFilter;
import utils.PropertyReader;

@Log4j2
public class BaseAdapter {

    public static final String BASE_URL = "http://82.142.167.37:4879";
    protected String validEmail = System.getProperty("Email", PropertyReader.getProperty("email"));
    protected String validPassword = System.getProperty("Password", PropertyReader.getProperty("password"));

    public static Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .setPrettyPrinting()
            .setVersion(1.0)
            .create();

    // Общая спецификация запроса для всех будущих эндпоинтов с маскированием кредов логина и пароля
    public static final RequestSpecification baseRequestSpec = new RequestSpecBuilder()
            .setBaseUri(BASE_URL)
            .setContentType(ContentType.JSON)
            .addFilter(new LogMaskFilter())
            .build();

    public static ResponseSpecification ok200 = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .build();

    public static ResponseSpecification ok202 = new ResponseSpecBuilder()
            .expectStatusCode(202)
            .build();

    public static ResponseSpecification ok201 = new ResponseSpecBuilder()
            .expectStatusCode(201)
            .build();

    public static ResponseSpecification ok204 = new ResponseSpecBuilder()
            .expectStatusCode(204)
            .build();

    //Сделал общий метод получения кода, просто подставляйте код в проверки прямо в тестах
    public static ResponseSpecification checkStatusCode(int statusCode) {
        return new ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .build();
    }
}