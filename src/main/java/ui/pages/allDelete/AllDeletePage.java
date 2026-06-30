package ui.pages.allDelete;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import ui.pages.BasePage;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class AllDeletePage extends BasePage {

    // Локаторы кнопок удаления
    private static final String BTN_DELETE_USER = "//button[@value='user']";
    private static final String BTN_DELETE_HOUSE = "//button[@value='house']";
    private static final String BTN_DELETE_CAR = "//button[@value='car']";

    // Локаторы Input элементов
    private static final String INPUT_USER_ID = BTN_DELETE_USER + "/following-sibling::button/input";
    private static final String INPUT_HOUSE_ID = BTN_DELETE_HOUSE + "/following-sibling::button/input";
    private static final String INPUT_CAR_ID = BTN_DELETE_CAR + "/following-sibling::button/input";

    // Локаторы статус-кнопок
    private static final String STATUS_USER = BTN_DELETE_USER + "/following-sibling::button[contains(@class,'status')]";
    private static final String STATUS_HOUSE = BTN_DELETE_HOUSE + "/following-sibling::button[contains(@class,'status')]";
    private static final String STATUS_CAR = BTN_DELETE_CAR + "/following-sibling::button[contains(@class,'status')]";

    public AllDeletePage() {super();}

    @Override
    @Step("Открытие страницы 'All DELETE'.")
    public AllDeletePage openPage() {
        log.info("Opening 'All DELETE' page");
        String targetUrl = BASE_URL + "/#/delete/all";

        // Пытаемся открыть страницу до 3 раз
        for (int attempt = 1; attempt <= 3; attempt++) {
            log.info("Attempt {} to open page: {}", attempt, targetUrl);
            com.codeborne.selenide.WebDriverRunner.getWebDriver().get(targetUrl);

            try {
                $x(BTN_DELETE_USER).shouldBe(visible, java.time.Duration.ofSeconds(15));
                log.info("Page loaded successfully on attempt {}", attempt);
                return this;
            } catch (Exception e) {
                log.warn("Attempt {} failed, retrying...", attempt);
                if (attempt == 3) {
                    throw e;
                }
                sleep(1000);
            }
        }
        return this;
    }

    @Step("Проверка открытия страницы 'All DELETE'.")
    public AllDeletePage isPageOpened() {
        log.info("Checking 'All DELETE' page is loaded");
        $x(BTN_DELETE_USER).shouldBe(visible, java.time.Duration.ofSeconds(15));
        String currentUrl = com.codeborne.selenide.WebDriverRunner.driver().url();
        log.info("Current URL after load: {}", currentUrl);
        return this;
    }

    //Удаления пользователя
    @Step("Удаление пользователя с ID: {0}")
    public AllDeletePage deleteUser(String userId) {
        log.info("Deleting user with ID: {}", userId);

        SelenideElement inputField = $x(INPUT_USER_ID);
        inputField.shouldBe(Condition.interactable, java.time.Duration.ofSeconds(10));
        inputField.click();
        inputField.clear();
        inputField.sendKeys(userId);
        inputField.shouldHave(Condition.value(userId), java.time.Duration.ofSeconds(5));

        $x(BTN_DELETE_USER).shouldBe(Condition.interactable, java.time.Duration.ofSeconds(10)).click();

        // Ожидаем статус 204 (No Content — успешное удаление)
        $x(STATUS_USER).shouldHave(Condition.text("204"), java.time.Duration.ofSeconds(30));
        log.info("User {} deleted successfully", userId);
        return this;
    }

    @Step("Удаление дома с ID: {0}")
    public AllDeletePage deleteHouse(String houseId) {
        log.info("Deleting house with ID: {}", houseId);

        SelenideElement inputField = $x(INPUT_HOUSE_ID);
        inputField.shouldBe(Condition.interactable, java.time.Duration.ofSeconds(10));
        inputField.click();
        inputField.clear();
        inputField.sendKeys(houseId);
        inputField.shouldHave(Condition.value(houseId), java.time.Duration.ofSeconds(5));

        $x(BTN_DELETE_HOUSE).shouldBe(Condition.interactable, java.time.Duration.ofSeconds(10)).click();

        $x(STATUS_HOUSE).shouldHave(Condition.text("204"), java.time.Duration.ofSeconds(30));
        log.info("House {} deleted successfully", houseId);
        return this;
    }

    @Step("Удаление автомобиля с ID: {0}")
    public AllDeletePage deleteCar(String carId) {
        log.info("Deleting car with ID: {}", carId);

        SelenideElement inputField = $x(INPUT_CAR_ID);
        inputField.shouldBe(Condition.interactable, java.time.Duration.ofSeconds(10));
        inputField.click();
        inputField.clear();
        inputField.sendKeys(carId);
        inputField.shouldHave(Condition.value(carId), java.time.Duration.ofSeconds(5));

        $x(BTN_DELETE_CAR).shouldBe(Condition.interactable, java.time.Duration.ofSeconds(10)).click();

        $x(STATUS_CAR).shouldHave(Condition.text("204"), java.time.Duration.ofSeconds(30));
        log.info("Car {} deleted successfully", carId);
        return this;
    }
}