package ui.pages;

import io.qameta.allure.Step;
import static com.codeborne.selenide.Selenide.open;

public class CarsBuyOrSellCarPage extends BasePage{

    @Override
    @Step("Открытие страницы 'Cars -> Buy or sell car'")
    public CarsBuyOrSellCarPage openPage() {
        log.info("Opening the 'Cars -> Buy or sell car' start page");
        open (BASE_URL + "/#/update/users/buyCar");
        return this;
    }
}