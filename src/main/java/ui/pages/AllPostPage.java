package ui.pages;

import ui.dto.*;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

/*
Создать пользователя -> получить ID
Пополнить пользователю баланс
Создать автомобиль -> получить ID
Купить авто пользователем
Продать авто пользователем
Создать дом -> получить ID
Заселить пользователя в дом
Проверить: пользователь отображается в жильцах дома
Выселить пользователя из дома
 */
public class AllPostPage extends BasePage {

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
    @Step("Открытие страницы 'All POST'")
    public AllPostPage openPage() {
        log.info("Opening 'All POST' page");
        String targetUrl = BASE_URL + "/#/create/all";
        // Пытаемся открыть страницу до 3 раз
        for (int attempt = 1; attempt <= 3; attempt++) {
            log.info("Attempt {} to open page: {}", attempt, targetUrl);
            com.codeborne.selenide.WebDriverRunner.getWebDriver().get(targetUrl);
            // Ждём появления элемента формы
            try {
                $x(INPUT_USER_FIRST_NAME).shouldBe(Condition.visible, java.time.Duration.ofSeconds(15));
                log.info("Page loaded successfully on attempt {}", attempt);
                return this;
            } catch (Exception e) {
                log.warn("Attempt {} failed, retrying...", attempt);
                if (attempt == 3) {
                    throw e; // На последней попытке пробрасываем исключение
                }
                sleep(1000);
            }
        }
        return this;
    }

    @Step("Проверка открытия страницы 'All POST'.")
    public AllPostPage isPageOpened() {
        log.info("Checking 'All POST' page is loaded");
        // Если openPage() уже отработал - элемент уже виден, логируем URL для отладки
        String currentUrl = com.codeborne.selenide.WebDriverRunner.driver().url();
        log.info("Current URL after load: {}", currentUrl);
        // Дополнительная проверка: если элемент не виден - открываем заново
        if (!$x(INPUT_USER_FIRST_NAME).is(Condition.visible)) {
            log.info("Form not visible, reopening page...");
            openPage();
        }
        return this;
    }

    @Step("Создание пользователя: {0}")
    public AllPostPage createUser(User user) {
        log.info("Creating user: {}", user);

        $x(USER_BLOCK).shouldBe(visible, java.time.Duration.ofSeconds(20));
        sleep(1000);
        //Selenide-подход для React: click() + sendKeys() + ожидание значения
        // Заполняем firstName
        SelenideElement firstNameField = $x(INPUT_USER_FIRST_NAME);
        firstNameField
                .shouldBe(Condition.interactable, java.time.Duration.ofSeconds(15))
                .click();  // фокусируемся на поле для react
        firstNameField.clear();
        firstNameField.sendKeys(user.getFirstName());
        firstNameField.shouldHave(Condition.value(user.getFirstName()), java.time.Duration.ofSeconds(5));

        // Заполняем lastName
        SelenideElement lastNameField = $x(INPUT_USER_LAST_NAME);
        lastNameField
                .shouldBe(Condition.interactable, java.time.Duration.ofSeconds(10))
                .click();
        lastNameField.clear();
        lastNameField.sendKeys(user.getLastName());
        lastNameField.shouldHave(Condition.value(user.getLastName()), java.time.Duration.ofSeconds(5));

        // Заполняем age
        SelenideElement ageField = $x(INPUT_USER_AGE);
        ageField
                .shouldBe(Condition.interactable, java.time.Duration.ofSeconds(10))
                .click();
        ageField.clear();
        ageField.sendKeys(String.valueOf(user.getAge()));
        ageField.shouldHave(Condition.value(String.valueOf(user.getAge())), java.time.Duration.ofSeconds(5));

        // Выбор пола
        if ("MALE".equalsIgnoreCase(user.getSex())) {
            $x(RADIO_USER_MALE)
                    .shouldBe(Condition.interactable, java.time.Duration.ofSeconds(10))
                    .click();
        } else {
            $x(RADIO_USER_FEMALE)
                    .shouldBe(Condition.interactable, java.time.Duration.ofSeconds(10))
                    .click();
        }

        // Заполняем money
        SelenideElement moneyField = $x(INPUT_USER_MONEY);
        moneyField
                .shouldBe(Condition.interactable, java.time.Duration.ofSeconds(10))
                .click();
        moneyField.clear();
        moneyField.sendKeys(String.valueOf((long) user.getMoney()));
        moneyField.shouldHave(Condition.value(String.valueOf((long) user.getMoney())), java.time.Duration.ofSeconds(5));

        // Логирование значений перед отправкой формы
        log.info("--Верификация полей в форме перед отправкой --");
        log.info("First name: '{}'", firstNameField.getValue());
        log.info("Last name: '{}'", lastNameField.getValue());
        log.info("Age: '{}'", ageField.getValue());
        log.info("Money: '{}'", moneyField.getValue());

        // Клик по кнопке
        $x(BTN_USER_PUSH)
                .shouldBe(Condition.interactable, java.time.Duration.ofSeconds(15))
                .click();

        log.info("Clicked Push to API");

        // Ожидаем успешный статус
        $x(STATUS_USER).shouldHave(Condition.text("201"), java.time.Duration.ofSeconds(30));

        return this;
    }

