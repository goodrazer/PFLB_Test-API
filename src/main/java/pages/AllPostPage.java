package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import dto.User;
import io.qameta.allure.Step;
import wrappers.ButtonWrapper;
import wrappers.InputWrapper;
import wrappers.RadioWrapper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class AllPostPage extends BasePage{

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
    private static final String INPUT_USER_LAST_NAME = USER_BLOCK + "//input[@id='last_name_send']";
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

        $x(INPUT_USER_FIRST_NAME).shouldBe(Condition.visible, java.time.Duration.ofSeconds(20));
//        String currentUrl = com.codeborne.selenide.WebDriverRunner.driver().url();
//        if (!currentUrl.contains("#/create/all")) {
//            throw new AssertionError("Expected URL to contain '#/create/all', but got: " + currentUrl);
//        }
        return this;
    }

    @Step("Создание пользователя: {0}")
    public AllPostPage createUser(User user) {
        log.info("Creating user: {}", user);

        $x(USER_BLOCK).shouldBe(visible, java.time.Duration.ofSeconds(20));
        // Получаем каждый элемент
        SelenideElement firstNameField = $x(INPUT_USER_FIRST_NAME);
        SelenideElement lastNameField = $x(INPUT_USER_LAST_NAME);
        SelenideElement ageField = $x(INPUT_USER_AGE);
        SelenideElement moneyField = $x(INPUT_USER_MONEY);
        // Ждём каждое поле
        firstNameField.shouldBe(Condition.interactable, java.time.Duration.ofSeconds(15));
        firstNameField.clear();
        firstNameField.sendKeys(user.getFirstName());
        // Проверяем, что значение записалось
        firstNameField.shouldHave(Condition.value(user.getFirstName()), java.time.Duration.ofSeconds(5));

        lastNameField.shouldBe(Condition.interactable, java.time.Duration.ofSeconds(10));
        lastNameField.clear();
        lastNameField.sendKeys(user.getLastName());
        lastNameField.shouldHave(Condition.value(user.getLastName()), java.time.Duration.ofSeconds(5));

        ageField.shouldBe(Condition.interactable, java.time.Duration.ofSeconds(10));
        ageField.clear();
        ageField.sendKeys(String.valueOf(user.getAge()));
        ageField.shouldHave(Condition.value(String.valueOf(user.getAge())), java.time.Duration.ofSeconds(5));

        new InputWrapper(INPUT_USER_FIRST_NAME).setValue(user.getFirstName());
        new InputWrapper(INPUT_USER_LAST_NAME).setValue(user.getLastName());
        new InputWrapper(INPUT_USER_AGE).setNumberValue(String.valueOf(user.getAge()));

        if ("MALE".equalsIgnoreCase(user.getSex())) {
            SelenideElement maleRadio = $x(RADIO_USER_MALE);
            maleRadio.shouldBe(Condition.interactable, java.time.Duration.ofSeconds(10));
            maleRadio.click();
        } else {
            SelenideElement femaleRadio = $x(RADIO_USER_FEMALE);
            femaleRadio.shouldBe(Condition.interactable, java.time.Duration.ofSeconds(10));
            femaleRadio.click();
        }

        moneyField.shouldBe(Condition.interactable, java.time.Duration.ofSeconds(10));
        moneyField.clear();
        moneyField.sendKeys(String.valueOf((long) user.getMoney()));
        moneyField.shouldHave(Condition.value(String.valueOf((long) user.getMoney())), java.time.Duration.ofSeconds(5));


        //new InputWrapper(INPUT_USER_MONEY).setNumberValue(String.valueOf(user.getMoney()));
        //String moneyStr = String.format(java.util.Locale.US, "%.2f", user.getMoney());
        //new InputWrapper(INPUT_USER_MONEY).setNumberValue(moneyStr);

        //new InputWrapper(INPUT_USER_MONEY).setNumberValue(String.valueOf(10000));

        //new ButtonWrapper(BTN_USER_PUSH).click();
        //$x(STATUS_USER).shouldHave(Condition.text("201"));
        log.info("Page URL before click: {}",
                com.codeborne.selenide.WebDriverRunner.driver().url());

//        SelenideElement pushButton = $x(BTN_USER_PUSH);
//        pushButton.scrollTo();  // Скроллим к кнопке
//        com.codeborne.selenide.Selenide.executeJavaScript("arguments[0].click();", pushButton);

        SelenideElement pushButton = $x(BTN_USER_PUSH);
        pushButton.shouldBe(Condition.interactable, java.time.Duration.ofSeconds(15));
        //pushButton.scrollTo();
        log.info("--Верификация полей в форме перед отправкой --");
        log.info("First name: '{}'", firstNameField.getValue());
        log.info("Last name: '{}'", lastNameField.getValue());
        log.info("Age: '{}'", ageField.getValue());
        log.info("Money: '{}'", moneyField.getValue());

        //String formHtml = $x(USER_BLOCK).getAttribute("outerHTML");
        //log.info("Form HTML snippet:\n{}", formHtml);
        pushButton.click();
        log.info("Clicked Push to API");
        // Кнопки (текст)
        //log.info("Push button text: '{}'", $x(BTN_USER_PUSH).getText());
        //log.info("Status button text: '{}'", $x(STATUS_USER).getText());
        //log.info("New ID button text: '{}'", $x(NEW_ID_USER).getText());

        $x(STATUS_USER).shouldHave(Condition.text("201"), java.time.Duration.ofSeconds(30));
        return this;
    }

    @Step("Получение сгенерированного ID пользователя")
    public String getGeneratedUserId() {
        String text = $x(NEW_ID_USER).getText();
        // Парсим "New user ID: 12345" в "12345"
        return text.replaceAll("[^0-9]", "");
    }
}