package ui.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import ui.dto.House;
import ui.wrappers.InputHousesCreateNewOneTable;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class HousesCreateNewPage extends BasePage{

    private final SelenideElement BUTTON_PUSH_TO_API
            = $x("//button[@class='tableButton btn btn-primary']");
    private final SelenideElement STATUS_CODE_MESSAGE
            = $x("//button[@disabled and contains(@class, 'status')]");
    private final SelenideElement NEW_HOUSE_ID
            = $x("//button[@disabled and contains(@class, 'newId')]");

    @Override
    public HousesCreateNewPage openPage() {
        log.info("Opening the 'HousesCreateNewPage' start page");
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
}