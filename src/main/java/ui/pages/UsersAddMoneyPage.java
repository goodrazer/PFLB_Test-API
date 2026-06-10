package ui.pages;

import static com.codeborne.selenide.Selenide.open;

public class UsersAddMoneyPage extends BasePage{
    @Override
    public BasePage openPage() {
        log.info("Opening the 'UsersAddMoneyPage' start page");
        open (BASE_URL + "/#/update/users/plusMoney");
        return this;
    }
}