package ui.pages;

import static com.codeborne.selenide.Selenide.open;

public class CarsReadAllPage extends BasePage{
    @Override
    public CarsReadAllPage openPage() {
        log.info("Opening the 'CarsReadAllPage' start page");
        open (BASE_URL + "/#/read/cars");
        return this;
    }
}