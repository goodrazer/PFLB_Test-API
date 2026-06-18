package ui.pages;

import io.qameta.allure.Step;
import static com.codeborne.selenide.Selenide.open;

public class UsersReadUserWithCarsPage extends BasePage{
    @Override
    @Step("Открытие страницы 'Users -> Read user with cars'")
    public BasePage openPage() {
        log.info("Opening the 'Users -> Read user with cars' start page");
        open (BASE_URL + "/#/read/userInfo");
        return this;
    }
}