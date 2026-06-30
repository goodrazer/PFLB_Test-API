package tests.ui.houses;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import tests.ui.BaseTest;
import ui.dto.houses.House;

public class HousesTest extends BaseTest {

    @Test(testName = "АТ.04/3.12.Проверка создания дома с заполнением обязательных полей:" +
            " {floors} и {price} валидными кредами",
            description = "Проверка создания дома с заполнением обязательных полей {floors} и {price} валидными " +
                    "кредами и получением успешного кода выполнения операции",
            priority = 1,
            groups = {"Positive", "Regression", "Smoke"},
            enabled = true)
    @Description("Проверка создания дома с заполнением обязательных полей {floors} и {price} валидными кредами и " +
            "получением успешного кода выполнения операции")
    @Epic("Epic04_Houses")
    @Feature("Проверка создания дома с заполнением обязательных полей {floors} и {price} валидными кредами")
    @Story("Проверка создания дома с заполнением обязательных полей {floors} и {price} валидными кредами и " +
            "получением успешного кода выполнения операции")
    @Severity(SeverityLevel.BLOCKER)
    @Link(value = "docs.google", name = "Чек-лист PFLB")
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    @Flaky
    @Owner("Malevaniy Anton")
    public void checkingTheCreationOfAHouseByFillingInTheRequiredFieldsWithValidCredits() {
        loginStep.successfulAuthorization(validEmail, validPassword);
        housesCreateNewPage.openPage();
        House house = House.builder()
                .floors(2)
                .price(3000000.00)
                .build();
        housesCreateNewPage.addNewHouse(house)
                .clickButtonPushToApi();
        Selenide.sleep(2000);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(
                housesCreateNewPage.getTheTextOfTheConservationStatusOfANewProperty(),
                "Status: Successfully pushed, code: 201",
                "Ошибка!!! Статус выполнения операции отличается от 'Status: Successfully pushed, code: 201'");
        softAssert.assertTrue(housesCreateNewPage.isVisibleNewHouseID(),
                "Ошибка!!! ID нового объекта недвижимости не отображен на странице!");
        String actualIdText = housesCreateNewPage.getHouseIdText();
        String expectedRegex = "New house ID: \\d+";
        softAssert.assertTrue(actualIdText.matches(expectedRegex),
                String.format("Ошибка: Текст на странице '%s' не соответствует шаблону '%s'!",
                        actualIdText, expectedRegex));
        softAssert.assertAll();
    }

    @Test(testName = "АТ.04/3.13.Проверка создания дома с заполнением всех полей валидными кредами",
            description = "Проверка создания дома с заполнением всех полей валидными кредами " +
                    "(включая необязательные)",
            priority = 1,
            groups = {"Positive", "Regression", "Smoke"},
            enabled = true)
    @Description("Проверка создания дома с заполнением всех полей валидными кредами " +
            "(включая необязательные")
    @Epic("Epic04_Houses")
    @Feature("Проверка создания дома")
    @Story("Проверка создания дома с заполнением всех полей валидными кредами " +
            "(включая необязательные")
    @Severity(SeverityLevel.BLOCKER)
    @Link(value = "docs.google", name = "Чек-лист PFLB")
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    @Flaky
    @Owner("Malevaniy Anton")
    public void checkingTheCreationOfAHouseWithAllFieldsFilledWithValidCredits(){
        loginStep.successfulAuthorization(validEmail, validPassword);
        housesCreateNewPage.openPage();
        House house = House.builder()
                .floors(2)
                .price(3000000.00)
                .hasWarmAndCoveredParkingPlaces(1)
                .hasWarmNotCoveredParkingPlaces(1)
                .hasColdButCoveredParkingPlaces(0)
                .hasColdNotCoveredParkingPlaces(0)
                .build();
        housesCreateNewPage.creatingAHouseWithAllTheParameters(house)
                .clickButtonPushToApi();
        Selenide.sleep(2000);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(
                housesCreateNewPage.getTheTextOfTheConservationStatusOfANewProperty(),
                "Status: Successfully pushed, code: 201",
                "Ошибка!!! Статус выполнения операции отличается от 'Status: Successfully pushed, code: 201'");
        softAssert.assertTrue(housesCreateNewPage.isVisibleNewHouseID(),
                "Ошибка!!! ID нового объекта недвижимости не отображен на странице!");
        String actualIdText = housesCreateNewPage.getHouseIdText();
        String expectedRegex = "New house ID: \\d+";
        softAssert.assertTrue(actualIdText.matches(expectedRegex),
                String.format("Ошибка: Текст на странице '%s' не соответствует шаблону '%s'!",
                        actualIdText, expectedRegex));
        String newHouseID = housesCreateNewPage.getHouseId();
        housesReadOneByIdPage.openPage()
                .fillingInTheHouseInputSearchField(newHouseID)
                .clickButtonRead();
        Selenide.sleep(2000);
        String idFoundHouse = housesReadOneByIdPage.getIdFoundHouse();
        softAssert.assertEquals(idFoundHouse, newHouseID,
                "Ошибка!!! ID созданного объекта недвижимости при поиске через {Houses -> Read One By ID} " +
                        "не совпадает с ожидаемым!");
        softAssert.assertAll();
    }

