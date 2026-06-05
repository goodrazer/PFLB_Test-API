package pages;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class AllPostPage extends BasePage{

    //Локаторы для блока User
    private static final String USER_BLOCK = "//section[@class='workspace']/div/div[1]/table";
    private static final String INPUT_USER_FIRST_NAME = USER_BLOCK + "//input[@id='first_name_send']";
    private static final String INPUT_USER_AGE = USER_BLOCK + "//input[@id='age_send']";
    private static final String RADIO_USER_MALE = USER_BLOCK + "//input[@name='sex_send' and @value='MALE']";
    private static final String RADIO_USER_FEMALE = USER_BLOCK + "//input[@name='sex_send' and @value='FEMALE']";
    private static final String INPUT_USER_MONEY = USER_BLOCK + "//input[@id='money_send']";
    private static final String BTN_USER_PUSH = USER_BLOCK +
            "//following-sibling::div/button[contains(., 'PUSH') and contains(., 'API')]";
    private static final String STATUS_USER = USER_BLOCK +
            "//following-sibling::div/button[contains(@class,'status')]";
    private static final String NEW_ID_USER = USER_BLOCK +
            "//following-sibling::div/button[contains(@class,'newId')]";

    //Локаторы для блока House
    private static final String HOUSE_BLOCK = "//section[@class='workspace']/div/div[6]/table[1]";
    private static final String INPUT_HOUSE_FLOORS = HOUSE_BLOCK + "//input[@id='floor_send']";
    private static final String INPUT_HOUSE_PRICE = HOUSE_BLOCK + "//input[@id='price_send']";
    private static final String INPUT_PARKING_1 = "//input[@id='parking_first_send']";
    private static final String INPUT_PARKING_2 = "//input[@id='parking_second_send']";
    private static final String INPUT_PARKING_3 = "//input[@id='parking_third_send']";
    private static final String INPUT_PARKING_4 = "//input[@id='parking_fourth_send']";
    private static final String BTN_HOUSE_PUSH = HOUSE_BLOCK +
            "//following-sibling::div/button[contains(., 'PUSH') and contains(., 'API')]";
    private static final String STATUS_HOUSE = HOUSE_BLOCK +
            "//following-sibling::div/button[contains(@class,'status')]";
    private static final String NEW_ID_HOUSE = HOUSE_BLOCK +
            "//following-sibling::div/button[contains(@class,'newId')]";

    public AllPostPage() {super();}

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


}
