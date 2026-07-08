package tests.api.houses;

import api.models.houses.HouseCreateNewRequest;
import api.models.houses.HouseCreateNewResponse;
import api.models.parking.ParkingRequest;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import tests.api.base.BaseApiTest;
import java.util.List;

public class HousesApiTest extends BaseApiTest {

    @Test(testName = "API.04.01. Создание дома с валидными данными",
            description = "Создание дома с валидными данными POST/house",
            priority = 1,
            groups = {"Positive", "Regression", "Smoke"})
    @Description("Создание дома с валидными данными POST/house")
    @Epic("Epic04_Houses")
    @Feature("Создание дома")
    @Story("Создание дома с валидными данными POST/house")
    @Severity(SeverityLevel.BLOCKER)
    @Link(value = "docs.google", name = "Чек-лист PFLB")
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    @Flaky
    @Owner("Malevaniy Anton")
    public void createNewHouseWithValidDataTest() {
        Response loginResponse = loginAdapter.authorizationApi(validEmail, validPassword);
        String accessToken = loginAdapter.obtainingATokenAPI(loginResponse);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(accessToken,
                "Токен авторизации равен null");
        softAssert.assertFalse(accessToken.trim().isEmpty(),
                "Токен авторизации пустой");
        ParkingRequest expectedParking = ParkingRequest.builder()
                .isCovered(true)
                .isWarm(true)
                .placesCount(24)
                .build();
        HouseCreateNewRequest requestBody = HouseCreateNewRequest.builder()
                .floorCount(5)
                .price(150000.0)
                .parkingPlaces(List.of(expectedParking))
                .build();
        HouseCreateNewResponse actualResponse = houseAdapter.houseCreateNew(requestBody, accessToken);
        softAssert.assertNotNull(actualResponse.getId(),
                "Ошибка!!! ID для созданного дома не сгенерирован!");
        softAssert.assertEquals(actualResponse.getFloorCount(), requestBody.getFloorCount(),
                "Ошибка !!!Количество этажей не совпадает!");
        softAssert.assertEquals(actualResponse.getPrice(), requestBody.getPrice(),
                "Ошибка!!! Стоимость дома не совпадает!");
        softAssert.assertFalse(actualResponse.getParkingPlaces().isEmpty(),
                "Ошибка!!! Список парковок в ответе пуст!");
        ParkingRequest actualParking = actualResponse.getParkingPlaces().get(0);
        softAssert.assertNotNull(actualParking.getId(),
                "Ошибка!!! ID для парковки созданного дома не сгенерирован!");
        softAssert.assertEquals(actualParking.getIsCovered(), expectedParking.getIsCovered(),
                "Ошибка!!! Парковка не крытая!");
        softAssert.assertEquals(actualParking.getIsWarm(), expectedParking.getIsWarm(),
                "Ошибка!!! Парковка не является теплой!");
        softAssert.assertEquals(actualParking.getPlacesCount(), expectedParking.getPlacesCount(),
                "Ошибка!!! Количество парковочных мест не совпадает!");
        softAssert.assertAll();
    }

    @Test(testName = "API.04.02. Создание дома с невалидным токеном",
            description = "Проверка невозможности создания дома без авторизации",
            priority = 2,
            groups = {"Negative", "Regression"})
    @Description("API.04.02.Попытка создания дома с некорректным JWT-токеном POST/house")
    @Epic("Epic04_Houses")
    @Feature("Создание дома")
    @Story("Создание дома с невалидным токеном")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Malevaniy Anton")
    public void createNewHouseWithInvalidTokenTest() {
        String invalidToken = "invalid_token";
        HouseCreateNewRequest requestBody = HouseCreateNewRequest.builder()
                .floorCount(1)
                .price(50000.0)
                .build();
        Response response = houseAdapter.houseCreateNewNotVerifyStatusCode(requestBody, invalidToken);
        response.then().log().ifValidationFails();
        int actualStatusCode = response.getStatusCode();
        Assert.assertEquals(actualStatusCode, 403,
                "Ошибка!!! Сервер вернул некорректный статус-код при запросе с невалидным токеном!");
    }
}
