package ui.pages.swaggers;

import ui.pages.base.BasePage;
import static com.codeborne.selenide.Selenide.open;

public class SwaggerDevMvcPage extends BasePage {
    @Override
    public SwaggerDevMvcPage openPage() {
        log.info("Opening the 'SwaggerDevMvcPage' start page");
        open (BASE_URL + "/swagger-ui/index.html");
        return this;
    }
}
