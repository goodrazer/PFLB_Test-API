package ui.pages;

import static com.codeborne.selenide.Selenide.open;

public class UsersReadAllPage extends BasePage{
    @Override
    public BasePage openPage() {
        log.info("Opening the 'UsersReadAllPage' start page");
        open (BASE_URL + "/#/read/users");
        return this;
    }
}
