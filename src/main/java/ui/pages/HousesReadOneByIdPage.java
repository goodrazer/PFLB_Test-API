package ui.pages;

import static com.codeborne.selenide.Selenide.open;

public class HousesReadOneByIdPage extends BasePage{
    @Override
    public HousesReadOneByIdPage openPage() {
        log.info("Opening the 'HousesReadOneByIdPage' start page");
        open (BASE_URL + "/#/read/house");
        return this;
    }
}
