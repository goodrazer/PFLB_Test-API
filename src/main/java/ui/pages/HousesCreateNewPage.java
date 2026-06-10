package ui.pages;

import static com.codeborne.selenide.Selenide.open;

public class HousesCreateNewPage extends BasePage{
    @Override
    public HousesCreateNewPage openPage() {
        log.info("Opening the 'HousesCreateNewPage' start page");
        open (BASE_URL + "/#/create/house");
        return this;
    }
}