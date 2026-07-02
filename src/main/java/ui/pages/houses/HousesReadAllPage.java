package ui.pages.houses;

import ui.pages.base.BasePage;
import static com.codeborne.selenide.Selenide.open;

public class HousesReadAllPage extends BasePage {
    @Override
    public HousesReadAllPage openPage() {
        log.info("Opening the 'HousesReadAllPage' start page");
        open (BASE_URL + "/#/read/houses");
        return this;
    }
}
