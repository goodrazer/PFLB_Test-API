package ui.pages;

import io.qameta.allure.Step;
import static com.codeborne.selenide.Selenide.open;

public class HousesSettleOrEvictUser extends BasePage{
    @Override
    @Step("Открытие страницы 'Houses -> Settle or evict user'")
    public HousesSettleOrEvictUser openPage() {
        log.info("Opening the 'Houses -> Settle or evict user' start page");
        open (BASE_URL + "/#/update/houseAndUser");
        return this;
    }
}