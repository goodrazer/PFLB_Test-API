package ui.pages;

import io.qameta.allure.Step;
import static com.codeborne.selenide.Selenide.open;

public class HousesReadAllPage extends BasePage{
    @Override
    @Step("Открытие страницы 'Houses -> Read all'")
    public HousesReadAllPage openPage() {
        log.info("Opening the 'Houses -> Read all' start page");
        open (BASE_URL + "/#/read/houses");
        return this;
    }
}