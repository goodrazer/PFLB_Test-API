package ui.pages;

import io.qameta.allure.Step;
import static com.codeborne.selenide.Selenide.open;

public class UsersAddMoneyPage extends BasePage{
    @Override
    @Step("Открытие страницы 'Users -> Add money'")
    public BasePage openPage() {
        log.info("Opening the 'Users -> Add money' start page");
        open (BASE_URL + "/#/update/users/plusMoney");
        return this;
    }
}