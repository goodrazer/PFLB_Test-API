package ui.pages.allPost;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import ui.dto.cars.Car;
import ui.dto.houses.House;
import ui.dto.users.User;
import ui.pages.base.BasePage;
import ui.wrappers.ButtonWrapper;
import ui.wrappers.InputWrapper;
import ui.wrappers.RadioWrapper;
import java.time.Duration;
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
    @Step("Открытие страницы 'All POST'.")
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
        $x(USER_BLOCK).shouldBe(visible, Duration.ofSeconds(20));
        sleep(1000); // Даём React время прикрепить обработчики к полям
        // Используем InputWrapper для ввода данных (React подход clear + sendKeys + ожидание/проверка)
        new InputWrapper(INPUT_USER_FIRST_NAME).setValue(user.getFirstName());
        new InputWrapper(INPUT_USER_LAST_NAME).setValue(user.getLastName());
        new InputWrapper(INPUT_USER_AGE).setValue(String.valueOf(user.getAge()));
        // Выбор пола через RadioWrapper
        if ("MALE".equalsIgnoreCase(user.getSex())) {
            new RadioWrapper(RADIO_USER_MALE).select();
        } else {
            new RadioWrapper(RADIO_USER_FEMALE).select();
        }
        new InputWrapper(INPUT_USER_MONEY).setValue(String.valueOf((long) user.getMoney()));
        // Логирование значений перед отправкой формы
        log.info("--Верификация полей в форме перед отправкой --");
        log.info("First name: '{}'", new InputWrapper(INPUT_USER_FIRST_NAME).getValue());
        log.info("Last name: '{}'", new InputWrapper(INPUT_USER_LAST_NAME).getValue());
        log.info("Age: '{}'", new InputWrapper(INPUT_USER_AGE).getValue());
        log.info("Money: '{}'", new InputWrapper(INPUT_USER_MONEY).getValue());
        // Клик по кнопке и ожидание статуса
        new ButtonWrapper(BTN_USER_PUSH).click();
        log.info("Clicked Push to API");
        new ButtonWrapper(STATUS_USER).shouldHaveText("201");
        return this;
    }

    @Step("Получение сгенерированного ID пользователя")
    public String getGeneratedUserId() {
        String text = $x(NEW_ID_USER).getText();
        // Парсим числа - например "New user ID: 12345" в "12345"
        return text.replaceAll("[^0-9]", "");
    }

    @Step("Пополнение баланса пользователя {0} на сумму {1}")
    public AllPostPage addMoneyToUser(String userId, double amount) {
        log.info("Adding money {} to user {}", amount, userId);
        new InputWrapper(INPUT_MONEY_USER_ID).setValue(userId);
        new InputWrapper(INPUT_MONEY_AMOUNT).setValue(String.valueOf((long) amount));
        new ButtonWrapper(BTN_MONEY_PUSH).click();
        new ButtonWrapper(STATUS_MONEY).shouldHaveText("200");
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
        new InputWrapper(INPUT_CAR_ENGINE).setValue(car.getEngineType());
        new InputWrapper(INPUT_CAR_MARK).setValue(car.getMark());
        new InputWrapper(INPUT_CAR_MODEL).setValue(car.getModel());
        new InputWrapper(INPUT_CAR_PRICE).setValue(car.getPrice());
        new ButtonWrapper(BTN_CAR_PUSH).click();
        new ButtonWrapper(STATUS_CAR).shouldHaveText("201");
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
        new InputWrapper(INPUT_TRADE_USER_ID).setValue(userId);
        new InputWrapper(INPUT_TRADE_CAR_ID).setValue(carId);
        new RadioWrapper(RADIO_BUY).select();
        new ButtonWrapper(BTN_TRADE_PUSH).click();
        new ButtonWrapper(STATUS_TRADE).shouldHaveText("200");
        log.info("Car purchased successfully");
        return this;
    }

    @Step("Продажа автомобиля {0} пользователем {1}")
    public AllPostPage sellCar(String userId, String carId) {
        log.info("User {} selling car {}", userId, carId);
        new InputWrapper(INPUT_TRADE_USER_ID).setValue(userId);
        new InputWrapper(INPUT_TRADE_CAR_ID).setValue(carId);
        new RadioWrapper(RADIO_SELL).select();
        new ButtonWrapper(BTN_TRADE_PUSH).click();
        new ButtonWrapper(STATUS_TRADE).shouldHaveText("200");
        log.info("Car sold successfully");
        return this;
    }

    @Step("Создание дома: {0}")
    public AllPostPage createHouse(House house) {
        log.info("Creating house: {}", house);
        // Основные поля дома
        new InputWrapper(INPUT_HOUSE_FLOORS).setValue(String.valueOf(house.getFloors()));
        new InputWrapper(INPUT_HOUSE_PRICE).setValue(String.valueOf((long) house.getPrice()));
        // Парковки (4 типа)
        new InputWrapper(INPUT_PARKING_1).setValue(String.valueOf(house.getParkingWarmCovered().getPlacesCount()));
        new InputWrapper(INPUT_PARKING_2).setValue(String.valueOf(house.getParkingWarmNotCovered().getPlacesCount()));
        new InputWrapper(INPUT_PARKING_3).setValue(String.valueOf(house.getParkingColdCovered().getPlacesCount()));
        new InputWrapper(INPUT_PARKING_4).setValue(String.valueOf(house.getParkingColdNotCovered().getPlacesCount()));
        new ButtonWrapper(BTN_HOUSE_PUSH).click();
        new ButtonWrapper(STATUS_HOUSE).shouldHaveText("201");
        log.info("House created successfully");
        return this;
    }

    @Step("Получение сгенерированного ID дома")
    public String getGeneratedHouseId() {
        String text = $x(NEW_ID_HOUSE).getText();
        return text.replaceAll("[^0-9]", "");
    }

    @Step("Заселение пользователя {0} в дом {1}")
    public AllPostPage settleUserInHouse(String userId, String houseId) {
        log.info("Settling user {} in house {}", userId, houseId);
        new InputWrapper(INPUT_SETTLE_USER_ID).setValue(userId);
        new InputWrapper(INPUT_SETTLE_HOUSE_ID).setValue(houseId);
        new RadioWrapper(RADIO_SETTLE).select();
        new ButtonWrapper(BTN_SETTLE_PUSH).click();
        new ButtonWrapper(STATUS_SETTLE).shouldHaveText("200");
        log.info("User settled successfully");
        return this;
    }

    @Step("Выселение пользователя {0} из дома {1}")
    public AllPostPage evictUserFromHouse(String userId, String houseId) {
        log.info("Evicting user {} from house {}", userId, houseId);
        new InputWrapper(INPUT_SETTLE_USER_ID).setValue(userId);
        new InputWrapper(INPUT_SETTLE_HOUSE_ID).setValue(houseId);
        new RadioWrapper(RADIO_EVICT).select();
        new ButtonWrapper(BTN_SETTLE_PUSH).click();
        new ButtonWrapper(STATUS_SETTLE).shouldHaveText("200");
        log.info("User evicted successfully");
        return this;
    }
}
