package ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ui.dto.Car;
import ui.steps.wrappers.InputCars;
import ui.steps.wrappers.InputWrapper;

import static com.codeborne.selenide.Selenide.*;

public class CarsCreateNewPage extends BasePage{

    private final String STATUS_CREATE_MESSAGE = "//button[contains(text(), 'Status')]";
    private final String PUSH_TO_API_BUTTON = "//*[@id='root']/div/section/div/div/button[1]";

    @Override
    @Step("открытие страницы создания нового авто.")
    public CarsCreateNewPage openPage() {
        log.info("Opening the 'CarsCreateNewPage' start page");
        open (BASE_URL + "/#/create/cars");
        return this;
    }

    @Step("Проверка открытия страницы 'Create new car'.")
    public CarsCreateNewPage isPageOpened() {
        log.info("Checking 'Create new car' page is loaded");
        // Если openPage() уже отработал - элемент уже виден, логируем URL для отладки
        String currentUrl = com.codeborne.selenide.WebDriverRunner.driver().url();
        log.info("Current URL after load: {}", currentUrl);
        // Дополнительная проверка: если элемент не виден - открываем заново
        if (!currentUrl.equals("http://82.142.167.37:4881/#/create/cars")) {
            log.info("Form not visible, reopening page...");
            openPage();
        }
        return this;
    }

    @Step("Создание авто с валидными данными")
    public CarsCreateNewPage createNewCar(Car car) {
        new InputCars("car_engine_type_send").write(car.getEngineType());
        new InputCars("car_mark_send").write(car.getMark());
        new InputCars("car_model_send").write(car.getModel());
        new InputCars("car_price_send").write(car.getPrice());
        $x(PUSH_TO_API_BUTTON).click();
        return this;
    }

    @Step("Получение текста ошибки авто")
    public String getStatusMessage() {
        log.info("Return error text after create care with broken data");
        //без явного ожидания, так как выведет ошибку, что не нашло нужного элемента по таймауту
        sleep(1000);
        return $x(STATUS_CREATE_MESSAGE).getText();
    }
}