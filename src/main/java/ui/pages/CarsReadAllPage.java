package ui.pages;

import io.qameta.allure.Step;
import static com.codeborne.selenide.Selenide.open;

public class CarsReadAllPage extends BasePage{
    @Override
    @Step("Открытие страницы 'Cars -> Read all'")
    public CarsReadAllPage openPage() {
        log.info("Opening the 'Cars -> Read all' start page");
        open (BASE_URL + "/#/read/cars");
        return this;
    }
}