    @Step("Получение сгенерированного ID пользователя")
    public String getGeneratedUserId() {
        String text = $x(NEW_ID_USER).getText();
        // Парсим "New user ID: 12345" в "12345"
        return text.replaceAll("[^0-9]", "");
    }

    @Step("Пополнение баланса пользователя {0} на сумму {1}")
    public AllPostPage addMoneyToUser(String userId, double amount) {
        log.info("Adding money {} to user {}", amount, userId);
        SelenideElement userIdField = $x(INPUT_MONEY_USER_ID);
        SelenideElement amountField = $x(INPUT_MONEY_AMOUNT);

        userIdField.shouldBe(Condition.interactable, java.time.Duration.ofSeconds(10))
                .click();
        userIdField.clear();
        userIdField.sendKeys(userId);
        userIdField.shouldHave(Condition.value(userId), java.time.Duration.ofSeconds(5));

        amountField.shouldBe(Condition.interactable, java.time.Duration.ofSeconds(10))
                .click();
        amountField.clear();
        amountField.sendKeys(String.valueOf((long) amount));
        amountField.shouldHave(Condition.value(String.valueOf((long) amount)), java.time.Duration.ofSeconds(5));

        $x(BTN_MONEY_PUSH).shouldBe(Condition.interactable, java.time.Duration.ofSeconds(10)).click();

        $x(STATUS_MONEY).shouldHave(Condition.text("200"), java.time.Duration.ofSeconds(30));
        log.info("Money added successfully");
        return this;
    }

    @Step("Получение текущего баланса пользователя")
    public String getCurrentBalance() {
        String text = $x(MONEY_BALANCE).getText();
        log.info("Current balance: {}", text);
        return text;
    }

    @Step("Создание автомобиля: {0}")
    public AllPostPage createCar(Car car) {
        log.info("Creating car: {}", car);
        SelenideElement engineField = $x(INPUT_CAR_ENGINE);
        SelenideElement markField = $x(INPUT_CAR_MARK);
        SelenideElement modelField = $x(INPUT_CAR_MODEL);
        SelenideElement priceField = $x(INPUT_CAR_PRICE);

        engineField.shouldBe(Condition.interactable, java.time.Duration.ofSeconds(10))
                .click();
        engineField.clear();
        engineField.sendKeys(car.getEngineType());
        engineField.shouldHave(Condition.value(car.getEngineType()), java.time.Duration.ofSeconds(5));

        markField.shouldBe(Condition.interactable, java.time.Duration.ofSeconds(10))
                .click();
        markField.sendKeys(car.getMark());
        markField.shouldHave(Condition.value(car.getMark()), java.time.Duration.ofSeconds(5));

        modelField.shouldBe(Condition.interactable, java.time.Duration.ofSeconds(10))
                .click();
        modelField.sendKeys(car.getModel());
        modelField.shouldHave(Condition.value(car.getModel()), java.time.Duration.ofSeconds(5));

        priceField.shouldBe(Condition.interactable, java.time.Duration.ofSeconds(10))
                .click();
        priceField.sendKeys(car.getPrice());
        priceField.shouldHave(Condition.value(car.getPrice()), java.time.Duration.ofSeconds(5));

        $x(BTN_CAR_PUSH).shouldBe(Condition.interactable, java.time.Duration.ofSeconds(10)).click();

        $x(STATUS_CAR).shouldHave(Condition.text("201"), java.time.Duration.ofSeconds(30));
        log.info("Car created successfully");
        return this;
    }

