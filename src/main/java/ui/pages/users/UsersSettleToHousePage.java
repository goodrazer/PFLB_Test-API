package ui.pages.users;

import ui.pages.base.BasePage;
import static com.codeborne.selenide.Selenide.open;

public class UsersSettleToHousePage extends BasePage {
    @Override
    public BasePage openPage() {
        log.info("Opening the 'UsersReadUserWithCarsPage' start page");
        open (BASE_URL + "/#/read/userInfo");
        return this;
    }
}
