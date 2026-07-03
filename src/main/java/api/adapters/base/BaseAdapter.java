package api.adapters.base;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.Listeners;
import utils.PropertyReader;
import utils.TestListener;

@Log4j2
@Listeners(TestListener.class)
public class BaseAdapter {

    public static final String BASE_API_URL = PropertyReader.getProperty("baseAPIUrl");
    protected String validEmail = System.getProperty("Email", PropertyReader.getProperty("email"));
    protected String validPassword = System.getProperty("Password", PropertyReader.getProperty("password"));

    public static Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .setPrettyPrinting()
            .setVersion(1.0)
            .create();

    // Накидал общую спецификацию запроса, устанавливает базовый URL и тип данных.
    public static final RequestSpecification baseRequestSpec = new RequestSpecBuilder()
            .setBaseUri(BASE_API_URL)
            .setContentType(ContentType.JSON)
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
}
