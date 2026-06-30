package ui.pages.houses;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import ui.dto.houses.House;
import ui.pages.BasePage;
import ui.wrappers.houses.InputHousesCreateNewOneTable;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class HousesCreateNewPage extends BasePage {

    private final SelenideElement BUTTON_PUSH_TO_API
            = $x("//button[@class='tableButton btn btn-primary']");
    private final SelenideElement STATUS_CODE_MESSAGE
            = $x("//button[@disabled and contains(@class, 'status')]");
    private final SelenideElement NEW_HOUSE_ID
            = $x("//button[@disabled and contains(@class, 'newId')]");
    private final SelenideElement INPUT_HAS_WARM_AND_COVERED_PARKING_PLACES
            = $x("//table[@class='table table table-striped table-bordered table-hover'][2]//input");
    private final SelenideElement INPUT_HAS_WARM_NOT_COVERED_PARKING_PLACES
            = $x("//table[@class='table table table-striped table-bordered table-hover'][3]//input");
    private final SelenideElement INPUT_HAS_COLD_BUT_COVERED_PARKING_PLACES
            = $x("//table[@class='table table table-striped table-bordered table-hover'][4]//input");
    private final SelenideElement INPUT_HAS_COLD_NOT_COVERED_PARKING_PLACES
            = $x("//table[@class='table table table-striped table-bordered table-hover'][5]//input");

    @Override
    @Step("Открытие страницы 'Houses -> Create new'")
    public HousesCreateNewPage openPage() {
        log.info("Opening the 'Houses -> Create new' start page");
        open (BASE_URL + "/#/create/house");
        return this;
    }

    @Step("Создание нового дома с заполнением обязательных полей c использованием dto 'House'")
    public HousesCreateNewPage addNewHouse(House house) {
        log.info("Creating a new house with required fields using dto 'House' {}", house);
        String floorsCount = String.valueOf(house.getFloors());
        String priceValue = String.valueOf(house.getPrice());
        new InputHousesCreateNewOneTable("Floors", "floor_send")
                .writeInputHousesCreateNewOneTable(floorsCount);
        new InputHousesCreateNewOneTable("Price", "price_send")
                .writeInputHousesCreateNewOneTable(priceValue);
        return this;
    }

    @Step("Клик по кнопке 'PUSH_TO_API'")
    public HousesCreateNewPage clickButtonPushToApi() {
        log.info("Click on the 'PUSH_TO_API' button");
        BUTTON_PUSH_TO_API.click();
        return this;
    }

    @Step("Получить текст статуса сохранения нового объекта недвижимости")
    public String getTheTextOfTheConservationStatusOfANewProperty() {
        log.info("Get the text of the conservation status of a new property");
        return STATUS_CODE_MESSAGE.getText();
    }

    @Step("Отображение элемента страницы 'New house ID:******'")
    public boolean isVisibleNewHouseID() {
        log.info("Displaying page element 'New house ID:******'");
        return NEW_HOUSE_ID.is(visible);
    }

    @Step("Получить текст с ID созданного объекта недвижимости")
    public String getHouseIdText() {
        log.info("Checking for the presence of 'New house ID'");
        return NEW_HOUSE_ID.text().trim();
    }

    @Step("Создание нового дома с заполнением всех полей (включая необязательные) c использованием dto 'House'")
    public HousesCreateNewPage creatingAHouseWithAllTheParameters(House house) {
        log.info("Create a new house by filling in all fields (including optional ones) using the dto 'House'");
        String floorsCount = String.valueOf(house.getFloors());
        String priceValue = String.valueOf(house.getPrice());
        String hasWarmAndCoveredParkingPlaces = String.valueOf(house.getHasWarmAndCoveredParkingPlaces());
        String hasWarmNotCoveredParkingPlaces = String.valueOf(house.getHasWarmNotCoveredParkingPlaces());
        String hasColdButCoveredParkingPlaces = String.valueOf(house.getHasColdButCoveredParkingPlaces());
        String hasColdNotCoveredParkingPlaces = String.valueOf(house.getHasColdNotCoveredParkingPlaces());
        new InputHousesCreateNewOneTable("Floors", "floor_send")
                .writeInputHousesCreateNewOneTable(floorsCount);
        new InputHousesCreateNewOneTable("Price", "price_send")
                .writeInputHousesCreateNewOneTable(priceValue);
        INPUT_HAS_WARM_AND_COVERED_PARKING_PLACES.setValue(hasWarmAndCoveredParkingPlaces);
        INPUT_HAS_WARM_NOT_COVERED_PARKING_PLACES.setValue(hasWarmNotCoveredParkingPlaces);
        INPUT_HAS_COLD_BUT_COVERED_PARKING_PLACES.setValue(hasColdButCoveredParkingPlaces);
        INPUT_HAS_COLD_NOT_COVERED_PARKING_PLACES.setValue(hasColdNotCoveredParkingPlaces);
        return this;
    }

    @Step("Получить ID созданного объекта недвижимости")
    public String getHouseId() {
        log.info("Get the ID of the created property");
        return NEW_HOUSE_ID.text().replaceAll("\\D", "");
    }
}