    @Step("Получение сгенерированного ID автомобиля")
    public String getGeneratedCarId() {
        String text = $x(NEW_ID_CAR).getText();
        return text.replaceAll("[^0-9]", "");
    }

    @Step("Покупка автомобиля {0} пользователем {1}")
    public AllPostPage buyCar(String userId, String carId) {
        log.info("User {} buying car {}", userId, carId);
        SelenideElement userIdField = $x(INPUT_TRADE_USER_ID);
        SelenideElement carIdField = $x(INPUT_TRADE_CAR_ID);

        userIdField.shouldBe(Condition.interactable, java.time.Duration.ofSeconds(10))
                .click();
        userIdField.clear();
        userIdField.sendKeys(userId);
        userIdField.shouldHave(Condition.value(userId), java.time.Duration.ofSeconds(5));

        carIdField.shouldBe(Condition.interactable, java.time.Duration.ofSeconds(10))
                .click();
        carIdField.clear();
        carIdField.sendKeys(carId);
        carIdField.shouldHave(Condition.value(carId), java.time.Duration.ofSeconds(5));

        $x(RADIO_BUY).shouldBe(Condition.interactable, java.time.Duration.ofSeconds(10)).click();

        $x(BTN_TRADE_PUSH).shouldBe(Condition.interactable, java.time.Duration.ofSeconds(10)).click();

        $x(STATUS_TRADE).shouldHave(Condition.text("200"), java.time.Duration.ofSeconds(30));
        log.info("Car purchased successfully");
        return this;
    }

    @Step("Продажа автомобиля {0} пользователем {1}")
    public AllPostPage sellCar(String userId, String carId) {
        log.info("User {} selling car {}", userId, carId);
        SelenideElement userIdField = $x(INPUT_TRADE_USER_ID);
        SelenideElement carIdField = $x(INPUT_TRADE_CAR_ID);

        userIdField.shouldBe(Condition.interactable, java.time.Duration.ofSeconds(10))
                .click();
        userIdField.clear();
        userIdField.sendKeys(userId);
        userIdField.shouldHave(Condition.value(userId), java.time.Duration.ofSeconds(5));

        carIdField.shouldBe(Condition.interactable, java.time.Duration.ofSeconds(10))
                .click();
        carIdField.clear();
        carIdField.sendKeys(carId);
        carIdField.shouldHave(Condition.value(carId), java.time.Duration.ofSeconds(5));

        $x(RADIO_SELL).shouldBe(Condition.interactable, java.time.Duration.ofSeconds(10)).click();

        $x(BTN_TRADE_PUSH).shouldBe(Condition.interactable, java.time.Duration.ofSeconds(10)).click();

        $x(STATUS_TRADE).shouldHave(Condition.text("200"), java.time.Duration.ofSeconds(30));
        log.info("Car sold successfully");
        return this;
    }

