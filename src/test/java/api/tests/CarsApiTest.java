package api.tests;

import api.adapters.CarAdapter;
import api.models.*;
import io.qameta.allure.*;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.util.List;
import java.util.Optional;
import static org.testng.AssertJUnit.assertEquals;
import io.qameta.allure.SeverityLevel;

@Log4j2
@Epic("Cars")
@Feature("CarsAPI")
@Owner("Egor P.")
@Link(value = "docs.google", name = "Чек-лист PFLB")
public class CarsApiTest {
    SoftAssert softAssert = new SoftAssert();

    //Данные для создания
    CreateCarRq rqCreateCar = CreateCarRq.builder()
            .engineType("Electric")
            .model("Wrangler")
            .mark("Jeep")
            .price(4999.99)
            .build();

    //Данные для изменения
    UpdateCarRq rqUpdateCar = UpdateCarRq.builder()
            .engineType("Diesel")
            .model("Grand Cherokee")
            .mark("Jeep")
            .price(7999.55)
            .build();

    @Test(testName = "АТ.03.22.Проверка цикла CRUD авто",
            description = "API проверка всего цикла CRUD. Создание, получение, изменения и удаление авто",
            priority = 1,
            groups = {"Positive", "E2E", "Regression", "Smoke"},
            enabled = true)
    @Description("API проверка всего цикла CRUD. Создание, получение, изменения и удаление авто")
    @Story("API Cars")
    @Severity(SeverityLevel.CRITICAL)
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    @Owner("Permyakov Egor")
    public void checkCarsCRUD() {
        //1. СОЗДАНИЕ (POST)
        log.info("Create auto: {} {}", rqCreateCar.getMark(), rqCreateCar.getModel());
        CreateCarRs rs = CarAdapter.createCar(rqCreateCar);
        Integer createdId = rs.getId();
        softAssert.assertNotNull(createdId, "Сервер не вернул ID созданного автомобиля");
        softAssert.assertTrue(createdId > 0, "ID должен быть положительным числом");

        //2. ЧТЕНИЕ (GET)
        log.info("Check created auto with id: {}", createdId);
        GetCarRs getCarRs = CarAdapter.getCar(createdId);
        softAssert.assertEquals(createdId, getCarRs.getId(),
                "ID созданного авто должен совпадать с запрошенным");
        softAssert.assertEquals("Wrangler", getCarRs.getModel());

        //3. Обновление (PUT) ---
        log.info("Update auto: {} {}. Then this auto is: {} {}", rqCreateCar.getMark(), rqCreateCar.getModel(),
                rqUpdateCar.getMark(), rqUpdateCar.getModel());
        CarAdapter.updateCar(rqUpdateCar, createdId);
        // Проверяем, что обновление применилось через GET
        GetCarRs updatedCar = CarAdapter.getCar(createdId);
        softAssert.assertEquals("Grand Cherokee", updatedCar.getModel());

        //4. УДАЛЕНИЕ (DELETE) ---
        log.info("Delete auto with id: {}", createdId);
        CarAdapter.deleteCar(createdId);
        // Проверяем, что обновление применилось через GET
        log.info("Check created auto with id: {}, after delete", createdId);
        Response responseAfterDelete = CarAdapter.getCarRawResponse(createdId);
        responseAfterDelete.then()
                .assertThat()
                .statusCode(204);

        softAssert.assertAll();
    }

    @Test(testName = "АТ.03.23.Проверка списка авто с помощью GET /cars",
            description = "API проверка списка всех авто с помощью ручки GET /cars",
            priority = 2,
            groups = {"Positive", "E2E", "Regression", "Smoke"},
            enabled = true)
    @Description("API проверка списка всех авто с помощью ручки GET /cars")
    @Story("API Cars")
    @Severity(SeverityLevel.CRITICAL)
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    @Owner("Permyakov Egor")
    public void checkGetAllCars() {
        log.info("Get all cars");
        List<GetCarRs> cars = CarAdapter.getAllCar();
        softAssert.assertFalse(cars.isEmpty(), "Список автомобилей пуст");
        //Ищем автомобиль с конкретной моделью (например, "X5")
        Optional<GetCarRs> foundCar = cars.stream()
                .filter(car -> "X5".equals(car.getModel()))
                .findFirst();
        //Проверяем, что такой автомобиль вообще найден в списке
        softAssert.assertTrue(foundCar.isPresent(), "Автомобиль с моделью 'X5' не найден в списке");
        //Если найден, проверяем его модель
        if (foundCar.isPresent()) {
            String actualMark = foundCar.get().getMark();
            softAssert.assertEquals("BMW", actualMark, "Марка автомобиля с моделью 'X5' не совпадает");
        }
        //Можно проверить что автомобилей больше 100 - Опционально
        softAssert.assertTrue(cars.size() > 100,
                "Количество автомобилей должно быть больше 100, но найдено: " + cars.size());

        softAssert.assertAll();
    }

