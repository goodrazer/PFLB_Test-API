package ui.pages;

import io.qameta.allure.Step;
import static com.codeborne.selenide.Selenide.open;

public class UsersBuyOrSellCarPage extends BasePage{
    @Override
    @Step("Открытие страницы 'Users -> Buy or sell car'")
    public BasePage openPage() {
        log.info("Opening the 'Users -> Buy or sell car' start page");
        open (BASE_URL + "/#/update/users/buyCar");
        return this;
    }
}