package ui.pages;

import static com.codeborne.selenide.Selenide.open;

public class SwaggerDevMvcPage extends BasePage{
    @Override
    public SwaggerDevMvcPage openPage() {
        log.info("Opening the 'SwaggerDevMvcPage' start page");
        open ("http://82.142.167.37:4879/swagger-ui/index.html");
        return this;
    }
}
