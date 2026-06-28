package api.adapters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class BaseAdapter {

    public static Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .setPrettyPrinting()
            .setVersion(1.0)
            .create();

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
}