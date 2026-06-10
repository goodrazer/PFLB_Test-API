package ui.pages;

import static com.codeborne.selenide.Selenide.open;

public class CarsBuyOrSellCarPage extends BasePage{
    @Override
    public CarsBuyOrSellCarPage openPage() {
        log.info("Opening the 'CarsBuyOrSellCarPage' start page");
        open (BASE_URL + "/#/update/users/buyCar");
        return this;
    }
}