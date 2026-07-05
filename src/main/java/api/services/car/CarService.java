package api.services.car;

import api.adapters.base.BaseAdapter;
import api.adapters.cars.CarAdapter;
import api.adapters.login.AuthHelper;
import api.models.cars.CreateCarRq;
import api.models.cars.CreateCarRs;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import io.restassured.specification.RequestSpecification;
import lombok.extern.log4j.Log4j2;
import ui.dto.cars.CarGenerated;

import static com.codeborne.selenide.Selenide.$;

@Log4j2
public class CarService extends BaseAdapter {

    public final SelenideElement input = $("#user_input");
    public String newCarId;
    private AuthHelper authHelper;
    private RequestSpecification authSpec;

    @Step("Создание нового автомобиля через API с данными: {carGenerated}")
    public CarService createCarWithApiAccess(
            CarGenerated carGenerated
    ) {
        // Авторизуемся
        authHelper = new AuthHelper(BASE_API_URL);
        authHelper.loginAsJson(validEmail, validPassword);
        authSpec = authHelper.getAuthenticatedSpec();
        // Создаём DTO автомобиля
        CreateCarRq rqCreateCar = CreateCarRq.builder()
                .engineType(carGenerated.getEngineType())
                .model(carGenerated.getModel())
                .mark(carGenerated.getMark())
                .price(Double.parseDouble(carGenerated.getPrice()))
                .build();
        // создание нового автомобиля
        log.info("Create new car with data: Engine Type = \"" + carGenerated.getEngineType() + "\", Mark = \""
                + carGenerated.getMark() + "\", Model = \"" + carGenerated.getModel() + "\", Price = \""
                + carGenerated.getPrice() + "\"; in API");
        CreateCarRs newCarResponse = CarAdapter.createCar(rqCreateCar, authSpec);
        newCarId = String.valueOf(newCarResponse.getId());
        log.info("New car has been created with id = \"" + newCarId + "\"");
        return this;
    }
}