    @DataProvider(name = "negativeCarData")
    public Object[][] negativeCarData() {
        return new Object[][] {
                {
                        CreateCarRq.builder().engineType("Diesel").model("Grand Cherokee").mark("").price(7999.55).
                                build(), 400 //пустая марка
                }, {
                        CreateCarRq.builder().engineType("Diesel").model("").mark("Jeep").price(7999.55).
                                build(), 400 //пустая модель
                }, {
                        CreateCarRq.builder().engineType("Diesel").model("Grand Cherokee").mark("Jeep").price(-100.0).
                                build(), 400 //отрицательная сумма
                }, {
                        CreateCarRq.builder().engineType("Benzin").model("Grand Cherokee").mark("Jeep").price(7999.55).
                                build(), 400 //неверный тип двигателя
                }, {
                        CreateCarRq.builder().engineType("").model("Grand Cherokee").mark("Jeep").price(7999.55).
                                build(), 400 //пустой тип двигателя
                }
        };
    }

    @Test(testName = "АТ.03.24.Проверка негативного создания авто через API",
            description = "API проверка создания авто POST /car с невалидным телом",
            priority = 2,
            groups = {"Negative", "E2E", "Regression", "Smoke"},
            enabled = true,
            dataProvider = "negativeCarData")
    @Description("API проверка создания авто POST /car с невалидным телом")
    @Story("API Cars")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    @Owner("Permyakov Egor")
    public void checkNegativeCreateCar(CreateCarRq request, int expectedStatusCode) {
        log.info("Running negative test. Mark: '{}', Model: '{}', Price: {}",
                request.getMark(), request.getModel(), request.getPrice());
        Response response = CarAdapter.createCarRaw(request);
        assertEquals(response.getStatusCode(), expectedStatusCode);
    }

    @Test(testName = "AT.03.25.Удаление несуществующего авто с помощью API",
            description = "Удаление автомобиля с несуществующим id DELETE /car/{carId}",
            priority = 2,
            groups = {"Negative", "E2E", "Regression", "Smoke"},
            enabled = true)
    @Description("Удаление автомобиля с несуществующим id DELETE /car/{carId}")
    @Story("API_Cars")
    @Severity(SeverityLevel.NORMAL) // <--- Теперь компилятор видит NORMAL
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    @Owner("Permyakov Egor")
    public void checkNegativeDeleteCar() {
        log.info("Get all cars to calculate non-existing ID");
        Response response = CarAdapter.deleteCar(0);
        int statusCode = response.getStatusCode();
        assertEquals("Expected status 404 for non-existing car, but got: " + statusCode, statusCode,
                404);

    }

    @Test(testName = "AT.03.26.Запрос несуществующего авто с помощью API",
            description = "Запрос автомобиля с несуществующим id GET /car/{carId}",
            priority = 2,
            groups = {"Negative", "E2E", "Regression", "Smoke"},
            enabled = true)
    @Description("Запрос автомобиля с несуществующим id GET /car/{carId}")
    @Story("API_Cars")
    @Severity(SeverityLevel.NORMAL) // <--- Теперь компилятор видит NORMAL
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    @Owner("Permyakov Egor")
    public void checkNegativeGetCar() {
        log.info("Get all cars to calculate non-existing ID");
        Response response = CarAdapter.getNegativeCar(0);
        int statusCode = response.getStatusCode();
        assertEquals("При запросе с несуществующим id ошибка должна быть: " + statusCode, statusCode,
                404);

    }

    @Test(testName = "AT.03.26.Изменение несуществующего авто с помощью API",
            description = "Изменение автомобиля с несуществующим id GET /car/{carId}",
            priority = 2,
            groups = {"Negative", "E2E", "Regression", "Smoke"},
            enabled = true)
    @Description("Изменение автомобиля с несуществующим id GET /car/{carId}")
    @Story("API_Cars")
    @Severity(SeverityLevel.NORMAL) // <--- Теперь компилятор видит NORMAL
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    @Owner("Permyakov Egor")
    public void checkNegativeUpdateCar() {
        log.info("Get all cars to calculate non-existing ID");
        Response response = CarAdapter.NegativeUpdateCar(0, rqUpdateCar);
        int statusCode = response.getStatusCode();
        assertEquals("При запросе с несуществующим id ошибка должна быть: " + statusCode, statusCode,
                404);

    }

