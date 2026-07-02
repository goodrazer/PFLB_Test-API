package ui.pages.houses;

import ui.pages.base.BasePage;
import static com.codeborne.selenide.Selenide.open;

public class HousesSettleOrEvictUser extends BasePage {
    @Override
    public HousesSettleOrEvictUser openPage() {
        log.info("Opening the 'HousesSettleOrEvictUser' start page");
        open (BASE_URL + "/#/update/houseAndUser");
        return this;
    }
}
