package ui.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class AllPostPage extends BasePage {

    //Локаторы для дропдаунов Users, Cars, Houses:
    //Дропдаун Users:
    private final SelenideElement DROPDOWN_USERS = $("#basic-nav-dropdown");
    //Опции дропдауна Users:
    private final SelenideElement DROPDOWN_USERS_ITEM_READ_ALL = $("a[href='#/read/users']");
    private final SelenideElement DROPDOWN_USERS_ITEM_READ_USER_WITH_CARS = $("a[href='#/read/userInfo']");
    private final SelenideElement DROPDOWN_USERS_ITEM_CREATE_NEW = $("a[href='#/create/user']");
    private final SelenideElement DROPDOWN_USERS_ITEM_ADD_MONEY = $("a[href='#/update/users/plusMoney']");
    private final SelenideElement DROPDOWN_USERS_ITEM_BUY_OR_SELL_CAR = $("a[href='#/update/users/buyCar']");
    private final SelenideElement DROPDOWN_USERS_ITEM_SETTLE_TO_HOUSE = $("a[href='#/update/houseAndUser']");
    private final SelenideElement DROPDOWN_USERS_ITEM_ISSUE_A_LOAN = $("a[href='#/update/Issue_A_Loan']");
    //Дропдаун Cars:
    private final SelenideElement DROPDOWN_CARS = $("basic-nav-dropdown");
    //Опции дропдауна Users:
    private final SelenideElement DROPDOWN_CARS_ITEM_READ_ALL = $("a[href='#/read/cars']");
    private final SelenideElement DROPDOWN_CARS_ITEM_CREATE_NEW = $("a[href='#/create/cars']");
    private final SelenideElement DROPDOWN_CARS_ITEM_BUY_OR_SELL_CAR = $("a[href='#/update/users/buyCar']");
    //Дропдаун Houses:
    private final SelenideElement DROPDOWN_HOUSES = $("#basic-nav-dropdown");
    //Опции дропдауна Users:
    private final SelenideElement DROPDOWN_HOUSES_ITEM_READ_ALL = $("a[href='#/read/houses']");
    private final SelenideElement DROPDOWN_HOUSES_READ_ONE_BY_ID = $("a[href='#/read/house']");
    private final SelenideElement DROPDOWN_HOUSES_CREATE_NEW = $("a[href='#/create/house']");
    private final SelenideElement DROPDOWN_HOUSES_SETTLE_OR_EVICT_USER = $("a[href='#/update/houseAndUser']");
    //Локатор первого поля 'ID will be generated' таблицы открытой по дропдауну Users --> Create new:
    private final SelenideElement TABLE_FIELD_CREATE_NEW_FIELD_ID_WILL_BE_GENERATED =
            $(withText("ID will be generated"));

    //Локаторы кнопок для отправки, получения статуса и получения ID
    private static final String BTN_PUSH =
            "//following-sibling::div/button[contains(., 'PUSH') and contains(., 'API')]";
    private static final String BTN_STATUS =
            "//following-sibling::div/button[contains(@class,'status')]";
    private static final String BTN_ID =
            "//following-sibling::div/button[contains(@class,'newId')]";

    //Локаторы для блока User (1-ая таблица)
    private static final String USER_BLOCK = "//section[@class='workspace']/div/div[1]/table";
    private static final String INPUT_USER_FIRST_NAME = USER_BLOCK + "//input[@id='first_name_send']";
    private static final String INPUT_USER_AGE = USER_BLOCK + "//input[@id='age_send']";
    private static final String RADIO_USER_MALE = USER_BLOCK + "//input[@name='sex_send' and @value='MALE']";
    private static final String RADIO_USER_FEMALE = USER_BLOCK + "//input[@name='sex_send' and @value='FEMALE']";
    private static final String INPUT_USER_MONEY = USER_BLOCK + "//input[@id='money_send']";
    private static final String BTN_USER_PUSH = USER_BLOCK + BTN_PUSH;
    private static final String STATUS_USER = USER_BLOCK + BTN_STATUS;
    private static final String NEW_ID_USER = USER_BLOCK + BTN_ID;

    // Локаторы для блока Add Money (2-ая таблица)
    private static final String MONEY_BLOCK = "//section[@class='workspace']/div/div[2]/table";
    private static final String INPUT_MONEY_USER_ID = MONEY_BLOCK + "//input[@id='id_send']";
    private static final String INPUT_MONEY_AMOUNT = MONEY_BLOCK + "//input[@id='money_send']";
    private static final String BTN_MONEY_PUSH = MONEY_BLOCK + BTN_PUSH;
    private static final String STATUS_MONEY = MONEY_BLOCK + BTN_STATUS;
    private static final String MONEY_BALANCE = MONEY_BLOCK +
            "//following-sibling::div/button[contains(@class,'money')]";

    // Локаторы для блока Settle/Evict (3-я таблица)
    private static final String SETTLE_BLOCK = "//section[@class='workspace']/div/div[3]/table";
    private static final String INPUT_SETTLE_USER_ID = SETTLE_BLOCK + "//input[@id='id_send']";
    private static final String INPUT_SETTLE_HOUSE_ID = SETTLE_BLOCK + "//input[@id='house_send']";
    private static final String RADIO_SETTLE = SETTLE_BLOCK + "//input[@name='settleOrEvict' and @value='settle']";
    private static final String RADIO_EVICT = SETTLE_BLOCK + "//input[@name='settleOrEvict' and @value='evict']";
    private static final String BTN_SETTLE_PUSH = SETTLE_BLOCK + BTN_PUSH;
    private static final String STATUS_SETTLE = SETTLE_BLOCK + BTN_STATUS;

    // Локаторы для блока Buy/Sell Car (4-ая таблица)
    private static final String CAR_TRADE_BLOCK = "//section[@class='workspace']/div/div[4]/table";
    private static final String INPUT_TRADE_USER_ID = CAR_TRADE_BLOCK + "//input[@id='id_send']";
    private static final String INPUT_TRADE_CAR_ID = CAR_TRADE_BLOCK + "//input[@id='car_send']";
    private static final String RADIO_BUY = CAR_TRADE_BLOCK + "//input[@name='settleOrEvict' and @value='buyCar']";
    private static final String RADIO_SELL = CAR_TRADE_BLOCK + "//input[@name='settleOrEvict' and @value='sellCar']";
    private static final String BTN_TRADE_PUSH = CAR_TRADE_BLOCK + BTN_PUSH;
    private static final String STATUS_TRADE = CAR_TRADE_BLOCK + BTN_STATUS;

    // Локаторы для блока Car (5-ая таблица)
    private static final String CAR_BLOCK = "//section[@class='workspace']/div/div[5]/table";
    private static final String INPUT_CAR_ENGINE = CAR_BLOCK + "//input[@id='car_engine_type_send']";
    private static final String INPUT_CAR_MARK = CAR_BLOCK + "//input[@id='car_mark_send']";
    private static final String INPUT_CAR_MODEL = CAR_BLOCK + "//input[@id='car_model_send']";
    private static final String INPUT_CAR_PRICE = CAR_BLOCK + "//input[@id='car_price_send']";
    private static final String BTN_CAR_PUSH = CAR_BLOCK + BTN_PUSH;
    private static final String STATUS_CAR = CAR_BLOCK + BTN_STATUS;
    private static final String NEW_ID_CAR = CAR_BLOCK + BTN_ID;

    //Локаторы для блока House (6-ая таблица)
    private static final String HOUSE_BLOCK = "//section[@class='workspace']/div/div[6]/table[1]";
    private static final String INPUT_HOUSE_FLOORS = HOUSE_BLOCK + "//input[@id='floor_send']";
    private static final String INPUT_HOUSE_PRICE = HOUSE_BLOCK + "//input[@id='price_send']";
    private static final String INPUT_PARKING_1 = "//input[@id='parking_first_send']";
    private static final String INPUT_PARKING_2 = "//input[@id='parking_second_send']";
    private static final String INPUT_PARKING_3 = "//input[@id='parking_third_send']";
    private static final String INPUT_PARKING_4 = "//input[@id='parking_fourth_send']";
    private static final String BTN_HOUSE_PUSH = HOUSE_BLOCK + BTN_PUSH;
    private static final String STATUS_HOUSE = HOUSE_BLOCK + BTN_STATUS;
    private static final String NEW_ID_HOUSE = HOUSE_BLOCK + BTN_ID;

    public AllPostPage() {
        super();
    }

    @Override
    @Step("Открытие страницы 'All POST'.")
    public AllPostPage openPage() {
        log.info("Opening 'All POST' page");
        open(BASE_URL + "/#/create/all");
        return this;
    }

    @Step("Проверка открытия страницы 'All POST'.")
    public AllPostPage isPageOpened() {
        log.info("Checking 'All POST' page is loaded");
        String currentUrl = com.codeborne.selenide.WebDriverRunner.driver().url();
        if (!currentUrl.contains("#/create/all")) {
            throw new AssertionError("Expected URL to contain '#/create/all', but got: " + currentUrl);
        }
        return this;
    }

    @Step("Раскрытие выпадающего списка 'Users'")
    public AllPostPage clickUsersButton() {
        log.info("Click the 'Users' button");
        DROPDOWN_USERS.click();
        return this;
    }

    @Step("Выбор опции 'Read all' из выпадающего списка 'Users'")
    public AllPostPage clickReadAllButton() {
        log.info("Select the 'Read all' option from the 'Users' drop-down list");
        DROPDOWN_USERS_ITEM_READ_ALL.click();
        return this;
    }

    @Step("Выбор опции 'Create new' из выпадающего списка 'Users'")
    public AllPostPage clickCreateNewButton() {
        log.info("Select the 'Create new' option from the 'Users' drop-down list");
        DROPDOWN_USERS_ITEM_CREATE_NEW.shouldBe(visible).click();
        return this;
    }

    @Step("Отображение элемента 'ID will be generated' в таблице 'Create new' из выпадающего списка 'Users'")
    public String getTextElementIDWillBeGenerated() {
        log.info("Displaying the 'ID will be generated' item in the 'Create new' table from the 'Users' drop-down list");
        return TABLE_FIELD_CREATE_NEW_FIELD_ID_WILL_BE_GENERATED.shouldBe(visible).getText();
    }
}