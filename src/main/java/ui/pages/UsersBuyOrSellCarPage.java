package ui.pages;

import static com.codeborne.selenide.Selenide.open;

public class UsersBuyOrSellCarPage extends BasePage{
    @Override
    public BasePage openPage() {
        log.info("Opening the 'UsersBuyOrSellCarPage' start page");
        open (BASE_URL + "/#/update/users/buyCar");
        return this;
    }
}