    @DataProvider(name = "negativeCarUpdateData")
    public Object[][] negativeCarUpdateData() {
        return new Object[][] {
                {
                        UpdateCarRq.builder().engineType("Diesel").model("Grand Cherokee").mark("").price(7999.55).
                                build(), 400 //пустая марка
                }, {
                        UpdateCarRq.builder().engineType("Diesel").model("").mark("Jeep").price(7999.55).
                                build(), 400 //пустая модель
                }, {
                        UpdateCarRq.builder().engineType("Diesel").model("Grand Cherokee").mark("Jeep").price(-100.0).
                                build(), 400 //отрицательная сумма
                }, {
                        UpdateCarRq.builder().engineType("Benzin").model("Grand Cherokee").mark("Jeep").price(7999.55).
                                build(), 400 //неверный тип двигателя
                }, {
                        UpdateCarRq.builder().engineType("").model("Grand Cherokee").mark("Jeep").price(7999.55).
                                build(), 400 //пустой тип двигателя
                }
        };
    }

    @Test(testName = "АТ.03.27.Проверка негативного обновления авто через API",
            description = "API проверка обновления авто POST /car с невалидным телом",
            priority = 2,
            groups = {"Negative", "E2E", "Regression", "Smoke"},
            enabled = true,
            dataProvider = "negativeCarUpdateData")
    @Description("API проверка обновления авто POST /car с невалидным телом")
    @Story("API Cars")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    @Owner("Permyakov Egor")
    public void checkNegativeUpdateCarBrokeBody(UpdateCarRq request, int expectedStatusCode) {
        log.info("Running negative test. Mark: '{}', Model: '{}', Price: {}",
                request.getMark(), request.getModel(), request.getPrice());
        //Находим все авто и вытаскиваем первый существующий id
        Response response = null;
        List<GetCarRs> cars = CarAdapter.getAllCar();
        Integer firstId = cars.get(0).getId();
        response = CarAdapter.updateCarRaw(request, firstId);
        assertEquals(response.getStatusCode(), expectedStatusCode);
    }

    @Test(testName = "АТ.03.28.Получение авто конкретного пользователя",
            description = "API проверка получения авто конкретного пользователя GET /user/{userId}/cars",
            priority = 1,
            groups = {"Positive", "E2E", "Regression", "Smoke"},
            enabled = true)
    @Description("API проверка получения авто конкретного пользователя GET /user/{userId}/cars")
    @Story("API Cars")
    @Severity(SeverityLevel.CRITICAL)
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    @Owner("Permyakov Egor")
    public void checkUserCar() {
        log.info("");
        List<GetUserRs> users = CarAdapter.getAllUser();
        Integer firstId = users.get(0).getId();
        Response response = CarAdapter.getUserCar(firstId);
        int statusCode = response.getStatusCode();
        softAssert.assertEquals(statusCode,
                200, "Метод должен был закончиться с кодом 200" + statusCode);
        String bodyString = response.getBody().asString();
        softAssert.assertFalse(bodyString == null || bodyString.trim().isEmpty(),
                "Response body is empty or null for user " + firstId);
        softAssert.assertAll();
    }

    @Test(testName = "AT.03.29.Запрос несуществующего пользователя с авто с помощью API",
            description = "Запрос автомобиля несуществующего пользователя GET /user/{userId}/cars",
            priority = 2,
            groups = {"Negative", "E2E", "Regression", "Smoke"},
            enabled = true)
    @Description("Запрос автомобиля несуществующего пользователя GET /user/{userId}/cars")
    @Story("API_Cars")
    @Severity(SeverityLevel.NORMAL) // <--- Теперь компилятор видит NORMAL
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    @Owner("Permyakov Egor")
    public void checkNegativeUserCar() {
        log.info("Get all cars user's to calculate non-existing ID");
        Response response = CarAdapter.getUserCar(0);
        int statusCode = response.getStatusCode();
        assertEquals("При запросе с несуществующим id ошибка должна быть: " + statusCode, statusCode,
                404);

    }
}
