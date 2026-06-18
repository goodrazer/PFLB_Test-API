package ui.pages;

import io.qameta.allure.Step;
import static com.codeborne.selenide.Selenide.open;

public class UsersReadAllPage extends BasePage{
    @Override
    @Step("Открытие страницы 'Users -> Read all'")
    public BasePage openPage() {
        log.info("Opening the 'Users -> Read all' start page");
        open (BASE_URL + "/#/read/users");
        return this;
    }
}