    @DataProvider(name = "negativeHouseDataProvider")
    public static Object[][] getNegativeHouseMatrix() {
        return new Object[][] {
                {House.builder()
                        .floors(-1)
                        .price(1.00)
                        .hasWarmAndCoveredParkingPlaces(1)
                        .hasWarmNotCoveredParkingPlaces(1)
                        .hasColdButCoveredParkingPlaces(1)
                        .hasColdNotCoveredParkingPlaces(1)
                        .build(), "Введено отрицательное значение в поле 'Floors'"},
                {House.builder()
                        .floors(0)
                        .price(1.00)
                        .hasWarmAndCoveredParkingPlaces(1)
                        .hasWarmNotCoveredParkingPlaces(1)
                        .hasColdButCoveredParkingPlaces(1)
                        .hasColdNotCoveredParkingPlaces(1)
                        .build(), "Введено нулевое значение в поле 'Floors'"},
                {House.builder()
                        .floors(1)
                        .price(-1.00)
                        .hasWarmAndCoveredParkingPlaces(1)
                        .hasWarmNotCoveredParkingPlaces(1)
                        .hasColdButCoveredParkingPlaces(1)
                        .hasColdNotCoveredParkingPlaces(1)
                        .build(), "Введено отрицательное значение в поле 'Price'"},
                {House.builder()
                        .floors(1)
                        .price(1.00)
                        .hasWarmAndCoveredParkingPlaces(-1)
                        .hasWarmNotCoveredParkingPlaces(1)
                        .hasColdButCoveredParkingPlaces(1)
                        .hasColdNotCoveredParkingPlaces(1)
                        .build(), "Введено отрицательное значение в поле 'Warm and Covered Parking'"},
                {House.builder()
                        .floors(1).price(1.00)
                        .hasWarmAndCoveredParkingPlaces(1)
                        .hasWarmNotCoveredParkingPlaces(-1)
                        .hasColdButCoveredParkingPlaces(1)
                        .hasColdNotCoveredParkingPlaces(1)
                        .build(), "Введено отрицательное значение в поле 'Warm and Not Covered Parking'"},
                {House.builder()
                        .floors(1).price(1.00)
                        .hasWarmAndCoveredParkingPlaces(1)
                        .hasWarmNotCoveredParkingPlaces(1)
                        .hasColdButCoveredParkingPlaces(-1)
                        .hasColdNotCoveredParkingPlaces(1)
                        .build(), "Введено отрицательное значение в поле 'Cold and Covered Parking'"},
                {House.builder()
                        .floors(1)
                        .price(1.00)
                        .hasWarmAndCoveredParkingPlaces(1)
                        .hasWarmNotCoveredParkingPlaces(1)
                        .hasColdButCoveredParkingPlaces(1)
                        .hasColdNotCoveredParkingPlaces(-1)
                        .build(), "Введено отрицательное значение в поле 'Cold and Not Covered Parking'"}
        };
    }

    @Test(testName = "АТ.04/4.14.Проверка валидации полей на странице 'Houses/Create new' с получением ошибок " +
            "при вводе невалидных значений",
            description = "Проверка валидации полей на странице 'Houses/Create new' с получением ошибок при вводе " +
                    "невалидных значений",
            dataProvider = "negativeHouseDataProvider",
            priority = 2,
            groups = {"Negative", "Regression"},
            enabled = true)
    @Description("Проверка валидации полей на странице 'Houses/Create new' с получением ошибок при вводе невалидных " +
            "значений")
    @Epic("Epic04_Houses")
    @Feature("Проверка валидации полей на странице 'Houses/Create new'")
    @Story("Проверка валидации полей на странице 'Houses/Create new' с получением ошибок при вводе невалидных значений")
    @Severity(SeverityLevel.CRITICAL)
    @Link(value = "docs.google", name = "Чек-лист PFLB")
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    @Flaky
    @Owner("Malevaniy Anton")
    public void checkingTheValidationOfFieldsOnTheHousesCreateNewPageAndReceivingErrorsWhenEnteringInvalidValues(
            House invalidHouse,
            String status){
        loginStep.successfulAuthorization(validEmail, validPassword);
        housesCreateNewPage.openPage();
        housesCreateNewPage.creatingAHouseWithAllTheParameters(invalidHouse)
                .clickButtonPushToApi();
        Selenide.sleep(2000);
        SoftAssert softAssert = new SoftAssert();
        String statusCodeText = housesCreateNewPage.getTheTextOfTheConservationStatusOfANewProperty();
        softAssert.assertTrue(
                statusCodeText.contains("Status: Invalid input data"),
                String.format("Ошибка!!! Для поля '%s' ожидался текст 'Status: Invalid input data'," +
                        " но на UI отображается: '%s'", status, statusCodeText));
        softAssert.assertAll();
    }
}