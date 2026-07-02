package ui.pages.swaggers;

import ui.pages.base.BasePage;

import static com.codeborne.selenide.Selenide.open;

public class SwaggerMasterMvcPage extends BasePage {
    @Override
    public SwaggerMasterMvcPage openPage() {
        log.info("Opening the 'SwaggerMasterMvcPage' start page");
        open (BASE_URL + "/swagger-ui/index.html");
        return this;
    }
}