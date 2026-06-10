package ui.pages;

import static com.codeborne.selenide.Selenide.open;

public class SwaggerMasterMvcPage extends BasePage{
    @Override
    public SwaggerMasterMvcPage openPage() {
        log.info("Opening the 'SwaggerMasterMvcPage' start page");
        open ("http://82.142.167.37:4880/swagger-ui/index.html");
        return this;
    }
}