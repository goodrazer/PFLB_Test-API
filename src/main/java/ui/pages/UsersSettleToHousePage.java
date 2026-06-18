package ui.pages;

import io.qameta.allure.Step;
import static com.codeborne.selenide.Selenide.open;

public class UsersSettleToHousePage extends BasePage{
    @Override
    @Step("Открытие страницы 'Users -> Settle to house'")
    public BasePage openPage() {
        log.info("Opening the 'Users -> Settle to house' start page");
        open (BASE_URL + "/#/read/userInfo");
        return this;
    }
}