    @Step("Создание дома: {0}")
    public AllPostPage createHouse(House house) {
        log.info("Creating house: {}", house);

        SelenideElement floorsField = $x(INPUT_HOUSE_FLOORS);
        SelenideElement priceField = $x(INPUT_HOUSE_PRICE);

        floorsField.shouldBe(Condition.interactable, java.time.Duration.ofSeconds(10))
                .click();
        floorsField.clear();
        floorsField.sendKeys(String.valueOf(house.getFloors()));
        floorsField.shouldHave(Condition.value(String.valueOf(house.getFloors())), java.time.Duration.ofSeconds(5));

        priceField.shouldBe(Condition.interactable, java.time.Duration.ofSeconds(10))
                .click();
        priceField.clear();
        priceField.sendKeys(String.valueOf((long) house.getPrice()));
        priceField.shouldHave(Condition.value(String.valueOf((long) house.getPrice())), java.time.Duration.ofSeconds(5));

        // Парковки
        SelenideElement parking1 = $x(INPUT_PARKING_1);
        parking1.shouldBe(Condition.interactable, java.time.Duration.ofSeconds(10))
                .click();
        parking1.clear();
        parking1.sendKeys(String.valueOf(house.getParkingWarmCovered().getPlacesCount()));

        SelenideElement parking2 = $x(INPUT_PARKING_2);
        parking2.shouldBe(Condition.interactable, java.time.Duration.ofSeconds(10))
                .click();
        parking2.clear();
        parking2.sendKeys(String.valueOf(house.getParkingWarmNotCovered().getPlacesCount()));

        SelenideElement parking3 = $x(INPUT_PARKING_3);
        parking3.shouldBe(Condition.interactable, java.time.Duration.ofSeconds(10))
                .click();
        parking3.clear();
        parking3.sendKeys(String.valueOf(house.getParkingColdCovered().getPlacesCount()));

        SelenideElement parking4 = $x(INPUT_PARKING_4);
        parking4.shouldBe(Condition.interactable, java.time.Duration.ofSeconds(10))
                .click();
        parking4.clear();
        parking4.sendKeys(String.valueOf(house.getParkingColdNotCovered().getPlacesCount()));

        $x(BTN_HOUSE_PUSH).shouldBe(Condition.interactable, java.time.Duration.ofSeconds(10)).click();

        $x(STATUS_HOUSE).shouldHave(Condition.text("201"), java.time.Duration.ofSeconds(30));
        log.info("House created successfully");
        return this;
    }

    @Step("Получение сгенерированного ID дома")
    public String getGeneratedHouseId() {
        String text = $x(NEW_ID_HOUSE).getText();
        return text.replaceAll("[^0-9]", "");
    }

    // ========== Заселение/выселение ==========
    @Step("Заселение пользователя {0} в дом {1}")
    public AllPostPage settleUserInHouse(String userId, String houseId) {
        log.info("Settling user {} in house {}", userId, houseId);

        SelenideElement userIdField = $x(INPUT_SETTLE_USER_ID);
        SelenideElement houseIdField = $x(INPUT_SETTLE_HOUSE_ID);

        userIdField.shouldBe(Condition.interactable, java.time.Duration.ofSeconds(10))
                .click();
        userIdField.clear();
        userIdField.sendKeys(userId);
        userIdField.shouldHave(Condition.value(userId), java.time.Duration.ofSeconds(5));

        houseIdField.shouldBe(Condition.interactable, java.time.Duration.ofSeconds(10))
                .click();
        houseIdField.clear();
        houseIdField.sendKeys(houseId);
        houseIdField.shouldHave(Condition.value(houseId), java.time.Duration.ofSeconds(5));

        $x(RADIO_SETTLE).shouldBe(Condition.interactable, java.time.Duration.ofSeconds(10)).click();

        $x(BTN_SETTLE_PUSH).shouldBe(Condition.interactable, java.time.Duration.ofSeconds(10)).click();

        $x(STATUS_SETTLE).shouldHave(Condition.text("200"), java.time.Duration.ofSeconds(30));
        log.info("User settled successfully");
        return this;
    }

    @Step("Выселение пользователя {0} из дома {1}")
    public AllPostPage evictUserFromHouse(String userId, String houseId) {
        log.info("Evicting user {} from house {}", userId, houseId);

        SelenideElement userIdField = $x(INPUT_SETTLE_USER_ID);
        SelenideElement houseIdField = $x(INPUT_SETTLE_HOUSE_ID);

        userIdField.shouldBe(Condition.interactable, java.time.Duration.ofSeconds(10))
                .click();
        userIdField.clear();
        userIdField.sendKeys(userId);
        userIdField.shouldHave(Condition.value(userId), java.time.Duration.ofSeconds(5));

        houseIdField.shouldBe(Condition.interactable, java.time.Duration.ofSeconds(10))
                .click();
        houseIdField.clear();
        houseIdField.sendKeys(houseId);
        houseIdField.shouldHave(Condition.value(houseId), java.time.Duration.ofSeconds(5));

        $x(RADIO_EVICT).shouldBe(Condition.interactable, java.time.Duration.ofSeconds(10)).click();

        $x(BTN_SETTLE_PUSH).shouldBe(Condition.interactable, java.time.Duration.ofSeconds(10)).click();

        $x(STATUS_SETTLE).shouldHave(Condition.text("200"), java.time.Duration.ofSeconds(30));
        log.info("User evicted successfully");
        return this;
    }
}