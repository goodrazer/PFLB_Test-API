package ui.pages;

import static com.codeborne.selenide.Selenide.open;

public class UsersReadUserWithCarsPage extends BasePage{
    @Override
    public BasePage openPage() {
        log.info("Opening the 'UsersReadUserWithCarsPage' start page");
        open (BASE_URL + "/#/read/userInfo");
        return this;
    }
}
