package ui.pages;

import static com.codeborne.selenide.Selenide.open;

public class AllDeletePage extends BasePage{
    @Override
    public AllDeletePage openPage() {
        log.info("Opening the 'AllDeletePage' start page");
        open (BASE_URL + "/#/delete/all");
        return this;
    }
}