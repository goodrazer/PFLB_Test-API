package ui.pages;

import static com.codeborne.selenide.Selenide.open;

public class CarsCreateNewPage extends BasePage{
    @Override
    public CarsCreateNewPage openPage() {
        log.info("Opening the 'CarsCreateNewPage' start page");
        open (BASE_URL + "/#/create/cars");
        return this;
    }
}