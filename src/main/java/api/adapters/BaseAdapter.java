package adapters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class BaseAdapter {

    public static Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .setPrettyPrinting()
            .setVersion(1.0)
            .create();

    public static RequestSpecification spec = new RequestSpecBuilder()
            .setContentType(ContentType.JSON)
            .setBaseUri("http://82.142.167.37:4879")
            //.setBasePath("/v1")
            .addHeader("Token",
                    "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyQHBmbGIucnUiLCJyb2xlcyI6WyJ1c2VyI"
                            + "l0sImlzcyI6Imh0dHA6Ly84Mi4xNDIuMTY3LjM3OjQ4NzkvbG9naW4iLCJleHAiOjE3ODE5ODA4MDh9.q-u2I" +
                            "-lUPJUVu4AZy-TKQd1EP6R66JUPDLXEY6ij0_")
            .build();

    public static ResponseSpecification ok200 = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .build();

    public static ResponseSpecification ok202 = new ResponseSpecBuilder()
            .expectStatusCode(202)
            .build();
}